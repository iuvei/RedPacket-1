package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.ForgetPayPasswordModule;
import com.ooo.main.mvp.contract.ForgetPayPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.ForgetPayPasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ForgetPayPasswordModule.class, dependencies = AppComponent.class)
public interface ForgetPayPasswordComponent {
    void inject(ForgetPayPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPayPasswordComponent.Builder view(ForgetPayPasswordContract.View view);

        ForgetPayPasswordComponent.Builder appComponent(AppComponent appComponent);

        ForgetPayPasswordComponent build();
    }
}