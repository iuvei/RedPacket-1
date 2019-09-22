package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.TurnToWeiXinRechargeModule;
import com.ooo.main.mvp.contract.TurnToWeiXinRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.TurnToWeiXinRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 14:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TurnToWeiXinRechargeModule.class, dependencies = AppComponent.class)
public interface TurnToWeiXinRechargeComponent {
    void inject(TurnToWeiXinRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TurnToWeiXinRechargeComponent.Builder view(TurnToWeiXinRechargeContract.View view);

        TurnToWeiXinRechargeComponent.Builder appComponent(AppComponent appComponent);

        TurnToWeiXinRechargeComponent build();
    }
}