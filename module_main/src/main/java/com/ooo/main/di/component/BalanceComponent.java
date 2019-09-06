package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BalanceModule;
import com.ooo.main.mvp.contract.BalanceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BalanceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BalanceModule.class, dependencies = AppComponent.class)
public interface BalanceComponent {
    void inject(BalanceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BalanceComponent.Builder view(BalanceContract.View view);

        BalanceComponent.Builder appComponent(AppComponent appComponent);

        BalanceComponent build();
    }
}