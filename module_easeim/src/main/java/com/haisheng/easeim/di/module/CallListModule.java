package com.haisheng.easeim.di.module;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.haisheng.easeim.mvp.ui.adapter.CallRecordAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.haisheng.easeim.mvp.contract.CallListContract;
import com.haisheng.easeim.mvp.model.CallListModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class CallListModule {
    private CallListContract.View view;

    /**
     * 构建CallListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CallListModule(CallListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CallListContract.View provideCallListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CallListContract.Model provideCallListModel(CallListModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CallListContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static RecyclerView.Adapter provideCallRecordAdapter(List<CallRecordEntity> list) {
        CallRecordAdapter callRecordAdapter = new CallRecordAdapter(list);
        callRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CallRecordEntity entity = (CallRecordEntity) adapter.getItem(position);
//                PersonInfoActivity.start((Activity) view.getContext(),entity.getUsername());
            }
        });
        return callRecordAdapter;
    }

    @FragmentScope
    @Provides
    static List<CallRecordEntity> provideList() {
        return new ArrayList<>();
    }
}