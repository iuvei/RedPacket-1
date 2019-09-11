package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.LuckyDrawModule;
import com.ooo.main.mvp.contract.LuckyDrawContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.LuckyDrawActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 18:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LuckyDrawModule.class, dependencies = AppComponent.class)
public interface LuckyDrawComponent {
    void inject(LuckyDrawActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LuckyDrawComponent.Builder view(LuckyDrawContract.View view);

        LuckyDrawComponent.Builder appComponent(AppComponent appComponent);

        LuckyDrawComponent build();
    }
}