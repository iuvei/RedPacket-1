package com.ooo.main.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.ooo.main.R;

/**
 * Created by Administrator on 2017/8/22.
 */

public class NotescontactDialog {

    private AlertDialog mAlertDialog;

    public NotescontactDialog(Context context) {
        mAlertDialog = new AlertDialog.Builder(context)
                .setView(R.layout.popupwindow_notescontact)
                .create();
    }


    /**
     * 按钮的监听
     */
    public void show() {
        mAlertDialog.show();
    }
}
