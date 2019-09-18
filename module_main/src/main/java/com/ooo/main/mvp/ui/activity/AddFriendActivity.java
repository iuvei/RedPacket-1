package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerAddFriendComponent;
import com.ooo.main.mvp.contract.AddFriendContract;
import com.ooo.main.mvp.presenter.AddFriendPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 11:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.ADD_FRIEND_ACTIVITY)
public class AddFriendActivity extends BaseSupportActivity <AddFriendPresenter> implements AddFriendContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.iv_address_book)
    ImageView ivAddressBook;
    @BindView(R2.id.tv_address_book)
    TextView tvAddressBook;
    @BindView(R2.id.rl_address_book)
    RelativeLayout rlAddressBook;
    @BindView(R2.id.iv_scan)
    ImageView ivScan;
    @BindView(R2.id.tv_scan)
    TextView tvScan;
    @BindView(R2.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R2.id.iv_contact)
    ImageView ivContact;
    @BindView(R2.id.tv_contact)
    TextView tvContact;
    @BindView(R2.id.rl_contact)
    RelativeLayout rlContact;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddFriendComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
        ARouter.getInstance ().inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_friend; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "添加好友" );
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

    @OnClick({R2.id.iv_back, R2.id.rl_address_book, R2.id.rl_scan, R2.id.rl_contact})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.rl_address_book) {
            //手机通讯录匹配
            openActivity ( ContactFriendActivity.class );
        } else if (i == R.id.rl_scan) {
            //扫一扫
            QrConfig qrConfig = new QrConfig.Builder ()
                    .setLooperWaitTime ( 5 * 1000 )//连续扫描间隔时间
                    .create ();
            QrManager.getInstance ().init ( qrConfig ).startScan ( this, new QrManager.OnScanResultCallback () {
                @Override
                public void onScanSuccess(ScanResult result) {
                    Log.e ( TAG, "onScanSuccess: " + result );
                    Toast.makeText ( AddFriendActivity.this, "内容：" + result.getContent ()
                            + "  类型：" + result.getType (), Toast.LENGTH_SHORT ).show ();
                }
            } );
        } else if (i == R.id.rl_contact) {
            //邀请手机联系人
            openActivity ( InviteContactActivity.class );
        }
    }
}
