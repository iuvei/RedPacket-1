package me.jessyan.armscomponent.commonres.utils;

import android.app.ProgressDialog;
import android.content.Context;

import me.jessyan.armscomponent.commonres.dialog.IconDrawableDialog;

public class ProgressDialogUtils {
    private IconDrawableDialog mProgressDialog;


    public ProgressDialogUtils(Context mContext) {
        mProgressDialog = new IconDrawableDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("加载中...");
    }


    public static ProgressDialogUtils getInstance(Context mContext) {
        return new ProgressDialogUtils(mContext);
    }

    public ProgressDialogUtils setMessage(String context) {
        mProgressDialog.setMessage(context);
        return this;
    }


    public ProgressDialogUtils show() {
        mProgressDialog.show();
        return this;
    }

    public ProgressDialogUtils dismiss() {
        mProgressDialog.dismiss();
        return this;
    }
}
