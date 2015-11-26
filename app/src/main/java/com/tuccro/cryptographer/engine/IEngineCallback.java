package com.tuccro.cryptographer.engine;

/**
 * Created by tuccro on 11/23/15.
 */
public interface IEngineCallback {

    int STATE_FILE_READING = 0;
    int STATE_KEY_GENERATING = 1;
    int STATE_FILE_ENCODING = 2;
    int STATE_FILE_WRITING = 3;

    void onStateChange(int state);

    void onFinish();

    void onError();
}
