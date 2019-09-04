package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.SelfModule;
import com.ooo.main.mvp.contract.SelfContract;

import com.jess.arms.di.scope.FragmentScope;
import com.ooo.main.mvp.ui.fragment.SelfFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SelfModule.class, dependencies = AppComponent.class)
public interface SelfComponent {
    void inject(SelfFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelfComponent.Builder view(SelfContract.View view);

        SelfComponent.Builder appComponent(AppComponent appComponent);

        SelfComponent build();
    }
}