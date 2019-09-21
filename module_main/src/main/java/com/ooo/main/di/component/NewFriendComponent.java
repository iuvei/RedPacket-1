package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.NewFriendModule;
import com.ooo.main.mvp.contract.NewFriendContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.NewFriendActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 09:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NewFriendModule.class, dependencies = AppComponent.class)
public interface NewFriendComponent {
    void inject(NewFriendActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewFriendComponent.Builder view(NewFriendContract.View view);

        NewFriendComponent.Builder appComponent(AppComponent appComponent);

        NewFriendComponent build();
    }
}