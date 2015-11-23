package com.tuccro.cryptographer.engine;

import com.tuccro.cryptographer.utils.FilesIO;

import java.io.IOException;

/**
 * Created by tuccro on 11/23/15.
 */
public class CryptoThread extends Thread {

    IEngineCallback iEngineCallback;

    String sourceFilePath;
    String resultDestinationPath;

    public CryptoThread(IEngineCallback iEngineCallback
            , String sourceFilePath, String resultDestinationPath) {
        this.iEngineCallback = iEngineCallback;
        this.sourceFilePath = sourceFilePath;
        this.resultDestinationPath = resultDestinationPath;
    }

    @Override
    public void run() {
        super.run();

        byte[] fileBytes;

        try {
            fileBytes = FilesIO.getFileBytes(sourceFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            iEngineCallback.onError();
        }

//        Testing the interface
        iEngineCallback.onFinish();
    }
}
