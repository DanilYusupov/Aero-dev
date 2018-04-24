package com.gdc.aerodev.dao.test.postgres;

import java.io.*;

abstract class WithFiles {
    byte[] getImage() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File image = new File(classLoader.getResource("java.png").getFile());
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
