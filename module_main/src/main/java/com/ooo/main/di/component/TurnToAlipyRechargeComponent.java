package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.TurnToAlipyRechargeModule;
import com.ooo.main.mvp.contract.TurnToAlipyRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.TurnToAlipyRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TurnToAlipyRechargeModule.class, dependencies = AppComponent.class)
public interface TurnToAlipyRechargeComponent {
    void inject(TurnToAlipyRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TurnToAlipyRechargeComponent.Builder view(TurnToAlipyRechargeContract.View view);

        TurnToAlipyRechargeComponent.Builder appComponent(AppComponent appComponent);

        TurnToAlipyRechargeComponent build();
    }
}