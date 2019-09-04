package com.ooo.main.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jess.arms.widget.CustomPopupWindow;
import com.ooo.main.R;


public class SwitchShortcutPopupWindow {
    private Context mContext;
    private CustomPopupWindow mPopupWindow;

    public SwitchShortcutPopupWindow(Context context) {
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow_switch_shortcut, null, false);
        mPopupWindow = CustomPopupWindow
                .builder()
                .contentView(contentView)
                .isWrap(true)
                .customListener(mCustomPopupWindowListener)
                .build();
        mPopupWindow.setOnDismissListener(mOnDismissListener);
    }

    CustomPopupWindow.CustomPopupWindowListener mCustomPopupWindowListener = new CustomPopupWindow.CustomPopupWindowListener() {
        @Override
        public void initPopupView(View contentView) {
        }
    };

    PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(1.0f);
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();

        }
    };

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * 按钮的监听
     * @param v
     */
    public void openPopWindow(View v) {
        setBackgroundAlpha(0.8f);
        mPopupWindow.showAsDropDown(v);
    }

}
