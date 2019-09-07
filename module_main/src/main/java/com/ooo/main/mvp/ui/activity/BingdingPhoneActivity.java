package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerBingdingPhoneComponent;
import com.ooo.main.mvp.contract.BingdingPhoneContract;
import com.ooo.main.mvp.presenter.BingdingPhonePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.CountDownUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 17:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BingdingPhoneActivity extends BaseActivity <BingdingPhonePresenter> implements BingdingPhoneContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    private CountDownUtils mCountDownUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBingdingPhoneComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bingding_phone; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus(this);
        StatusBarUtils.setStatusBarDarkTheme ( this,true );
        tvTitle.setText ( "绑定手机号" );
        mCountDownUtils = new CountDownUtils (tvGetCode);
        mCountDownUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }
    private void sendSms(){
        String phoneNumber = etPhone.getText().toString();
        if(TextUtils.isEmpty ( phoneNumber )){
            ToastUtils.showShort ("手机号不能为空!");
            etPhone.requestFocus();
            return;
        }
        ToastUtils.showShort ( "短信下发成功，请注意查收" );
        mCountDownUtils.start ();
        mPresenter.sendSms(phoneNumber,true);
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

    @OnClick({R2.id.iv_back,R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.btn_sure) {//确定
            String phoneNum = etPhone.getText ().toString ().trim ();
            String phoneCode = etCode.getText ().toString ().trim ();
            if (TextUtils.isEmpty ( phoneNum )) {
                ToastUtils.showShort ( "请输入手机号码" );
                return;
            }
            if (TextUtils.isEmpty ( phoneCode )) {
                ToastUtils.showShort ( "请输入验证码" );
                return;
            }
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
}
