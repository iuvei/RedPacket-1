package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BillingInfoModule;
import com.ooo.main.mvp.contract.BillingInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BillingInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 16:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BillingInfoModule.class, dependencies = AppComponent.class)
public interface BillingInfoComponent {
    void inject(BillingInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BillingInfoComponent.Builder view(BillingInfoContract.View view);

        BillingInfoComponent.Builder appComponent(AppComponent appComponent);

        BillingInfoComponent build();
    }
}