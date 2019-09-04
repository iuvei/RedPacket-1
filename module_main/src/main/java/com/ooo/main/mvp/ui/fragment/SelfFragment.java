package com.ooo.main.mvp.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.ooo.main.mvp.ui.activity.BillListActivity;
import com.ooo.main.mvp.ui.activity.LoginActivity;
import com.ooo.main.mvp.ui.activity.LoginAdActivity;
import com.ooo.main.mvp.ui.activity.MemberInfoActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
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
public class SelfFragment extends BaseSupportFragment<SelfPresenter> implements SelfContract.View, OnRefreshListener {

    @BindView(R2.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.iv_sex)
    ImageView ivSex;
    @BindView(R2.id.tv_user_id)
    TextView tvUserId;
    @BindView(R2.id.tv_balance)
    TextView tvBalance;
    @BindView(R2.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R2.id.tv_version)
    TextView tvVersion;

    @Inject
    AppManager mAppManager;

    private MemberInfo mMemberInfo;

    public static SelfFragment newInstance() {
        SelfFragment fragment = new SelfFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.initDatas();
    }

    @OnClick({R2.id.rl_member_info, R2.id.tv_proxy_center, R2.id.tv_share_make_money, R2.id.ll_invite_code, R2.id.ll_recharge_center, R2.id.ll_withdraw_center, R2.id.ll_bill_records,
            R2.id.ll_help_center, R2.id.ll_app_version, R2.id.ll_setting, R2.id.ll_exit,R2.id.ibtn_copy_invite_code})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.rl_member_info) {
            MemberInfoActivity.start(_mActivity,mMemberInfo);

        } else if (i == R.id.tv_proxy_center) {
        } else if (i == R.id.tv_share_make_money) {
        } else if (i == R.id.ibtn_copy_invite_code) {
        } else if (i == R.id.ll_invite_code) {
        } else if (i == R.id.ll_recharge_center) {
        } else if (i == R.id.ll_withdraw_center) {
        } else if (i == R.id.ll_bill_records) {
            launchActivity(new Intent(mContext, BillListActivity.class));

        } else if (i == R.id.ll_help_center) {
        } else if (i == R.id.ll_app_version) {
        } else if (i == R.id.ll_setting) {
        } else if (i == R.id.ll_exit) {
            showEditDialog();
        }
    }

    @Override
    public void refreshMemberInfo(MemberInfo memberInfo) {
        mMemberInfo = memberInfo;
        ImageLoader.displayHeaderImage(mContext,memberInfo.getAvatarUrl(),ivAvatar);
        tvNickname.setText(memberInfo.getNickname());
        tvUserId.setText(String.format("账号:%d",memberInfo.getId()));
        tvBalance.setText(String.format("余额:%.2f元",memberInfo.getBalance()));
        tvInviteCode.setText(memberInfo.getInviteCode());
    }

    @Override
    public void logoutSuccess() {
        UserPreferenceManager.getInstance().removeCurrentUserInfo();
        launchActivity(new Intent(_mActivity, LoginAdActivity.class));
        mAppManager.killAll(LoginAdActivity.class);
    }

    private void showEditDialog(){
        new AlertDialog.Builder(mContext)
                .setMessage("是否确认退出？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       mPresenter.logout();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode != RESULT_OK) return;
        mPresenter.requestDatas();
    }
}
