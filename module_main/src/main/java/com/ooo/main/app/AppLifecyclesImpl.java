package com.ooo.main.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.BuildConfig;
import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.raizlabs.android.dbflow.config.FlowManager;
//import com.raizlabs.android.dbflow.config.module_mainGeneratedDatabaseHolder;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by MVPArmsTemplate
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    private static LoginResultInfo userinfo = new LoginResultInfo ();

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        FlowManager.initModule(module_mainGeneratedDatabaseHolder.class);
        //使用 RetrofitUrlManager 切换 BaseUrl
//        RetrofitUrlManager.getInstance().putDomain(Api.IM_DOMAIN_NAME, Api.IM_DOMAIN);
        //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码
        if (BuildConfig.IS_BUILD_MODULE) {
            //leakCanary内存泄露检查
            ArmsUtils.obtainAppComponentFromContext(application).extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    public static LoginResultInfo getUserinfo() {
        return userinfo;
    }

}
