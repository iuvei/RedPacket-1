package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.YinLianRechargeModule;
import com.ooo.main.mvp.contract.YinLianRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.YinLianRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 14:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = YinLianRechargeModule.class, dependencies = AppComponent.class)
public interface YinLianRechargeComponent {
    void inject(YinLianRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        YinLianRechargeComponent.Builder view(YinLianRechargeContract.View view);

        YinLianRechargeComponent.Builder appComponent(AppComponent appComponent);

        YinLianRechargeComponent build();
    }
}