package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.MyContactListModule;
import com.haisheng.easeim.mvp.contract.MyContactListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.MyContactListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyContactListModule.class, dependencies = AppComponent.class)
public interface MyContactListComponent {
    void inject(MyContactListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyContactListComponent.Builder view(MyContactListContract.View view);

        MyContactListComponent.Builder appComponent(AppComponent appComponent);

        MyContactListComponent build();
    }
}