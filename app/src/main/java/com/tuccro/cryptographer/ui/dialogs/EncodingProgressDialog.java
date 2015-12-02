package com.tuccro.cryptographer.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuccro.cryptographer.R;

/**
 * Created by tuccro on 11/26/15.
 */
public class EncodingProgressDialog extends DialogFragment {

    public EncodingProgressDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_progress, container);
        this.setTitle("title");
        return view;
    }

    public void setTitle(String title) {
//        getDialog().setTitle(title);
    }
}
