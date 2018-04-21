package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Avatar;

import java.util.List;

/**
 * DAO for working with {@code Avatar} entity
 *
 * @see com.gdc.aerodev.model.Avatar
 * @author Yusupov Danil
 */
public interface AvatarDao extends GenericDao<Avatar, Long> {

    /**
     * There is no reason to get avatars of all users.
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<Avatar> getAll(){return null;}

    /**
     * {@code Avatar} haven't got a name. It can used, to get avatar by user's name,
     * but it seems to be redefined.
     * @return {@code null}
     */
    @Deprecated
    @Override
    default Avatar getByName(String name){return null;}
}
