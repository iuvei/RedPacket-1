package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.ooo.main.app.MainConstants;

import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.component.im.service.IMService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.SpUtils;

public class WelcomeActivity extends BaseSupportActivity {


    @Autowired(name = RouterHub.IM_SERVICE_IMSERVICE)
    public IMService mIMService;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SpUtils.get(this, MainConstants.FIRST_START, false);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            startActivity(new Intent(this, GuideActivity.class));
            finish();
            return;
        }
        ARouter.getInstance().inject(this);
        // 如果不是第一次启动app，则正常显示启动屏
        new Handler().postDelayed(this::enterHomeActivity, 2000);
    }

    private void enterHomeActivity() {
        if(!mIMService.isLoggedIn()){
            openActivity(LoginActivity.class);
        }else{
            openActivity(MainActivity.class);
        }
        finish();
    }
}
