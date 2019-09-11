package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.PayPasswordModule;
import com.ooo.main.mvp.contract.PayPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.PayPasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 09:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PayPasswordModule.class, dependencies = AppComponent.class)
public interface PayPasswordComponent {
    void inject(PayPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PayPasswordComponent.Builder view(PayPasswordContract.View view);

        PayPasswordComponent.Builder appComponent(AppComponent appComponent);

        PayPasswordComponent build();
    }
}