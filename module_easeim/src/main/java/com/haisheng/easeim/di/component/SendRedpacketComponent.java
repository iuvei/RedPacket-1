package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.haisheng.easeim.mvp.ui.activity.SendGunControlRedpacketActivity;
import com.haisheng.easeim.mvp.ui.activity.SendNiuniuRedpacketActivity;
import com.haisheng.easeim.mvp.ui.activity.SendWelfarRedpacketActivity;
import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.SendRedpacketModule;
import com.haisheng.easeim.mvp.contract.SendRedpacketContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.SendMineRedpacketActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 18:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SendRedpacketModule.class, dependencies = AppComponent.class)
public interface SendRedpacketComponent {
    void inject(SendMineRedpacketActivity activity);
    void inject(SendWelfarRedpacketActivity activity);
    void inject(SendNiuniuRedpacketActivity activity);
    void inject(SendGunControlRedpacketActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SendRedpacketComponent.Builder view(SendRedpacketContract.View view);

        SendRedpacketComponent.Builder appComponent(AppComponent appComponent);

        SendRedpacketComponent build();
    }
}
