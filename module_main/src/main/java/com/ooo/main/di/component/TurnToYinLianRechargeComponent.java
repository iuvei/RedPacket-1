package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.TurnToYinLianRechargeModule;
import com.ooo.main.mvp.contract.TurnToYinLianRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.TurnToYinLianRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TurnToYinLianRechargeModule.class, dependencies = AppComponent.class)
public interface TurnToYinLianRechargeComponent {
    void inject(TurnToYinLianRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TurnToYinLianRechargeComponent.Builder view(TurnToYinLianRechargeContract.View view);

        TurnToYinLianRechargeComponent.Builder appComponent(AppComponent appComponent);

        TurnToYinLianRechargeComponent build();
    }
}