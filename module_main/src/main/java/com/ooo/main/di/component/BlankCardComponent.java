package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BlankCardModule;
import com.ooo.main.mvp.contract.BlankCardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BlankCardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BlankCardModule.class, dependencies = AppComponent.class)
public interface BlankCardComponent {
    void inject(BlankCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BlankCardComponent.Builder view(BlankCardContract.View view);

        BlankCardComponent.Builder appComponent(AppComponent appComponent);

        BlankCardComponent build();
    }
}