package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerWithdrawalComponent;
import com.ooo.main.mvp.contract.WithdrawalContract;
import com.ooo.main.mvp.presenter.WithdrawalPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 11:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawalActivity extends BaseSupportActivity <WithdrawalPresenter> implements WithdrawalContract.View {

    @BindView(R2.id.tv_blank_info)
    TextView tvBlankInfo;
    @BindView(R2.id.et_withrawal)
    EditText etWithrawal;
    @BindView(R2.id.iv_money)
    TextView ivMoney;
    @BindView(R2.id.iv_take_all)
    TextView ivTakeAll;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawalComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdrawal; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
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

    @OnClick({R2.id.iv_back, R2.id.tv_withdrawal_record, R2.id.tv_blank_info, R2.id.iv_take_all, R2.id.btn_next})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_withdrawal_record) {
            //提现记录
        } else if (i == R.id.tv_blank_info) {
            //银行信息

        } else if (i == R.id.iv_take_all) {
            //全部提取
        } else if (i == R.id.btn_next) {
            //下一步
            String money = etWithrawal.getText ().toString ().trim ();
            if (TextUtils.isEmpty ( money )){
                ToastUtils.showShort ( "请输入金额" );
                return;
            }
            if (money.indexOf ( "." )>0){
                ToastUtils.showShort ( "提现金额不得包含小数" );
                return;
            }
            if (ConvertNumUtils.stringToInt ( money )<100){
                ToastUtils.showShort ( "最低提现100元" );
                return;
            }
        }
    }
}
