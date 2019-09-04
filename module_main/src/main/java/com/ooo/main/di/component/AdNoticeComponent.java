package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.AdNoticeModule;
import com.ooo.main.mvp.contract.AdNoticeContract;

import com.jess.arms.di.scope.FragmentScope;
import com.ooo.main.mvp.ui.fragment.GameFragment;
import com.ooo.main.mvp.ui.fragment.MeesageFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 10:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AdNoticeModule.class, dependencies = AppComponent.class)
public interface AdNoticeComponent {
    void inject(GameFragment fragment);
    void inject(MeesageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AdNoticeComponent.Builder view(AdNoticeContract.View view);

        AdNoticeComponent.Builder appComponent(AppComponent appComponent);

        AdNoticeComponent build();
    }
}