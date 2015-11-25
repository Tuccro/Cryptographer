package com.tuccro.cryptographer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tuccro.cryptographer.R;
import com.tuccro.cryptographer.engine.CryptoThread;
import com.tuccro.cryptographer.engine.IEngineCallback;
import com.tuccro.filemanager.FileManager;

import java.io.File;

public class EncryptActivity extends AppCompatActivity implements IEngineCallback {

    Button buttonGetFile;
    Button buttonGetFolder;
    Button buttonNext;

    CheckBox checkBoxUseOrigin;

    TextView textViewFrom;
    TextView textViewTo;

    String fileFromPath;
    String resultFolderPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
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
                    FileManager.getFile(EncryptActivity.this);
                    break;
                case R.id.button_get_folder:
                    FileManager.getFolder(EncryptActivity.this);
                    break;
                case R.id.button_next:
                    new CryptoThread(EncryptActivity.this, fileFromPath, resultFolderPath, "test").start();
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
    public void onFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EncryptActivity.this, "Wow!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError() {
    }
}
