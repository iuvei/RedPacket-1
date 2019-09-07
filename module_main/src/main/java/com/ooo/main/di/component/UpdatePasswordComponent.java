package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.UpdatePasswordModule;
import com.ooo.main.mvp.contract.UpdatePasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.UpdatePasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 18:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UpdatePasswordModule.class, dependencies = AppComponent.class)
public interface UpdatePasswordComponent {
    void inject(UpdatePasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UpdatePasswordComponent.Builder view(UpdatePasswordContract.View view);

        UpdatePasswordComponent.Builder appComponent(AppComponent appComponent);

        UpdatePasswordComponent build();
    }
}