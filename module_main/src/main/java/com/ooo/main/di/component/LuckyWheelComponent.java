package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.LuckyWheelModule;
import com.ooo.main.mvp.contract.LuckyWheelContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.LuckyWheelActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 17:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LuckyWheelModule.class, dependencies = AppComponent.class)
public interface LuckyWheelComponent {
    void inject(LuckyWheelActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LuckyWheelComponent.Builder view(LuckyWheelContract.View view);

        LuckyWheelComponent.Builder appComponent(AppComponent appComponent);

        LuckyWheelComponent build();
    }
}