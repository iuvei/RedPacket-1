package com.haisheng.easeim.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerContactComponent;
import com.haisheng.easeim.mvp.contract.ContactContract;
import com.haisheng.easeim.mvp.presenter.ContactPresenter;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.haisheng.easeim.mvp.ui.adapter.ContactAdapter;
import com.haisheng.easeim.mvp.ui.widget.sidelist.Sidebar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.view.recyclerview.TopLayoutManager;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

@Route(path = RouterHub.IM_CONTACTFRAGMENT)
public class ContactFragment extends BaseSupportFragment <ContactPresenter> implements ContactContract.View, OnRefreshListener {

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.sidebar)
    Sidebar sidebar;
//    @BindView(R2.id.tv_current_letter)
//    TextView tvCurrentLetter;
//    RecyclerView rvMyInviteFriend;
//    RecyclerView rvInviteMyFriend;
//    RecyclerView rvCustomerService;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ContactAdapter mAdapter;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    Unbinder unbinder;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment ();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerContactComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_contact, container, false );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView ();
        sidebar.setListView ( recyclerView );
        mPresenter.initDatas ();
        StatusBarUtils.setTranslucentStatus ( getActivity () );
        StatusBarUtils.setStatusBarDarkTheme ( getActivity (), true );
        ivBack.setVisibility ( View.GONE );
        tvTitle.setText ( "通讯录" );
        ivRight.setImageResource ( R.mipmap.alipay );
        ivRight.setVisibility ( View.VISIBLE );
    }


    //初始化RecyclerView
    private void initRecyclerView() {
        refreshLayout.setRefreshHeader ( new ClassicsHeader ( mContext ) );
        refreshLayout.setOnRefreshListener ( this );

        TopLayoutManager layoutManager = new TopLayoutManager ( mContext );
        ArmsUtils.configRecyclerView ( recyclerView, layoutManager );
        recyclerView.setAdapter ( mAdapter );
        mAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserInfo userInfo = (UserInfo) adapter.getItem ( position );
                ChatActivity.start ( (Activity) mContext, userInfo.getHxId () );
//                RoomBean roomBean = (RoomBean) adapter.getItem(position);
//                mPresenter.joinRoom(roomBean.getHxId());
            }
        } );
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showRefresh() {
        refreshLayout.autoRefresh ();
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh ();
    }

    @Override
    public void startLoadMore() {
        refreshLayout.autoLoadMore ();
    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadMore ();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas ( true );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView ( inflater, container, savedInstanceState );
        unbinder = ButterKnife.bind ( this, rootView );
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @OnClick({ R2.id.iv_right})
    public void onViewClicked() {
        //添加好友
        ARouter.getInstance ().build ( RouterHub.ADD_FRIEND_ACTIVITY ).navigation ();
    }
}
