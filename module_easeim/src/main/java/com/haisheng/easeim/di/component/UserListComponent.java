package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.UserListModule;
import com.haisheng.easeim.mvp.contract.UserListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.UserListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 15:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserListModule.class, dependencies = AppComponent.class)
public interface UserListComponent {
    void inject(UserListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserListComponent.Builder view(UserListContract.View view);

        UserListComponent.Builder appComponent(AppComponent appComponent);

        UserListComponent build();
    }
}
