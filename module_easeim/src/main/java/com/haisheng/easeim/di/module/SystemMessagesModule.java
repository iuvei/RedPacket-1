package com.haisheng.easeim.di.module;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.SystemMessage;
import com.hyphenate.util.DateUtils;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.haisheng.easeim.mvp.contract.SystemMessagesContract;
import com.haisheng.easeim.mvp.model.SystemMessagesModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Module
public class SystemMessagesModule {
    private SystemMessagesContract.View view;

    /**
     * 构建SystemMessagesModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SystemMessagesModule(SystemMessagesContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SystemMessagesContract.View provideSystemMessagesView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SystemMessagesContract.Model provideSystemMessagesModel(SystemMessagesModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SystemMessagesContract.View view) {
        return new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

    }

    @ActivityScope
    @Provides
    static RecyclerView.Adapter provideAdapter(List<SystemMessage> list) {
        BaseQuickAdapter adapter = new BaseQuickAdapter<SystemMessage, BaseViewHolder>(R.layout.item_system_message,list) {
            @Override
            protected void convert(BaseViewHolder helper, SystemMessage item) {
                helper.setText(R.id.tvTime,DateUtils.getTimestampString(new Date(item.getTime())));
                helper.setText(R.id.tvMessage, item.getMessage());
            }
        };
        return adapter;
    }

    @ActivityScope
    @Provides
    static List<SystemMessage> provideList() {
        return new ArrayList<>();
    }
}