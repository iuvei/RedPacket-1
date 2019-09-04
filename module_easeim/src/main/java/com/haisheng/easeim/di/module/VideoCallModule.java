package com.haisheng.easeim.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.haisheng.easeim.mvp.contract.VideoCallContract;
import com.haisheng.easeim.mvp.model.VideoCallModel;


@Module
public class VideoCallModule {
    private VideoCallContract.View view;

    /**
     * 构建VideoCallModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VideoCallModule(VideoCallContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VideoCallContract.View provideVideoCallView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VideoCallContract.Model provideVideoCallModel(VideoCallModel model) {
        return model;
    }
}