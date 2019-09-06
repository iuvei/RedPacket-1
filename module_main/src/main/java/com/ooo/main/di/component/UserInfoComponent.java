package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.UserInfoModule;
import com.ooo.main.mvp.contract.UserInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.UserInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 10:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserInfoModule.class, dependencies = AppComponent.class)
public interface UserInfoComponent {
    void inject(UserInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserInfoComponent.Builder view(UserInfoContract.View view);

        UserInfoComponent.Builder appComponent(AppComponent appComponent);

        UserInfoComponent build();
    }
}