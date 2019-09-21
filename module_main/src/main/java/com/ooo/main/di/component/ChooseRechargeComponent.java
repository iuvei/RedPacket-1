package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.ChooseRechargeModule;
import com.ooo.main.mvp.contract.ChooseRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.ChooseRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseRechargeModule.class, dependencies = AppComponent.class)
public interface ChooseRechargeComponent {
    void inject(ChooseRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseRechargeComponent.Builder view(ChooseRechargeContract.View view);

        ChooseRechargeComponent.Builder appComponent(AppComponent appComponent);

        ChooseRechargeComponent build();
    }
}