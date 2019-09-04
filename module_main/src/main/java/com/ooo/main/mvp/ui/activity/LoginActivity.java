package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerLoginComponent;
import com.ooo.main.mvp.contract.LoginContract;
import com.ooo.main.mvp.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/27/2019 14:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @Inject
    AppManager mAppManager;

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
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R2.id.btn_login, R2.id.tv_forgot_pwd})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_login) {
            login();

        } else if (i == R.id.tv_forgot_pwd) {
            openActivity(ResetPasswordActivity.class);
        }
    }

    private void login(){
        String phoneNumber = etPhone.getText().toString();
        if(!RegexUtils.isMobileSimple(phoneNumber)){
            showMessage("手机号不能为空/不可用!");
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            showMessage("密码不能为空/不可用!");
            return;
        }

        mPresenter.login(phoneNumber,password);
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void sendSmsSuccessful() {

    }

    @Override
    public void sendSmsFail() {

    }

    @Override
    public void loginSuccessful() {
        launchActivity(new Intent(this, MainActivity.class));
        mAppManager.killAll(MainActivity.class);
    }
}
