package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;

/**
 * creat at 2019/10/8
 * description
 * 支付密码弹窗
 */
public class PayPassDialog extends BaseCustomDialog {

    private BaseDialog dialog;
    private Context context;
    private String money;

    public PayPassDialog(Context context,String money) {
        super ( context );
        this.context = context;
        this.money = money;
        init();
    }

    public PayPassDialog(Context context, int theme,String money) {
        super ( context, theme );
        this.context = context;
        this.money = money;
        init();
    }

    public void init(){
        dialog = new BaseCustomDialog.Builder ( context, R.layout.dialog_paypasswrod_info, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                ImageView ivClose = layout.findViewById ( R.id.iv_close );
                ivClose.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
                TextView tvMoney = layout.findViewById ( R.id.tv_money );
                tvMoney.setText ( money );
                LinearLayout ll_balance = layout.findViewById ( R.id.ll_balance );
                EditText et_password = layout.findViewById ( R.id.et_password );
                et_password.addTextChangedListener ( new TextWatcher () {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!TextUtils.isEmpty ( charSequence ) && charSequence.length ()==6){
                            //密码输入完成
                            if (payPasswordInputListener!=null){
                                payPasswordInputListener.inputFinish ( charSequence.toString () );
                                dialog.dismiss ();
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );

            }
        } )
        .create ();
        dialog.show ();
    }

    private  PayPasswordInputListener payPasswordInputListener;

    public void setPayPasswordInputListener(PayPasswordInputListener payPasswordInputListener) {
        this.payPasswordInputListener = payPasswordInputListener;
    }

    public interface PayPasswordInputListener{
        void inputFinish(String password);
    }
}
