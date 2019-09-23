package com.haisheng.easeim.di.module;

import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.http.CommonModel;

import com.haisheng.easeim.mvp.contract.ChatContract;
import com.haisheng.easeim.mvp.model.ChatModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;


@Module
public class ChatModule {
    private ChatContract.View view;

    /**
     * 构建ChatModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChatModule(ChatContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChatContract.View provideChatView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChatContract.Model provideChatModel(ChatModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    public RedpacketModel provideRedpacketModel(IRepositoryManager iRepositoryManager) {
        return new RedpacketModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public ChatRoomModel provideChatRoomModel(IRepositoryManager iRepositoryManager) {
        return new ChatRoomModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public CommonModel provideCommonModel(IRepositoryManager iRepositoryManager) {
        return new CommonModel(iRepositoryManager);
    }
}
