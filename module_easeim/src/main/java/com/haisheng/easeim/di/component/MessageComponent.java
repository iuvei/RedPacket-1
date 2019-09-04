package com.haisheng.easeim.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.MessageModule;

import com.jess.arms.di.scope.FragmentScope;
import com.haisheng.easeim.mvp.ui.fragment.MessageFragment;

@FragmentScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    void inject(MessageFragment fragment);
}