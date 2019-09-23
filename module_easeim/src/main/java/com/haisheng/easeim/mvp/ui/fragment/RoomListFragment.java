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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerRoomListComponent;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.haisheng.easeim.mvp.ui.activity.CustomerServiceListActivity;
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

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.popupwindow.NotescontactPopupWindow;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
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
    TextView tvFriendsDescribe;
    ImageView ivFriendsRoundRed;
    RelativeLayout rlCustomerService;

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
//        mPresenter.initDatas(mTypeStatus);
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
            getHeadViews();
        }

        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatRoomBean roomBean = (ChatRoomBean) adapter.getItem(position);
                mPresenter.roomDetail(roomBean.getId());
            }
        });
    }

    private void getHeadViews(){
        View headView1 = LayoutInflater.from(mContext).inflate(R.layout.item_online_customer_service,null,false);
        rlCustomerService = headView1.findViewById(R.id.rl_online_customer_service);
        rlCustomerService.setOnClickListener(mOnClickListener);
        mAdapter.addHeaderView(headView1);

        View headView2 = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends,null,false);
        rlMyFriends = headView2.findViewById(R.id.rl_my_friends);
        tvFriendsDescribe = headView2.findViewById(R.id.tv_friends_describe);
        ivFriendsRoundRed = headView2.findViewById(R.id.iv_friends_round_red);
        rlMyFriends.setOnClickListener(mOnClickListener);
        mAdapter.addHeaderView(headView2);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.rl_online_customer_service){
                showNotescontactPopupWindow(v);

            }else if(id == R.id.rl_my_friends){
                launchActivity(new Intent(mContext, FriendConversationListActivity.class));
            }
        }
    };

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.initDatas(mTypeStatus);
        if(mTypeStatus == Constants.IM.TYPE_MESSAGE){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscriber(tag = EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION_LIST)
    private void updateList(String tag) {
        mPresenter.requestDatas();
    }

    @Override
    public void setData(@Nullable Object data) { }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas();
    }

    @Override
    public void showRefresh() {
        refreshLayout.autoRefreshAnimationOnly();
    }

    @Override
    public void finishRefresh() {
        if(null!=refreshLayout)
            refreshLayout.finishRefresh();
    }

    private NotescontactPopupWindow mNotescontactPopupWindow;
    private void showNotescontactPopupWindow(View view){
        if( null == mNotescontactPopupWindow ){
            mNotescontactPopupWindow = new NotescontactPopupWindow(mContext);
        }
        mNotescontactPopupWindow.openPopWindow(view);
    }

    @Override
    public void setMyFriendsUnreadCount(int unreadCount) {
        if(unreadCount>0){
            ivFriendsRoundRed.setVisibility(View.VISIBLE);
            String sUnreadCount = unreadCount > 99 ? "99+": String.valueOf(unreadCount);
            tvFriendsDescribe.setText(mContext.getString(R.string.has_unread_mssages,sUnreadCount));
        }else{
            ivFriendsRoundRed.setVisibility(View.INVISIBLE);
            tvFriendsDescribe.setText(mContext.getString(R.string.no_unread_messages));
        }
    }

    @Override
    public void joinRoomSuccessfully(ChatRoomBean chatRoomInfo) {
        ChatActivity.start(mContext,chatRoomInfo);
        if(mTypeStatus ==  Constants.IM.TYPE_ROOM){
            EventBus.getDefault().post(EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION_LIST, EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION_LIST);
        }
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
