package com.ooo.main.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.app.IMHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerMainComponent;
import com.ooo.main.mvp.contract.MainContract;
import com.ooo.main.mvp.model.entity.AdvertisingBean;
import com.ooo.main.mvp.model.entity.AppVersionBean;
import com.ooo.main.mvp.presenter.MainPresenter;
import com.ooo.main.mvp.ui.fragment.GameFragment;
import com.ooo.main.mvp.ui.fragment.RewardFragment;
import com.ooo.main.mvp.ui.fragment.SelfFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.ui.WebviewActivity;
import me.jessyan.armscomponent.commonres.utils.CommonMethod;
import me.jessyan.armscomponent.commonsdk.adapter.FragmentAdapter;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/28/2019 22:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseSupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R2.id.viewPager)
    ViewPager viewPager;
    @BindView(R2.id.iv_chat)
    ImageView ivChat;
    @BindView(R2.id.tv_unread_msg)
    TextView tvUnreadMsg;
    @BindView(R2.id.rl_chat)
    RelativeLayout rlChat;
    @BindView(R2.id.iv_group)
    ImageView ivGroup;
    @BindView(R2.id.iv_discover)
    ImageView ivDiscover;
    @BindView(R2.id.iv_me)
    ImageView ivMe;
    @BindView(R2.id.iv_game)
    ImageView ivGame;
    @BindView(R2.id.rl_animation)
    RelativeLayout rlAnimation;
    @BindView(R2.id.ll_group)
    LinearLayout llGroup;
    @BindView(R2.id.ll_discover)
    LinearLayout llDiscover;
    @BindView(R2.id.ll_me)
    LinearLayout llMe;

    private View mCurrentShowView,mCurrentItemView;
    private BaseDialog dialog;
    private BaseDialog updateDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus(this);
        StatusBarUtils.setStatusBarDarkTheme ( this,true );
        initFragments();
        requestPermission();
        AppLifecyclesImpl.getUserinfo ().setNickname ( CommonMethod.getNickNameForLocal ( ) );
        mPresenter.initUnreadIMMsgCountTotal();
        mPresenter.requestPermission();
        mPresenter.getAdvertising ();
        mPresenter.getAppVersion ();
    }

    public void requestPermission(){
        RxPermissions rxPermissions = new RxPermissions ( this );
        rxPermissions
                .request ( Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE )
                .subscribe ( granted -> {
                    if (!granted) { // Always true pre-M
                        ToastUtils.showShort ( "未授权权限，部分功能不能使用" );
                    }
                } );
    }

    private void initFragments() {
        //会话
        Fragment fragment1 = (Fragment) ARouter.getInstance()
                .build(RouterHub.IM_CONVERSATIONLISTFRAGMENT).navigation();
        //通讯录
        Fragment fragment4 = (Fragment) ARouter.getInstance()
                .build(RouterHub.IM_CONTACTFRAGMENT).navigation();
        //游戏
        Fragment fragment2 = GameFragment.newInstance();
        //发现界面
        Fragment fragment3 = RewardFragment.newInstance();
        //我的
        Fragment fragment5 = SelfFragment.newInstance();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment4);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment5);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        onViewClicked(rlChat);
                        break;
                    case 1:
                        onViewClicked(llGroup);
                        break;
                    case 2:
                        onViewClicked(rlAnimation);
                        break;
                    case 3:
                        onViewClicked(llDiscover);
                        break;
                    case 4:
                        onViewClicked(llMe);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //默认选中聊天
        mCurrentItemView = rlChat;
        mCurrentShowView = ivChat;
        mCurrentShowView.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(0);
    }

    @OnClick({R2.id.rl_chat, R2.id.ll_group, R2.id.ll_discover, R2.id.ll_me, R2.id.rl_animation})
    public void onViewClicked(View view) {
        if(mCurrentItemView.getId() == view.getId()) {
            return;
        }
        mCurrentItemView = view;

        int i = view.getId();
        mCurrentShowView.setVisibility(View.GONE);

        if (i == R.id.rl_chat) {
            viewPager.setCurrentItem(0);
            mCurrentShowView = ivChat;
            ivChat.setVisibility(View.VISIBLE);
        } else if (i == R.id.ll_group) {
            viewPager.setCurrentItem(1);
            mCurrentShowView = ivGroup;
            ivGroup.setVisibility(View.VISIBLE);

        } else if (i == R.id.ll_discover) {
            viewPager.setCurrentItem(3);
            mCurrentShowView = ivDiscover;
            ivDiscover.setVisibility(View.VISIBLE);
        } else if (i == R.id.ll_me) {
            viewPager.setCurrentItem(4);
            mCurrentShowView = ivMe;
            ivMe.setVisibility(View.VISIBLE);

        } else if (i == R.id.rl_animation) {
            viewPager.setCurrentItem(2);
            mCurrentShowView = ivGame;
//            mCurrentShowView = rlAnimation;
            ivGame.setVisibility ( View.VISIBLE );

        }

    }

    @Override
    public void setUnreadIMMsgCountTotal(int countTotal) {
        tvUnreadMsg.setVisibility(countTotal>=0 ? View.VISIBLE : View.INVISIBLE);
        if (countTotal == 0) {
            tvUnreadMsg.setText("");
            return;
        }
        if (countTotal>99){
            tvUnreadMsg.setText ( "..." );
            return;
        }
        tvUnreadMsg.setText ( countTotal+"" );
    }

    @Override
    public void getAppVersionSuccess(AppVersionBean.ResultBean result) {
        showVersionUpdateDialog(result);
    }

    @Override
    public void getAppVersionFail() {

    }

    @Override
    public void getAdvertisingSuccess(AdvertisingBean.ResultBean result) {
        showAdvertising(result);
    }

    private void showAdvertising(AdvertisingBean.ResultBean result) {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_advertising_info, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMessage = layout.findViewById ( R.id.tv_content );
                tvMessage.setText ( result.getAffiche () );
                layout.findViewById ( R.id.iv_colse ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                    }
                } );
            }
        } )
                .create ();
        dialog.show ();
    }

    @Override
    public void getAdvertisingFail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    //弹出版本更新
    private void showVersionUpdateDialog(AppVersionBean.ResultBean bean) {
        if (bean.isNeetUpdate ()) {
        updateDialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "请更新版本" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        //确定
                        WebviewActivity.start ( MainActivity.this,"更新版本",bean.getAndroidappurl () );
                    }
                } );
            }
        } )
                .create ();
            updateDialog.show ();
        }
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
        finish();
    }
}
