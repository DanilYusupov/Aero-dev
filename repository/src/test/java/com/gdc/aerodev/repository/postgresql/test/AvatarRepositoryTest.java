package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.AvatarRepository;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class AvatarRepositoryTest extends FileSharer {

    @Autowired
    private AvatarRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Long userTwoId = 2L;
    private int count = 3;

    @Test
    public void createAvatarTest() {
        createAvatar();
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void updateTest(){
        Avatar avatar = createAvatar();
        avatar.setAvatarData(new byte[]{});
        avatar = repository.save(avatar);
        assertEquals(0, avatar.getAvatarData().length);
    }

    @Test
    public void getByProjectTest() {
        User user = userRepository.findByUserId(userTwoId);
        Avatar avatar = createAvatar();
        assertEquals(avatar, repository.findByUser(user));
    }

    @Test
    public void cascadeDeleteTest() {
        createAvatar();
        userRepository.deleteById(userTwoId);
        assertEquals(0, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getByFakeId() {
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullFileTest() {
        User user = userRepository.findByUserId(userTwoId);
        Avatar avatar = new Avatar(null, "");
        avatar.setUser(user);
        repository.save(avatar);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveProjectNullTest() {
        Avatar avatar = new Avatar(new byte[]{}, "");
        repository.save(avatar);
    }

    private Avatar createAvatar() {
        User user = userRepository.findByUserId(userTwoId);
        Avatar avatar;
        try {
            avatar = new Avatar(getFile(), Files.probeContentType(inputFile.toPath()));
            avatar.setUser(user);
            user.setAvatar(avatar);
            avatar = repository.save(avatar);
            return avatar;
        } catch (IOException e) {
            throw new RuntimeException("Cannot get MIME type of file: '" + inputFile.getAbsolutePath() + "'.", e);
        }
    }

}
