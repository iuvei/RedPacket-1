package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.ui.WebviewActivity;
import me.jessyan.armscomponent.commonres.utils.CountDownUtils;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

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
    @BindView(R2.id.et_nickname)
    EditText etNickName;
    @BindView(R2.id.tv_agreement)
    TextView tvAgreement;

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
        StatusBarUtils.setTranslucentStatus(this);
        StatusBarUtils.setStatusBarDarkTheme ( this,true );

        mCountDownUtils = new CountDownUtils(tvGetCode);
        mCountDownUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }


    @OnClick({R2.id.btn_register, R2.id.tv_agreement,R2.id.iv_back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_register) {
            register();

        } else if (i == R.id.tv_agreement) {
            //用户协议
          WebviewActivity.start ( this,getString ( R.string.agreement ),"https://blog.csdn.net/weixin_40536539/article/details/78705904" );
        }else if (i==R.id.iv_back){
            finish ();
        }
    }

    public void hideSoftInput(){
        if(KeyboardUtils.isSoftInputVisible(this))
            KeyboardUtils.hideSoftInput(this);
    }


    private void sendSms(){
        String phoneNumber = etPhone.getText().toString();
        if(!RegexUtils.isMobileSimple(phoneNumber)){
            showMessage("手机号不能为空!");
            etPhone.requestFocus();
            return;
        }
        mCountDownUtils.start ();
        mPresenter.sendSms(phoneNumber,true);
    }

    private void register(){
        String phoneNumber = etPhone.getText().toString();
        if(!RegexUtils.isMobileSimple(phoneNumber)){
            showMessage("手机号不能为空/不可用!");
            return;
        }
        String verificationCode = etAuthCode.getText().toString();
        if (TextUtils.isEmpty(verificationCode)) {
            etAuthCode.requestFocus();
            showMessage("验证码不能为空/不可用!");
            return;
        }
        String nickname = etNickName.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            etNickName.requestFocus();
            showMessage("昵称不能为空");
            return;
        }
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            showMessage("密码不能为空");
            return;
        }
        String confirmPassword = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.requestFocus();
            showMessage("确认密码不能为空");
            return;
        }
        if(!password.equals(confirmPassword)){
            showMessage("确认密码不一致!");
            return;
        }
        hideSoftInput();
        mPresenter.register(phoneNumber,password,verificationCode,nickname,"");
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
    public void loginSuccessful(String id) {
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
