package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.AlipyRechargeModule;
import com.ooo.main.mvp.contract.AlipyRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.AlipyRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 19:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlipyRechargeModule.class, dependencies = AppComponent.class)
public interface AlipyRechargeComponent {
    void inject(AlipyRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AlipyRechargeComponent.Builder view(AlipyRechargeContract.View view);

        AlipyRechargeComponent.Builder appComponent(AppComponent appComponent);

        AlipyRechargeComponent build();
    }
}