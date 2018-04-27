package com.gdc.aerodev.dao.test.postgres;

import java.io.*;

/**
 * Child can get test image from classpath
 */
abstract class WithFiles {
    /**
     * Path to test image
     */
    private final String DEFAULT_IMAGE_PATH = "java.png";

    /**
     * Gives test image as array of bytes. <br>
     *     <b>Duplicates, because this method locale in 'dao'</b>
     * @return image via {@code byte[]}
     * @throws IOException if there is no such file or connection errors
     */
    @SuppressWarnings("Duplicates")
    byte[] getImage() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File image = new File(classLoader.getResource(DEFAULT_IMAGE_PATH).getFile());
        try (
                BufferedInputStream in =
                        new BufferedInputStream(
                                new FileInputStream(image)
                        );
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int a;
            while ((a = in.read()) != -1) {
                out.write(a);
            }
            return out.toByteArray();
        }
    }
}
