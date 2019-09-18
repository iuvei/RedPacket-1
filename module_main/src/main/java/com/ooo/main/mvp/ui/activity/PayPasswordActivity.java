package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerPayPasswordComponent;
import com.ooo.main.mvp.contract.PayPasswordContract;
import com.ooo.main.mvp.model.entity.PublicBean;
import com.ooo.main.mvp.presenter.PayPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 09:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * 支付密码
 */
public class PayPasswordActivity extends BaseActivity <PayPasswordPresenter> implements PayPasswordContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.iv_clear_password)
    ImageView ivClearPassword;
    @BindView(R2.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R2.id.iv_clear_confirm_password)
    ImageView ivClearConfirmPassword;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayPasswordComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "设置支付密码" );
        tvRight.setVisibility ( View.VISIBLE );
        tvRight.setText ( "确认" );
        setListener();
    }

    private void setListener() {
        etPassword.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPassword.getText ().toString ().trim ().length ()>0){
                    ivClearPassword.setVisibility ( View.VISIBLE );
                }else{
                    ivClearPassword.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        etConfirmPassword.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etConfirmPassword.getText ().toString ().trim ().length ()>0){
                    ivClearConfirmPassword.setVisibility ( View.VISIBLE );
                }else{
                    ivClearConfirmPassword.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
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

    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.iv_clear_password, R2.id.iv_clear_confirm_password})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
            //确认
            String password = etPassword.getText ().toString ().trim ();
            String confirmPassword = etConfirmPassword.getText ().toString ().trim ();
            if (!password.equals ( confirmPassword )){
                ToastUtils.showShort ( "两次密码不一致" );
                return;
            }
            if (password.length ()<6){
                ToastUtils.showShort ( "请输入6位数字密码" );
                return;
            }
            mPresenter.setPayPassword ( password,confirmPassword );
        } else if (i == R.id.iv_clear_password) {
            etPassword.setText ( "" );
        } else if (i == R.id.iv_clear_confirm_password) {
            etConfirmPassword.setText ( "" );
        }
    }

    @Override
    public void setPayPasswordSuccess(PublicBean bean) {
        ToastUtils.showShort ( bean.getMsg () );
        finish ();
    }

    @Override
    public void setPayPasswordFail(PublicBean bean) {
        ToastUtils.showShort ( bean.getMsg () );
    }
}
