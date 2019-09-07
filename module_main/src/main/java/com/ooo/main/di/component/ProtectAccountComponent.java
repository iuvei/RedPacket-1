package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.ProtectAccountModule;
import com.ooo.main.mvp.contract.ProtectAccountContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.ProtectAccountActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 17:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ProtectAccountModule.class, dependencies = AppComponent.class)
public interface ProtectAccountComponent {
    void inject(ProtectAccountActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ProtectAccountComponent.Builder view(ProtectAccountContract.View view);

        ProtectAccountComponent.Builder appComponent(AppComponent appComponent);

        ProtectAccountComponent build();
    }
}