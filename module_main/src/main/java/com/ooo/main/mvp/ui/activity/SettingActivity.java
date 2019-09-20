package com.ooo.main.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.haisheng.easeim.app.IMHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerSettingComponent;
import com.ooo.main.mvp.contract.SettingContract;
import com.ooo.main.mvp.presenter.SettingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/05/2019 18:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseSupportActivity <SettingPresenter> implements SettingContract.View {

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_safe)
    TextView tvSafe;
    @BindView(R2.id.layout_safe)
    LinearLayout layoutSafe;
    @BindView(R2.id.layout_notification)
    LinearLayout layoutNotification;
    @BindView(R2.id.layout_clear)
    LinearLayout layoutClear;
    @BindView(R2.id.tv_day_time)
    TextView tvDayTime;
    @BindView(R2.id.tv_record_time)
    TextView tvRecordTime;
    @BindView(R2.id.layout_record_time)
    LinearLayout layoutRecordTime;
    @BindView(R2.id.tv_version)
    TextView tvVersion;
    @BindView(R2.id.layout_about)
    LinearLayout layoutAbout;
    @BindView(R2.id.tv_logout)
    TextView tvLogout;
    @Inject
    AppManager mAppManager;
    private BaseDialog dialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "设置" );
        tvVersion.setText ( AppUtils.getAppVersionName () );
        String messageSaveTime = SpUtils.getValue ( this,"messageSaveTime","7天" );
        tvDayTime.setText ( "保留"+messageSaveTime+"的聊天记录" );
        tvRecordTime.setText ( messageSaveTime);
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

    @OnClick({R2.id.iv_back, R2.id.layout_safe, R2.id.layout_notification, R2.id.layout_clear,
            R2.id.layout_record_time, R2.id.layout_about,R2.id.tv_logout})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.layout_safe) {
            //账号与安全
            openActivity ( ProtectAccountActivity.class );
        } else if (i == R.id.layout_notification) {
            //新消息通知
            openActivity ( NewMessageNotificationActivity.class );
        } else if (i == R.id.layout_clear) {
            //清除所有缓存与聊天记录
            showClearCacheAndMessage();
        } else if (i == R.id.layout_record_time) {
            //聊天记录
            talkMessageDialg();
        } else if (i == R.id.layout_about) {
            //关于我们
            openActivity ( AboutUsActivity.class );
        } else if (i == R.id.tv_logout) {
            //退出登录
            showEditDialog();
        }
    }

    //清除缓存与聊天记录
    private void showClearCacheAndMessage() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "是否清除所有缓存与聊天记录？" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                        IMHelper.getInstance ().delectAllMessage ();
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

    //保存聊天记录
    private void talkMessageDialg() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_message_talking, true, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvSevenDay = layout.findViewById ( R.id.tv_seven_day);
                TextView tvHalfMonth = layout.findViewById ( R.id.tv_half_month );
                TextView tvOneMonth = layout.findViewById ( R.id.tv_one_month );
                TextView tvForever = layout.findViewById ( R.id.tv_forever );
                tvSevenDay.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDayTime.setText ( "七天" );
                        tvRecordTime.setText ( "七天" );
                        SpUtils.put ( SettingActivity.this,"messageSaveTime","七天" );
                    }
                } );
                tvHalfMonth.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDayTime.setText ( "十五天" );
                        tvRecordTime.setText ( "十五天" );
                        SpUtils.put ( SettingActivity.this,"messageSaveTime","十五天" );
                    }
                } );
                tvOneMonth.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDayTime.setText ( "一个月" );
                        tvRecordTime.setText ( "一个月" );
                        SpUtils.put ( SettingActivity.this,"messageSaveTime","一个月" );
                    }
                } );
                tvForever.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDayTime.setText ( "永久" );
                        tvRecordTime.setText ( "永久" );
                        SpUtils.put ( SettingActivity.this,"messageSaveTime","永久" );
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }

    private void showEditDialog() {
        new AlertDialog.Builder ( mContext )
                .setMessage ( "是否确认退出？" )
                .setPositiveButton ( "退出", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss ();
                        mPresenter.logout ();
                    }
                } )
                .setNegativeButton ( "取消", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel ();
                    }
                } ).create ().show ();
    }

    @Override
    public void logoutSuccess() {
        UserPreferenceManager.getInstance ().removeCurrentUserInfo ();
        launchActivity ( new Intent ( this, LoginActivity.class ) );
        mAppManager.killAll ( LoginActivity.class );
    }
}
