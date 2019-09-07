package com.ooo.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerSelfComponent;
import com.ooo.main.mvp.contract.SelfContract;
import com.ooo.main.mvp.model.entity.MemberInfo;
import com.ooo.main.mvp.presenter.SelfPresenter;
import com.ooo.main.mvp.ui.activity.BalanceActivity;
import com.ooo.main.mvp.ui.activity.BillingDetailsActivity;
import com.ooo.main.mvp.ui.activity.LoginActivity;
import com.ooo.main.mvp.ui.activity.SettingActivity;
import com.ooo.main.mvp.ui.activity.UserInfoActivity;
import com.ooo.main.mvp.ui.activity.WithdrawalActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelfFragment extends BaseSupportFragment <SelfPresenter> implements SelfContract.View, OnRefreshListener {

    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.tv_user_id)
    TextView tvUserId;

    @Inject
    AppManager mAppManager;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_star)
    ImageView ivStar;
    @BindView(R2.id.tv_balance)
    TextView tvBalance;
    @BindView(R2.id.ll_customer_service)
    LinearLayout llCustomerService;
    @BindView(R2.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R2.id.ll_self_withdrawal)
    LinearLayout llSelfWithdrawal;
    @BindView(R2.id.self_bill)
    LinearLayout selfBill;
    @BindView(R2.id.ll_setting)
    LinearLayout llSetting;
    Unbinder unbinder;

    private MemberInfo mMemberInfo;

    public static SelfFragment newInstance() {
        SelfFragment fragment = new SelfFragment ();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_self, container, false );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText ( "我的" );
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible ();
        mPresenter.initDatas ();
    }


    @Override
    public void refreshMemberInfo(MemberInfo memberInfo) {
        mMemberInfo = memberInfo;
        ImageLoader.displayHeaderImage ( mContext, memberInfo.getAvatarUrl (), ivAvatar );
        tvNickname.setText ( memberInfo.getNickname () );
        tvUserId.setText ( String.format ( "%d", memberInfo.getId () ) );
        tvBalance.setText(String.format("%.2f",memberInfo.getBalance()));
    }

    @Override
    public void logoutSuccess() {
        UserPreferenceManager.getInstance ().removeCurrentUserInfo ();
        launchActivity ( new Intent ( _mActivity, LoginActivity.class ) );
        mAppManager.killAll ( LoginActivity.class );
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas ();
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult ( requestCode, resultCode, data );
        if (requestCode != RESULT_OK) return;
        mPresenter.requestDatas ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView ( inflater, container, savedInstanceState );
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
    }

    @OnClick({R2.id.rl_member_info,R2.id.ll_balance, R2.id.ll_customer_service, R2.id.ll_recharge,
            R2.id.ll_self_withdrawal, R2.id.self_bill, R2.id.ll_setting})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.rl_member_info) {
            //个人详情
            startActivity ( new Intent ( getActivity (), UserInfoActivity.class ) );
        }else if (i == R.id.ll_balance){
            //余额
            startActivity ( new Intent ( getActivity (), BalanceActivity.class ) );
        } else if (i == R.id.ll_customer_service) {
            //客服
        } else if (i == R.id.ll_recharge) {
            //充值
        } else if (i == R.id.ll_self_withdrawal) {
            //提现
            startActivity ( new Intent ( getActivity (), WithdrawalActivity.class ) );
        } else if (i == R.id.self_bill) {
            //
            startActivity ( new Intent ( getActivity (), BillingDetailsActivity.class ) );
        } else if (i == R.id.ll_setting) {
            //设置
            startActivity ( new Intent ( getActivity (), SettingActivity.class ) );
        }
    }
}
