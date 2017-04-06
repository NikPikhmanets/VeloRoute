package com.nikpikhmanets.veloroute.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnSignOutConfirmListener;
import com.nikpikhmanets.veloroute.interfaces.OnVoteListener;

public abstract class DialogUtils {

    private static ProgressDialog progressDialog;
    private static AlertDialog.Builder builder;

    public static ProgressDialog getWaitingDialog(Context context, String title) {
        if (progressDialog == null || !progressDialog.getContext().equals(context)) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public static AlertDialog getRatingDialog(Context context, final OnVoteListener listener) {
        createBuilder(context);
        builder.setTitle("Оцените маршрут");
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rate, null, false);
        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.rb_vote);
        builder.setView(dialogView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onVoted((int) ratingBar.getRating());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("отмена", null);
        return builder.create();
    }

    public static AlertDialog getLogOutConfirmDialog(Context context, final OnSignOutConfirmListener listener) {
        createBuilder(context);
        builder.setTitle(R.string.menu_sign_out);
        builder.setMessage(R.string.msg_confirm_sign_out);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSignOutConfirmed();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private static void createBuilder(Context context) {
        if (builder == null || !builder.getContext().equals(context)) {
            builder = new AlertDialog.Builder(context);
        }
    }

}
