package com.haisheng.easeim.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.SystemMessagesModule;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.SystemMessagesActivity;

@ActivityScope
@Component(modules = SystemMessagesModule.class, dependencies = AppComponent.class)
public interface SystemMessagesComponent {
    void inject(SystemMessagesActivity activity);
}