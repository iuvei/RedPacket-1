package com.ooo.redpacket58;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.ArmsUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import timber.log.Timber;

public class MyApplication extends BaseApplication {

    public static Context applicationContext;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        FlowManager.init(this);
        super.onCreate();
        applicationContext = this;
        instance = this;
        if (BuildConfig.LOG_DEBUG) {//Timber日志打印
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            RetrofitUrlManager.getInstance().setDebug(true);
        }
        ARouter.init(this); // 尽可能早,推荐在Application中初始化
        UserPreferenceManager.init(this);
        Utils.init(this);
        ToastUtils.setGravity(Gravity.CENTER,0,0);
        ToastUtils.setMsgColor(ContextCompat.getColor(this,android.R.color.black));

        Intent intent=new Intent(this,CheckExitService.class);
        startService(intent);

        //leakCanary内存泄露检查
        ArmsUtils.obtainAppComponentFromContext(this).extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(this) : RefWatcher.DISABLED);
    }

}
