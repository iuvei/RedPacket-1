package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.PayManagerModule;
import com.ooo.main.mvp.contract.PayManagerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.PayManagerActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PayManagerModule.class, dependencies = AppComponent.class)
public interface PayManagerComponent {
    void inject(PayManagerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PayManagerComponent.Builder view(PayManagerContract.View view);

        PayManagerComponent.Builder appComponent(AppComponent appComponent);

        PayManagerComponent build();
    }
}