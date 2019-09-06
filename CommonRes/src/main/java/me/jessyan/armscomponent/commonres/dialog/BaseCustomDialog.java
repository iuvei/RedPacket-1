package me.jessyan.armscomponent.commonres.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jessyan.armscomponent.commonres.R;

/**
 * @author lanjian
 * creat at 2019/9/6
 * description
 */
public class BaseCustomDialog extends Dialog {

    public BaseCustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public BaseCustomDialog(Context context) {
        super(context);
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private OnShowDialogListener listener;
        private int xmlLayoutId;
        private boolean OnTouchOutside;
        //必须调用这个构造函数，而且要传入监听方法，所有的操作在监听方法那里执行,xmllayout为布局的id，OnTouchOutside点击外面是否会消息，true为会消失
        public Builder(Context context,int xmlLayoutId,Boolean OnTouchOutside,OnShowDialogListener onShowDialogListener) {
            this.context = context;
            this.listener = onShowDialogListener;
            this.xmlLayoutId = xmlLayoutId;
            this.OnTouchOutside = OnTouchOutside;
        }



        public BaseDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final BaseDialog dialog = new BaseDialog(context,
                    R.style.Dialog);
            dialog.setCanceledOnTouchOutside(OnTouchOutside);
            //布局页面
            View layout = inflater.inflate(xmlLayoutId, null);
            if (listener!=null) {
                listener.onShowDialog(layout);
            }
            dialog.addContentView(layout, new ViewGroup.LayoutParams (
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }

        public interface OnShowDialogListener{
            void onShowDialog(View layout);
        }

    }
}
