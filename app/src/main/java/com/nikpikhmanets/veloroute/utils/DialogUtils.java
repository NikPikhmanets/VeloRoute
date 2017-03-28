package com.nikpikhmanets.veloroute.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnVoteListener;

public abstract class DialogUtils {

//    public static AlertDialog getWaitingDialog(Context context, String title) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        dialogBuilder.setTitle(title);
//        dialogBuilder.setMessage(R.string.msg_wait);
//        dialogBuilder.setView(new ProgressBar(context));
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        return alertDialog;
//    }

    public static ProgressDialog getWaitingDialog(Context context, String title) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public static AlertDialog getRatingDialog(Context context, final OnVoteListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Оцените маршрут");
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rate, null, false);
        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.rb_vote);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onVoted((int) ratingBar.getRating());
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("отмена", null);
        return dialogBuilder.create();
    }

}
