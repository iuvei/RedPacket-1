package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.haisheng.easeim.mvp.contract.CallListContract;
import com.haisheng.easeim.mvp.ui.activity.CallListActivity;
import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.CallListModule;

import com.jess.arms.di.scope.FragmentScope;
import com.haisheng.easeim.mvp.ui.fragment.CallListFragment;

@FragmentScope
@Component(modules = CallListModule.class, dependencies = AppComponent.class)
public interface CallListComponent {
    void inject(CallListFragment fragment);
    void inject(CallListActivity activity);
}