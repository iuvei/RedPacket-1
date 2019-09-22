package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerTurnToYinLianRechargeComponent;
import com.ooo.main.mvp.contract.TurnToYinLianRechargeContract;
import com.ooo.main.mvp.presenter.TurnToYinLianRechargePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.CopyUtil;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TurnToYinLianRechargeActivity extends BaseActivity <TurnToYinLianRechargePresenter> implements TurnToYinLianRechargeContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.tv_blank)
    TextView tvBlank;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_card_num)
    TextView tvCardNum;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_marke)
    TextView tvMarke;
    @BindView(R2.id.tv_payName)
    TextView tvPayName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTurnToYinLianRechargeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_turn_to_yin_lian_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "充值中心" );
        tvRight.setText ( "客服" );
        tvRight.setVisibility ( View.VISIBLE );
        String money = getIntent ().getStringExtra ( "money" );
        String name = getIntent ().getStringExtra ( "name" );
        tvPayName.setText ( name );
        mPresenter.getRechargeInfo ( money, "" );
    }

    public static void start(Context context, String money, String name) {
        Intent intent = new Intent ( context, TurnToYinLianRechargeActivity.class );
        intent.putExtra ( "money", money );
        intent.putExtra ( "name", name );
        context.startActivity ( intent );
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

    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.btn_copy_name, R2.id.btn_copy_card_num,
            R2.id.btn_copy_money, R2.id.btn_copy_marke, R2.id.btn_cancel, R2.id.btn_submit})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
        } else if (i == R.id.btn_copy_name) {
            CopyUtil.getInstance ().copyString ( this,tvName.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_card_num) {
            CopyUtil.getInstance ().copyString ( this,tvCardNum.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_money) {
            CopyUtil.getInstance ().copyString ( this,tvMoney.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_marke) {
            CopyUtil.getInstance ().copyString ( this,tvMarke.getText ().toString ().trim () );
        } else if (i == R.id.btn_cancel) {
            finish ();
        } else if (i == R.id.btn_submit) {
            String payCodeId = "";
            String payMoney = tvMoney.getText ().toString ().trim ();
            String payName = "";
            String payImg = "";
            mPresenter.submitRechargeInfo ( payCodeId, payMoney, payName, payImg );
        }
    }
}
