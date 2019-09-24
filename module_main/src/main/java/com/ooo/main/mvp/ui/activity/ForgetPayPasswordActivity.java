package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerForgetPayPasswordComponent;
import com.ooo.main.mvp.contract.ForgetPayPasswordContract;
import com.ooo.main.mvp.model.entity.PublicBean;
import com.ooo.main.mvp.presenter.ForgetPayPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.CountDownUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * <p>
 * 忘记支付密码
 */
@Route ( path = RouterHub.MAIN_FORGETPAYPASSWORDACTIVITY)
public class ForgetPayPasswordActivity extends BaseSupportActivity <ForgetPayPasswordPresenter> implements ForgetPayPasswordContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.iv_clear_phone)
    ImageView ivClearPhone;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R2.id.et_new_password)
    EditText etNewPassword;
    @BindView(R2.id.iv_clear_password)
    ImageView ivClearPassword;
    private CountDownUtils mCountDownUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPayPasswordComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forget_pay_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "找回支付密码" );
        setListener();
    }

    private void setListener() {

        mCountDownUtils = new CountDownUtils (tvGetCode);
        mCountDownUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        etPhone.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPhone.getText ().toString ().trim ().length ()>0){
                    ivClearPhone.setVisibility ( View.VISIBLE );
                }else {
                    ivClearPhone.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        etNewPassword.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etNewPassword.getText ().toString ().trim ().length ()>0){
                    ivClearPassword.setVisibility ( View.VISIBLE );
                }else {
                    ivClearPassword.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }

    private void sendSms(){
        String phoneNumber = etPhone.getText().toString();
        if(TextUtils.isEmpty ( phoneNumber )){
            ToastUtils.showShort ("手机号不能为空!");
            etPhone.requestFocus();
            return;
        }
        ToastUtils.showShort ( "短信下发成功，请注意查收！" );
        mCountDownUtils.start ();
        mPresenter.sendSms(phoneNumber,false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {
        finish ();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.iv_clear_phone,R2.id.iv_clear_password,R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_clear_phone) {
            etPhone.setText ( "" );
        } else if (i == R.id.iv_clear_password) {
            etNewPassword.setText ( "" );
        } else if (i == R.id.btn_sure) {
            String phone = etPhone.getText ().toString ().trim ();
            String code = etCode.getText ().toString ().trim ();
            String password = etNewPassword.getText ().toString ().trim ();
            if (TextUtils.isEmpty ( phone )){
                ToastUtils.showShort ( "请输入手机号码" );
                return;
            }
            if (TextUtils.isEmpty ( code )){
                ToastUtils.showShort ( "请输入验证码" );
                return;
            }
            if (TextUtils.isEmpty ( password )){
                ToastUtils.showShort ( "请输入支付密码" );
                return;
            }
            mPresenter.findPayPassword ( phone,password,code );
        }
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
    public void findPayPasswordSuccess(PublicBean bean) {
        ToastUtils.showShort ( bean.getMsg () );
        finish ();
    }

    @Override
    public void findPayPasswordFail(PublicBean bean) {
        ToastUtils.showShort ( bean.getMsg () );
    }
}
