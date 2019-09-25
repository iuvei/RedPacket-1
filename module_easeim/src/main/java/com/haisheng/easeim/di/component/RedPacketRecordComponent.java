package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.RedPacketRecordModule;
import com.haisheng.easeim.mvp.contract.RedPacketRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.RedPacketRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/25/2019 12:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RedPacketRecordModule.class, dependencies = AppComponent.class)
public interface RedPacketRecordComponent {
    void inject(RedPacketRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RedPacketRecordComponent.Builder view(RedPacketRecordContract.View view);

        RedPacketRecordComponent.Builder appComponent(AppComponent appComponent);

        RedPacketRecordComponent build();
    }
}