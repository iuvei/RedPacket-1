package me.jessyan.armscomponent.commonres.view.popupwindow;

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

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;


public class NotescontactPopupWindow {
    private Context mContext;
    private CustomPopupWindow mPopupWindow;
    private ImageButton ibtnClose,ibtnNotescontact;

    public NotescontactPopupWindow(Context context) {
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate( R.layout.popupwindow_notescontact, null, false);
        mPopupWindow = CustomPopupWindow
                .builder()
                .contentView(contentView)
                .animationStyle( R.style.public_dialog_inout_anim)
                .isWrap(true)
                .customListener(mCustomPopupWindowListener)
                .build();
        mPopupWindow.setOnDismissListener(mOnDismissListener);
    }

    CustomPopupWindow.CustomPopupWindowListener mCustomPopupWindowListener = new CustomPopupWindow.CustomPopupWindowListener() {
        @Override
        public void initPopupView(View contentView) {
            contentView.findViewById( R.id.ibtn_close).setOnClickListener(mOnClickListener);
            contentView.findViewById( R.id.ibtn_notescontact).setOnClickListener(mOnClickListener);
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
            if(i == R.id.ibtn_close){
                mPopupWindow.dismiss();

            }else if(i == R.id.ibtn_notescontact){
                ARouterUtils.navigation(mContext, RouterHub.IM_CUSTOMERSERVICELISTACTIVITY);
                mPopupWindow.dismiss();
            }
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
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

}
