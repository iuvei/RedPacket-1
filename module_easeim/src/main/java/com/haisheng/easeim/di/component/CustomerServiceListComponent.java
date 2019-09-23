package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.CustomerServiceListModule;
import com.haisheng.easeim.mvp.contract.CustomerServiceListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.CustomerServiceListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CustomerServiceListModule.class, dependencies = AppComponent.class)
public interface CustomerServiceListComponent {
    void inject(CustomerServiceListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CustomerServiceListComponent.Builder view(CustomerServiceListContract.View view);

        CustomerServiceListComponent.Builder appComponent(AppComponent appComponent);

        CustomerServiceListComponent build();
    }
}
