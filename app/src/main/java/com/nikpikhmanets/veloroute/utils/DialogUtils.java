package com.nikpikhmanets.veloroute.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

/**
 * Created by Ivan on 10-Mar-17.
 */

public abstract class DialogUtils {

    public static AlertDialog getWaitingDialog(Context context, String title) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage("wait...");
        dialogBuilder.setView(new ProgressBar(context));
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

}
