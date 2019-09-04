package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.haisheng.easeim.di.component.DaggerSystemMessagesComponent;
import com.haisheng.easeim.di.module.SystemMessagesModule;
import com.haisheng.easeim.mvp.contract.SystemMessagesContract;
import com.haisheng.easeim.mvp.presenter.SystemMessagesPresenter;

import com.haisheng.easeim.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.StatusActivity;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SystemMessagesActivity extends StatusActivity<SystemMessagesPresenter> implements SystemMessagesContract.View {

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    public static void start(Activity context) {
        Intent intent = new Intent(context, SystemMessagesActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSystemMessagesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .systemMessagesModule(new SystemMessagesModule(this))
                .build()
                .inject(this);
    }

//    @Override
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return R.layout.activity_recycler;
//    }

    @Override
    public int bindLayout() {
        return  R.layout.activity_recycler;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitle(R.string.system_message);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        initRecyclerView();
        mPresenter.requestDatas();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContent() {
        super.showContent();
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
