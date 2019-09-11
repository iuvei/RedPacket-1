package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.NewMessageNotificationModule;
import com.ooo.main.mvp.contract.NewMessageNotificationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.NewMessageNotificationActivity;


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
@ActivityScope
@Component(modules = NewMessageNotificationModule.class, dependencies = AppComponent.class)
public interface NewMessageNotificationComponent {
    void inject(NewMessageNotificationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewMessageNotificationComponent.Builder view(NewMessageNotificationContract.View view);

        NewMessageNotificationComponent.Builder appComponent(AppComponent appComponent);

        NewMessageNotificationComponent build();
    }
}