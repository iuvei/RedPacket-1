package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.GroupTalkingModule;
import com.ooo.main.mvp.contract.GroupTalkingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.GroupTalkingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 10:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupTalkingModule.class, dependencies = AppComponent.class)
public interface GroupTalkingComponent {
    void inject(GroupTalkingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupTalkingComponent.Builder view(GroupTalkingContract.View view);

        GroupTalkingComponent.Builder appComponent(AppComponent appComponent);

        GroupTalkingComponent build();
    }
}