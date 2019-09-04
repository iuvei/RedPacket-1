package com.haisheng.easeim.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.haisheng.easeim.di.module.ChatModule;
import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;

@ActivityScope
@Component(modules = ChatModule.class, dependencies = AppComponent.class)
public interface ChatComponent {
    void inject(ChatActivity activity);
}