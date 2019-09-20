package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.haisheng.easeim.app.IMHelper;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerNewMessageNotificationComponent;
import com.ooo.main.mvp.contract.NewMessageNotificationContract;
import com.ooo.main.mvp.presenter.NewMessageNotificationPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.SwitchButton;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 12:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NewMessageNotificationActivity extends BaseActivity <NewMessageNotificationPresenter> implements NewMessageNotificationContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.swb_newmessage)
    SwitchButton swbNewmessage;
    @BindView(R2.id.swb_message_detail)
    SwitchButton swbMessageDetail;
    @BindView(R2.id.swb_voice)
    SwitchButton swbVoice;
    @BindView(R2.id.swb_vibration)
    SwitchButton swbVibration;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewMessageNotificationComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_message_notification; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "新消息通知" );
        boolean newMessageNotification = SpUtils.getValue ( this,"newMessageNotification", true);
        boolean notificationDetails = SpUtils.getValue ( this,"notificationDetails", true);
        boolean voice = SpUtils.getValue ( this,"voice", true);
        boolean vibrate = SpUtils.getValue ( this,"vibrate", true);
        swbNewmessage.setChecked ( newMessageNotification );
        swbMessageDetail.setChecked ( notificationDetails );
        swbVoice.setChecked ( voice );
        swbVibration.setChecked ( vibrate );
        setListener();
    }

    private void setListener() {
        swbMessageDetail.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SpUtils.put ( NewMessageNotificationActivity.this,"notificationDetails", isChecked);
            }
        } );
        swbNewmessage.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                IMHelper.getInstance ().getModel ().setSettingMsgNotification ( isChecked );
                SpUtils.put ( NewMessageNotificationActivity.this,"newMessageNotification", isChecked);
            }
        } );
        swbVibration.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                IMHelper.getInstance ().getModel ().setSettingMsgVibrate ( isChecked );
                SpUtils.put ( NewMessageNotificationActivity.this,"vibrate", isChecked);
            }
        } );
        swbVoice.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                IMHelper.getInstance ().getModel ().setSettingMsgSound ( isChecked );
                SpUtils.put ( NewMessageNotificationActivity.this,"voice", isChecked);
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

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish ();
    }
}
