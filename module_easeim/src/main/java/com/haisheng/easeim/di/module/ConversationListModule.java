package com.haisheng.easeim.di.module;

import com.haisheng.easeim.mvp.model.ConversationModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.haisheng.easeim.mvp.contract.ConversationListContract;
import com.jess.arms.integration.IRepositoryManager;


@Module
public class ConversationListModule {
    private ConversationListContract.View view;

    /**
     * 构建ConversationListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ConversationListModule(ConversationListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ConversationListContract.View provideConversationListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public ConversationModel provideConversationModel(IRepositoryManager iRepositoryManager) {
        return new ConversationModel(iRepositoryManager);
    }
}