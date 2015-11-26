package com.tuccro.cryptographer.engine;

import android.util.Log;

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
            iEngineCallback.onStateChange(IEngineCallback.STATE_FILE_READING);
            fileBytes = FilesIO.getFileBytes(sourceFilePath);

            iEngineCallback.onStateChange(IEngineCallback.STATE_KEY_GENERATING);
            byte[] key = Crypto.generateKey(password);

            iEngineCallback.onStateChange(IEngineCallback.STATE_FILE_ENCODING);
            byte[] result = Crypto.encodeFile(key, fileBytes);

            iEngineCallback.onStateChange(IEngineCallback.STATE_FILE_WRITING);
            FilesIO.writeFile(result, resultDestinationPath, new File(sourceFilePath).getName().concat(".encrypt"));

//            Log.e("Array size", String.valueOf(fileBytes.length));

        } catch (Exception e) {
            e.printStackTrace();
            iEngineCallback.onError();
        }

        iEngineCallback.onFinish();
    }
}
