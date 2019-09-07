package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BillingDetailsModule;
import com.ooo.main.mvp.contract.BillingDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BillingDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 16:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BillingDetailsModule.class, dependencies = AppComponent.class)
public interface BillingDetailsComponent {
    void inject(BillingDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BillingDetailsComponent.Builder view(BillingDetailsContract.View view);

        BillingDetailsComponent.Builder appComponent(AppComponent appComponent);

        BillingDetailsComponent build();
    }
}