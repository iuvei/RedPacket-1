package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.AddFriendModule;
import com.ooo.main.mvp.contract.AddFriendContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.AddFriendActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 11:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddFriendModule.class, dependencies = AppComponent.class)
public interface AddFriendComponent {
    void inject(AddFriendActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddFriendComponent.Builder view(AddFriendContract.View view);

        AddFriendComponent.Builder appComponent(AppComponent appComponent);

        AddFriendComponent build();
    }
}