package com.tuccro.cryptographer.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by tuccro on 11/23/15.
 */
public class FilesIO {

    public static byte[] getFileBytes(String sourceFilePath) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFilePath, "r");

        byte[] b = new byte[(int) randomAccessFile.length()];
        randomAccessFile.read(b);
        return b;
    }
}
