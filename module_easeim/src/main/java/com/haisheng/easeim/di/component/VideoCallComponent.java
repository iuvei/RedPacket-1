package com.haisheng.easeim.di.component;

import dagger.Component;

import com.haisheng.easeim.mvp.ui.activity.VoiceCallActivity;
import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.VideoCallModule;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.VideoCallActivity;

@ActivityScope
@Component(modules = VideoCallModule.class, dependencies = AppComponent.class)
public interface VideoCallComponent {
    void inject(VideoCallActivity activity);
    void inject(VoiceCallActivity activity);
}