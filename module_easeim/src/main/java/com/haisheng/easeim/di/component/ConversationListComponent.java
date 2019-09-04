package com.haisheng.easeim.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.ConversationListModule;

import com.jess.arms.di.scope.FragmentScope;
import com.haisheng.easeim.mvp.ui.fragment.ConversationListFragment;

@FragmentScope
@Component(modules = ConversationListModule.class, dependencies = AppComponent.class)
public interface ConversationListComponent {
    void inject(ConversationListFragment fragment);
}