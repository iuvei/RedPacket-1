package com.haisheng.easeim.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerCallListComponent;
import com.haisheng.easeim.di.module.CallListModule;
import com.haisheng.easeim.mvp.contract.CallListContract;
import com.haisheng.easeim.mvp.presenter.CallListPresenter;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import javax.inject.Inject;
import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.StatusFragment;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import static com.jess.arms.utils.Preconditions.checkNotNull;

public class CallListFragment extends StatusFragment<CallListPresenter> implements CallListContract.View ,OnRefreshListener,OnRefreshLoadMoreListener {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    public static CallListFragment newInstance() {
        CallListFragment fragment = new CallListFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCallListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .callListModule(new CallListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_call_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.initDatas();
    }

    @Override
    public void onClickErrorLoadData(View v) {
        super.onClickErrorLoadData(v);
        mPresenter.requestDatas(true);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
//        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showLoading() {
        mRefreshLayout.autoRefreshAnimationOnly();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void startLoadMore() {
        mRefreshLayout.autoLoadMore();
    }

    @Override
    public void endLoadMore() {
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

}
