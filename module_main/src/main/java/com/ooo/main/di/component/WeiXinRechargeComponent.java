package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.WeiXinRechargeModule;
import com.ooo.main.mvp.contract.WeiXinRechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.WeiXinRechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 14:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WeiXinRechargeModule.class, dependencies = AppComponent.class)
public interface WeiXinRechargeComponent {
    void inject(WeiXinRechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WeiXinRechargeComponent.Builder view(WeiXinRechargeContract.View view);

        WeiXinRechargeComponent.Builder appComponent(AppComponent appComponent);

        WeiXinRechargeComponent build();
    }
}