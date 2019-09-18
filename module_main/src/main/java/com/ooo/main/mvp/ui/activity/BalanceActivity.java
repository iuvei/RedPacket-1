package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerBalanceComponent;
import com.ooo.main.mvp.contract.BalanceContract;
import com.ooo.main.mvp.model.entity.TakeMoneyBean;
import com.ooo.main.mvp.presenter.BalancePresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BalanceActivity extends BaseSupportActivity <BalancePresenter> implements BalanceContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_balanceNum)
    TextView tvBalanceNum;
    @BindView(R2.id.tv_balanceDetail)
    TextView tvBalanceDetail;
    @BindView(R2.id.tv_payments)
    TextView tvPayments;
    @BindView(R2.id.tv_scan)
    TextView tvScan;
    @BindView(R2.id.tv_take_money)
    TextView tvTakeMoney;
    @BindView(R2.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R2.id.tv_blank_card)
    TextView tvBlankCard;
    @BindView(R2.id.tv_recharge_record)
    TextView tvRechargeRecord;
    private BaseDialog dialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBalanceComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_balance; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "钱包" );
        tvBalanceNum.setText ( AppLifecyclesImpl.getUserinfo ().getBalanceValue ()
        );
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
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }

    /**
     * 提取现金后刷新余额
     *{@link WithdrawalActivity#takeMoneySuccess(com.ooo.main.mvp.model.entity.TakeMoneyBean, java.lang.String)}
     */
    @Subscriber(tag="takeMoneySuccess")
    public void takeMoneySuccess(TakeMoneyBean bean){
        tvBalanceNum.setText ( AppLifecyclesImpl.getUserinfo ().getBalanceValue () );
    }

    @OnClick({R2.id.iv_back, R2.id.tv_balanceDetail, R2.id.tv_payments, R2.id.tv_scan,
            R2.id.tv_take_money, R2.id.tv_recharge, R2.id.tv_blank_card, R2.id.tv_recharge_record})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_balanceDetail) {
            //金额明细
            openActivity ( BillingDetailsActivity.class );
        } else if (i == R.id.tv_payments) {
            //收付款
            ToastUtils.showShort ( "暂未开通" );
        } else if (i == R.id.tv_scan) {
            //扫一扫
            QrConfig qrConfig = new QrConfig.Builder()
                    .setLooperWaitTime(5*1000)//连续扫描间隔时间
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(ScanResult result) {
                    Log.e(TAG, "onScanSuccess: "+result );
                    Toast.makeText(BalanceActivity.this, "内容："+result.getContent()
                            +"  类型："+result.getType(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (i == R.id.tv_take_money) {
            //提现
            if (!AppLifecyclesImpl.getUserinfo ().isCertification ()){
                showAuthDialog ();
                return;
            }
            startActivity ( new Intent ( this, WithdrawalActivity.class ) );
        } else if (i == R.id.tv_recharge) {
            //充值
            if (!AppLifecyclesImpl.getUserinfo ().isCertification ()){
                showAuthDialog ();
                return;
            }
        } else if (i == R.id.tv_blank_card) {
            //银行卡
            openActivity ( BlankCardActivity.class );
        } else if (i == R.id.tv_recharge_record) {
            //提现记录
            openActivity ( WithdrawalRecordActivity.class );
        }
    }

    private void showAuthDialog() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "未实名认证，是否去设置？" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                        openActivity ( CertificationActivity.class );
                    }
                } );
                layout.findViewById ( R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
            }
        } )
                .create ();
        dialog.show ();
    }
}
