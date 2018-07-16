package com.gdc.aerodev.repository.postgresql.test;

import java.io.*;

class FileSharer {

    File inputFile = new File(getClass().getResource("/file/test.jpg").getPath());
    private File outputFile = new File(getClass().getResource("/file/").getPath() + "out.jpg");

    byte[] getFile() {
        try (
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int a;
            while ((a = input.read()) != -1) {
                out.write(a);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Cannot load file: '" + inputFile.getAbsolutePath() + "'.", e);
        }
    }

    void saveFile(byte[] data){
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file to: '" + outputFile.getAbsolutePath() + "'.", e);
        }
    }
}
