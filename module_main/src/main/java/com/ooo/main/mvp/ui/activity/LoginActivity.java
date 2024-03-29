package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerLoginComponent;
import com.ooo.main.mvp.contract.LoginContract;
import com.ooo.main.mvp.presenter.LoginPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.CommonMethod;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonres.view.CodeView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

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
@Route ( path = RouterHub.MAIN_LOGINACTIVITY)
public class LoginActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.iv_clear_phone)
    ImageView ivClearPhone;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.iv_clear_password)
    ImageView ivClearPassword;
    @BindView(R2.id.tv_go_register)
    TextView ivGoRegister;
    @BindView(R2.id.ll_code)
    LinearLayout ll_code;
    @BindView(R2.id.codeview)
    CodeView codeview;
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
        StatusBarUtils.setTranslucentStatus(this);
        StatusBarUtils.setStatusBarDarkTheme ( this,true );
        String username = SpUtils.getValue ( this,"username","" );
        String password = SpUtils.getValue ( this,"password","" );
        Bundle bundle = getIntent ().getExtras ();
        if (bundle!=null) {
            boolean isLogout = bundle.getBoolean ( "logout" );
            if (isLogout) {
                mPresenter.logout ();
            }
        }
        etPhone.setText ( username );
        etPassword.setText ( password );
        /*
         * 获取到焦点的监听
         */
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivClearPassword.setVisibility ( View.GONE );
                if (hasFocus) {
                    //获取到焦点了
                    if (etPassword.getText ().length ()>0){
                        ivClearPassword.setVisibility ( View.VISIBLE );
                    }
                } else {
                    //失去焦点了
                }
            }
        });

        etPassword.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPassword.getText ().length ()>0){
                    ivClearPassword.setVisibility ( View.VISIBLE );
                }else{
                    ivClearPassword.setVisibility ( View.GONE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        /*
         * 获取到焦点的监听
         */
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivClearPhone.setVisibility ( View.GONE );
                if (hasFocus) {
                    //获取到焦点了
                    if (etPhone.getText ().length ()>0){
                        ivClearPhone.setVisibility ( View.VISIBLE );
                    }
                } else {
                    //失去焦点了
                }
            }
        });

        etPhone.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPhone.getText ().length ()>0){
                    ivClearPhone.setVisibility ( View.VISIBLE );
                }else{
                    ivClearPhone.setVisibility ( View.GONE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }
        @OnClick({R2.id.btn_login, R2.id.tv_forgot_pwd,R2.id.tv_go_register,R2.id.iv_clear_password,R2.id.iv_clear_phone,R2.id.codeview})
        public void onViewClicked(View view) {
            int i = view.getId();
            if (i == R.id.btn_login) {
                login();

            } else if (i == R.id.tv_forgot_pwd) {
                openActivity(ResetPasswordActivity.class);
            }else if (i == R.id.tv_go_register){
                openActivity ( RegisterActivity.class );
            }else if (i == R.id.iv_clear_password){
                etPassword.setText ( "" );
            }else if (i==R.id.iv_clear_phone){
                etPhone.setText ( "" );
            } else if (i==R.id.codeview){
                codeview.settingPaint ();
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
                showMessage("密码不能为空");
                return;
            }

            if (ll_code.getVisibility () == View.VISIBLE){
                String code = etCode.getText().toString().trim ();
                if (TextUtils.isEmpty(code)) {
                    etCode.requestFocus();
                    showMessage("验证码不能为空");
                    return;
                }else{
                    if (!code.equalsIgnoreCase ( codeview.getCode () )){
                        ToastUtils.showShort ( "验证码不正确" );
                        return;
                    }
                }
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
        public void loginSuccessful(String id, long uid) {
            SpUtils.put ( this,"username",etPhone.getText ().toString ().trim () );
            SpUtils.put ( this,"password",etPassword.getText ().toString ().trim () );
            CommonMethod.setHxidForLocal ( id );
            CommonMethod.setNickNameForLocal ( AppLifecyclesImpl.getUserinfo ().getNickname () );
            CommonMethod.setUidForLocal ( uid+"" );
            launchActivity(new Intent(this, MainActivity.class));
            mAppManager.killAll(MainActivity.class);
        }

    @Subscriber(tag = "login")
    public void loginFaile(String s){
        ll_code.setVisibility ( View.VISIBLE );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }
}
