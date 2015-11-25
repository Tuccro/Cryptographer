package com.tuccro.cryptographer.engine;

import com.tuccro.cryptographer.utils.Crypto;
import com.tuccro.cryptographer.utils.FilesIO;

import java.io.File;

/**
 * Created by tuccro on 11/23/15.
 */
public class CryptoThread extends Thread {

    IEngineCallback iEngineCallback;

    String sourceFilePath;
    String resultDestinationPath;
    String password;

    public CryptoThread(IEngineCallback iEngineCallback
            , String sourceFilePath, String resultDestinationPath, String password) {
        this.iEngineCallback = iEngineCallback;
        this.sourceFilePath = sourceFilePath;
        this.resultDestinationPath = resultDestinationPath;
        this.password = password;
    }

    @Override
    public void run() {
        super.run();

        byte[] fileBytes;

        try {
            fileBytes = FilesIO.getFileBytes(sourceFilePath);
            byte[] key = Crypto.generateKey(password);

            byte[] result = Crypto.encodeFile(key, fileBytes);

            FilesIO.writeFile(result, resultDestinationPath, new File(sourceFilePath).getName().concat(".encrypt"));

        } catch (Exception e) {
            e.printStackTrace();
            iEngineCallback.onError();
        }

//        Testing the interface
        iEngineCallback.onFinish();
    }
}
