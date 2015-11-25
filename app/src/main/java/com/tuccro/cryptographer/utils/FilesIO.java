package com.tuccro.cryptographer.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by tuccro on 11/23/15.
 */
public class FilesIO {

    public static byte[] getFileBytes(String sourceFilePath) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(sourceFilePath), "r");

        byte[] b = new byte[(int) randomAccessFile.length()];
        randomAccessFile.read(b);
        return b;
    }

    public static void writeFile(byte[] bytes, String destination, String name) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destination, name)));
        bos.write(bytes);
        bos.flush();
        bos.close();
    }
}
