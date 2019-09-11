package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.CommisonModule;
import com.ooo.main.mvp.contract.CommisonContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.CommisonActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommisonModule.class, dependencies = AppComponent.class)
public interface CommisonComponent {
    void inject(CommisonActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommisonComponent.Builder view(CommisonContract.View view);

        CommisonComponent.Builder appComponent(AppComponent appComponent);

        CommisonComponent build();
    }
}