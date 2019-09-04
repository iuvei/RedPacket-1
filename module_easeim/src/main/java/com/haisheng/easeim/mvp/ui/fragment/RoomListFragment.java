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
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerRoomListComponent;
import com.haisheng.easeim.mvp.model.entity.RoomBean;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.haisheng.easeim.mvp.ui.activity.FriendConversationListActivity;
import com.haisheng.easeim.mvp.ui.adapter.RoomListAdapter;
import com.hyphenate.easeui.EaseConstant;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.haisheng.easeim.mvp.contract.RoomListContract;
import com.haisheng.easeim.mvp.presenter.RoomListPresenter;

import com.haisheng.easeim.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 21:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.IM_ROOMLISTFRAGMENT)
public class RoomListFragment extends BaseSupportFragment<RoomListPresenter> implements RoomListContract.View, OnRefreshListener {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RelativeLayout rlMyFriends;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RoomListAdapter mAdapter;

    private ProgressDialogUtils progressDialogUtils;
    private View mEmptyView;

    private int mTypeStatus;

    public static RoomListFragment newInstance() {
        RoomListFragment fragment = new RoomListFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRoomListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(null != bundle){
            mTypeStatus = bundle.getInt("typeStatus");
        }

        initRecyclerView();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setOnRefreshListener(this);

        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager);

        mAdapter.setTypeStatus(mTypeStatus);
        recyclerView.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.public_empty_page,null,false);
        if(mTypeStatus == Constants.IM.TYPE_MESSAGE){
            mAdapter.addHeaderView(getHeadView());
            mPresenter.initMessageListener();
        }

        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RoomBean roomBean = (RoomBean) adapter.getItem(position);
                mPresenter.joinRoom(roomBean.getHxId());
            }
        });
    }

    private View getHeadView(){
        View headView = LayoutInflater.from(mContext).inflate(R.layout.view_conversation_head,null,false);
        rlMyFriends = headView.findViewById(R.id.rl_my_friends);
        rlMyFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(new Intent(mContext, FriendConversationListActivity.class));
            }
        });
        return headView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.initDatas();
    }

    @Override
    public void setData(@Nullable Object data) { }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas();
    }

    @Override
    public void showRefresh() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setConflict(boolean isConflict) {

    }

    @Override
    public void onConnectionDisconnected() {

    }

    @Override
    public void onConnectionConnected() {

    }

    @Override
    public void joinRoomSuccessfully(String roomId) {
        ChatActivity.start(getActivity(),roomId,  EaseConstant.CHATTYPE_CHATROOM);
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage(getString(R.string.public_loading));
        }
        if (show) {
            progressDialogUtils.show();
        } else {
            progressDialogUtils.dismiss();
        }
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
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
