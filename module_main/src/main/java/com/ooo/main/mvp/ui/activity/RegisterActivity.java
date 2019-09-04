package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerLoginComponent;
import com.ooo.main.mvp.contract.LoginContract;
import com.ooo.main.mvp.presenter.LoginPresenter;
import com.ooo.main.view.dialog.NotescontactDialog;
import com.ooo.main.view.popupwindow.NotescontactPopupWindow;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.CountDownUtils;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class RegisterActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R2.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R2.id.et_invite_code)
    EditText etInviteCode;

    @Inject
    AppManager mAppManager;

    private CountDownUtils mCountDownUtils;
    private ProgressDialogUtils progressDialogUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        mCountDownUtils = new CountDownUtils(tvGetCode);
        mCountDownUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }


    @OnClick({R2.id.btn_register, R2.id.iv_contact_custom})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_register) {
            register();

        } else if (i == R.id.iv_contact_custom) {
            showNotescontactPopupWindow(view);
        }
    }

    public void hideSoftInput(){
        if(KeyboardUtils.isSoftInputVisible(this))
            KeyboardUtils.hideSoftInput(this);
    }

    private NotescontactPopupWindow mNotescontactPopupWindow;
    private void showNotescontactPopupWindow(View view){
        if( null == mNotescontactPopupWindow ){
            mNotescontactPopupWindow = new NotescontactPopupWindow(mContext);
        }
        mNotescontactPopupWindow.openPopWindow(view);
    }

    private void sendSms(){
        String phoneNumber = etPhone.getText().toString();
        if(!RegexUtils.isMobileSimple(phoneNumber)){
            showMessage("手机号不能为空!");
            etPhone.requestFocus();
            return;
        }
        mPresenter.sendSms(phoneNumber,true);
    }

    private void register(){
        String phoneNumber = etPhone.getText().toString();
        if(!RegexUtils.isMobileSimple(phoneNumber)){
            showMessage("手机号不能为空/不可用!");
            return;
        }
        String verificationCode = etAuthCode.getText().toString();
        if (TextUtils.isEmpty(verificationCode) || verificationCode.length() < 6) {
            etAuthCode.requestFocus();
            showMessage("验证码不能为空/不可用!");
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            showMessage("密码不能为空/不可用!");
            return;
        }
        String confirmPassword = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.requestFocus();
            showMessage("确认密码不能为空/不可用!");
            return;
        }
        if(!password.equals(confirmPassword)){
            showMessage("确认密码不一致!");
            return;
        }
        String inviteCode = etInviteCode.getText().toString();
        if (TextUtils.isEmpty(inviteCode)) {
            etInviteCode.requestFocus();
            showMessage("邀请码不能为空/不可用!");
            return;
        }
        hideSoftInput();
        mPresenter.register(phoneNumber,verificationCode,password,inviteCode);
    }

    @Override
    public void sendSmsSuccessful() {
        if(null!= mCountDownUtils)
            mCountDownUtils.start();
    }

    @Override
    public void sendSmsFail() {
        if(null != mCountDownUtils)
            mCountDownUtils.reset();
    }

    @Override
    public void loginSuccessful() {
        launchActivity(new Intent(this, MainActivity.class));
        mAppManager.killAll(MainActivity.class);
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage(getString(R.string.public_loading));
        }
        if (show) {
            progressDialogUtils.show();
        } else {
            progressDialogUtils.dismiss();
        }
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

}
