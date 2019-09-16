package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.ChatDetailsModule;
import com.haisheng.easeim.mvp.contract.ChatDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.ChatDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 16:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChatDetailsModule.class, dependencies = AppComponent.class)
public interface ChatDetailsComponent {
    void inject(ChatDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChatDetailsComponent.Builder view(ChatDetailsContract.View view);

        ChatDetailsComponent.Builder appComponent(AppComponent appComponent);

        ChatDetailsComponent build();
    }
}