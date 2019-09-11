package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.CommissionListModule;
import com.ooo.main.mvp.contract.CommissionListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.CommissionListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 15:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommissionListModule.class, dependencies = AppComponent.class)
public interface CommissionListComponent {
    void inject(CommissionListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommissionListComponent.Builder view(CommissionListContract.View view);

        CommissionListComponent.Builder appComponent(AppComponent appComponent);

        CommissionListComponent build();
    }
}