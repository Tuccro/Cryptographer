package com.tuccro.cryptographer.ui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by tuccro on 11/26/15.
 */
public class EncodingProgressDialog extends ProgressDialog {

    public EncodingProgressDialog(Context context) {
        super(context);

        this.setCancelable(false);
    }
}
