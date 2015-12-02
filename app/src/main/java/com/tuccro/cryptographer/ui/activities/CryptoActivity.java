package com.tuccro.cryptographer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tuccro.cryptographer.R;
import com.tuccro.cryptographer.engine.CryptoThread;
import com.tuccro.cryptographer.engine.IEngineCallback;
import com.tuccro.cryptographer.ui.dialogs.EncodingProgressDialog;
import com.tuccro.filemanager.FileManager;

import java.io.File;

public class CryptoActivity extends AppCompatActivity implements IEngineCallback {

    public static final String KEY_DIRECTION = "direction";

    public static final String ENCRYPTION = "encryption";
    public static final String DECRYPTION = "decryption";

    private String currentDirection;

    Button buttonGetFile;
    Button buttonGetFolder;
    Button buttonNext;

    CheckBox checkBoxUseOrigin;

    TextView textViewFrom;
    TextView textViewTo;

    String fileFromPath;
    String resultFolderPath;

    EncodingProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            currentDirection = getIntent().getStringExtra(KEY_DIRECTION);
        } catch (Exception e) {
            e.printStackTrace();
            currentDirection = ENCRYPTION;
        }

        setContentView(R.layout.activity_crypto);

        initToolbar();
        initViews();
    }

    private void initViews() {
        buttonGetFile = (Button) findViewById(R.id.button_get_file);
        buttonGetFolder = (Button) findViewById(R.id.button_get_folder);
        buttonNext = (Button) findViewById(R.id.button_next);
        checkBoxUseOrigin = (CheckBox) findViewById(R.id.checkbox_use_original);

        textViewFrom = (TextView) findViewById(R.id.text_from);
        textViewTo = (TextView) findViewById(R.id.text_to);

        buttonGetFile.setOnClickListener(onClickListener);
        buttonGetFolder.setOnClickListener(onClickListener);
        buttonNext.setOnClickListener(onClickListener);

        checkBoxUseOrigin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonGetFolder.setEnabled(false);
                    setResultFolderByFileDestination();

                    Snackbar.make(buttonView, "File will be encrypted at source folder", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    buttonGetFolder.setEnabled(true);
                }
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        switch (currentDirection) {
            case ENCRYPTION:
                toolbar.setTitle(R.string.title_activity_encryptor);
                break;
            case DECRYPTION:
                toolbar.setTitle(R.string.title_activity_decryptor);
                break;
        }
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setResultFolderByFileDestination() {
        if (fileFromPath != null && !fileFromPath.isEmpty()) {

            resultFolderPath = getFileFolderPath(fileFromPath);
            textViewTo.setText(resultFolderPath);
        }
    }

    private String getFileFolderPath(String filePath) {
        return new File(fileFromPath).getParentFile().getPath();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_get_file:
                    FileManager.getFile(CryptoActivity.this);
                    break;
                case R.id.button_get_folder:
                    FileManager.getFolder(CryptoActivity.this);
                    break;
                case R.id.button_next:
                    new CryptoThread(CryptoActivity.this, fileFromPath, resultFolderPath, "test").start();

                    FragmentManager fm = getSupportFragmentManager();
//                    editNameDialog.show(fm, "fragment_edit_name");

                    progressDialog = new EncodingProgressDialog();
                    progressDialog.show(fm, "tag");

                    progressDialog.setTitle("INIT");
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) switch (requestCode) {
            case FileManager.REQUEST_FILE:

                fileFromPath = FileManager.getPathFromResultIntent(data);
                textViewFrom.setText(fileFromPath);
                break;
            case FileManager.REQUEST_FOLDER:

                resultFolderPath = FileManager.getPathFromResultIntent(data);
                textViewTo.setText(resultFolderPath);
                break;
        }
    }

    @Override
    public void onStateChange(final int state) {

        final String mState;

        switch (state) {
            case 0:
                mState = "FILE_READING";
                break;
            case 1:
                mState = "KEY_GENERATING";
                break;
            case 2:
                mState = "FILE_ENCODING";
                break;
            case 3:
                mState = "FILE_DECODING";
                break;
            case 4:
                mState = "FILE_WRITING";
                break;
            default:
                mState = "Please wait";
                break;
        }

        Log.e("Status", mState);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setTitle(mState);
            }
        });
    }

    @Override
    public void onFinish() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.getDialog().dismiss();
                Toast.makeText(CryptoActivity.this, "Wow!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError() {
    }
}
