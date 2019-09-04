package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.haisheng.easeim.mvp.contract.GroupInfoContract;
import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.GroupInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.GroupInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupInfoModule.class, dependencies = AppComponent.class)
public interface GroupInfoComponent {
    void inject(GroupInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupInfoComponent.Builder view(GroupInfoContract.View view);

        GroupInfoComponent.Builder appComponent(AppComponent appComponent);

        GroupInfoComponent build();
    }
}