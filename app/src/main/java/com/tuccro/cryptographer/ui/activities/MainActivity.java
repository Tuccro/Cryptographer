package com.tuccro.cryptographer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tuccro.cryptographer.R;

public class MainActivity extends AppCompatActivity {

    Button btEncrypt;
    Button btDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btEncrypt = (Button) findViewById(R.id.bt_encrypt);
        btDecrypt = (Button) findViewById(R.id.bt_decrypt);

        btEncrypt.setOnClickListener(onClickListener);
        btDecrypt.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;

            switch (v.getId()) {
                case R.id.bt_encrypt:
                    intent = new Intent(MainActivity.this, CryptoActivity.class);
                    intent.putExtra(CryptoActivity.KEY_DIRECTION, CryptoActivity.ENCRYPTION);
                    startActivity(intent);
                    break;
                case R.id.bt_decrypt:
                    intent = new Intent(MainActivity.this, CryptoActivity.class);
                    intent.putExtra(CryptoActivity.KEY_DIRECTION, CryptoActivity.DECRYPTION);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
