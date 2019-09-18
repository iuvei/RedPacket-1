package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerWithdrawalInfoComponent;
import com.ooo.main.mvp.contract.WithdrawalInfoContract;
import com.ooo.main.mvp.model.entity.WithRecordBean;
import com.ooo.main.mvp.presenter.WithdrawalInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 15:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawalInfoActivity extends BaseActivity <WithdrawalInfoPresenter> implements WithdrawalInfoContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_take_money)
    TextView tvTakeMoney; //提现金额
    @BindView(R2.id.tv_get_money)
    TextView tvGetMoney;  //到账金额
    @BindView(R2.id.tv_status)
    TextView tvStatus;   //提现状态
    @BindView(R2.id.tv_take_money_time)
    TextView tvTakeMoneyTime;  //提现时间
    @BindView(R2.id.tv_get_money_account)
    TextView tvGetMoneyAccount; //到账账号
    private WithRecordBean.ResultBean.ListBean recordBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawalInfoComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdrawal_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "提现详情" );
        recordBean = (WithRecordBean.ResultBean.ListBean) getIntent ().getSerializableExtra ( "info" );
        if (recordBean!=null){
            tvGetMoney.setText ( recordBean.getReagold () );
            tvGetMoneyAccount.setText ( "提现到"+recordBean.getCardopen ()+"("+recordBean.getCardCodeScreat ()+")" );
            tvStatus.setText ( recordBean.getStatusValue () );
            tvTakeMoney.setText ( recordBean.getGold () );
            tvTakeMoneyTime.setText ( recordBean.getAddtime () );
        }
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

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish ();
    }
}
