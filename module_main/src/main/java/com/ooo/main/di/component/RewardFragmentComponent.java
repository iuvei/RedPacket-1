package com.ooo.main.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.ooo.main.di.module.RewardFragmentModule;
import com.ooo.main.mvp.contract.RewardFragmentContract;
import com.ooo.main.mvp.ui.fragment.RewardFragment;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = RewardFragmentModule.class, dependencies = AppComponent.class)
public interface RewardFragmentComponent {
    void inject(RewardFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RewardFragmentComponent.Builder view(RewardFragmentContract.View view);

        RewardFragmentComponent.Builder appComponent(AppComponent appComponent);

        RewardFragmentComponent build();
    }
}