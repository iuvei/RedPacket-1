package com.haisheng.easeim.mvp.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerConversationListComponent;
import com.haisheng.easeim.di.module.ConversationListModule;
import com.haisheng.easeim.mvp.contract.ConversationListContract;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.presenter.ConversationListPresenter;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.hyphenate.util.NetUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import me.jessyan.armscomponent.commonres.utils.ConfigUtil;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.IM_CONVERSATIONLISTFRAGMENT)
public class ConversationListFragment extends BaseSupportFragment <ConversationListPresenter> implements ConversationListContract.View, OnRefreshListener {

    @BindView(R2.id.tv_connect_errormsg)
    TextView tvConnectErrormsg;
    @BindView(R2.id.ll_net_error)
    LinearLayout llNetError;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.conversation_list)
    EaseConversationList conversationListView;

    public boolean isConflict;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    Unbinder unbinder;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.ll_customer_service)
    LinearLayout llService;
    private int mTag;
    private List <Long> userInfos;

    public static ConversationListFragment newInstance(int tag) {
        ConversationListFragment fragment = new ConversationListFragment ();
        Bundle bundle = new Bundle ();
        bundle.putInt ( "tag", tag );
        fragment.setArguments ( bundle );
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerConversationListComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .conversationListModule ( new ConversationListModule ( this ) )
                .build ()
                .inject ( this );
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_conversation_list, container, false );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean ( IMConstants.IS_CONFLICT, false ))
            return;
        super.onActivityCreated ( savedInstanceState );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState ( outState );
        if (isConflict) {
            outState.putBoolean ( IMConstants.IS_CONFLICT, true );
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( getActivity () );
        StatusBarUtils.setStatusBarDarkTheme ( getActivity (), true );
        ivBack.setVisibility ( View.GONE );
        tvTitle.setText ( "聊天" );
        ivRight.setVisibility ( View.VISIBLE );
        ivRight.setBackgroundResource ( R.drawable.ic_talk_add );
        Bundle bundle = getArguments ();
        if (null != bundle) {
            mTag = bundle.getInt ( "tag" );
        }
        conversationListView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
//                if(0 == position){
//                    SystemMessagesActivity.start((Activity) mContext);
//                    return;
//                }

                EMConversation conversation = conversationListView.getItem ( position );
                String username = conversation.conversationId ();
                if (username.equals ( EMClient.getInstance ().getCurrentUser () )) {
                    showMessage ( getString ( R.string.Cant_chat_with_yourself ) );
                } else {
                    showMessage ( username );
                    if (conversation.isGroup ()) {
                        if (conversation.getType () == EMConversation.EMConversationType.ChatRoom) {
                            //获取群组详情
                            mPresenter.roomDetail ( "",username );
                        } else {
                            ChatActivity.start ( (Activity) mContext, username, EaseConstant.CHATTYPE_GROUP );
                        }
                    } else {
                        ChatActivity.start ( (Activity) mContext, username );
                    }
                }
            }
        } );

        initRefreshLayout ();

        mPresenter.initDatas ( mTag );

        llService.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ChatActivity.start ( getActivity (), ConfigUtil.SERVICE_HOMEPAGE,true );
            }
        } );
    }

    private void initRefreshLayout() {
        mRefreshLayout.setRefreshHeader ( new ClassicsHeader ( mContext ) );
        mRefreshLayout.setOnRefreshListener ( this );
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible ();
        mPresenter.sendRefreshOrder ();
    }

    @Override
    public void setData(@Nullable Object data) {
    }

    @Override
    public void setConflict(boolean isConflict) {
        this.isConflict = isConflict;
    }

    @Override
    public void onConnectionDisconnected() {
        llNetError.setVisibility ( View.VISIBLE );
        if (NetUtils.hasNetwork ( mContext )) {
            tvConnectErrormsg.setText ( R.string.can_not_connect_chat_server_connection );
        } else {
            tvConnectErrormsg.setText ( R.string.the_current_network );
        }
    }

    @Override
    public void onConnectionConnected() {
        llNetError.setVisibility ( View.GONE );
    }

    @Override
    public void setConversationList(List <EMConversation> conversationList) {
        for (int j = conversationList.size ()-1;j>=0;j--) {
            //是否置顶
            boolean chatTop = SpUtils.getValue ( getActivity (), conversationList.get ( j ).conversationId (), false );
            if (chatTop) {
                //将i，j的位置的两个元素交换
                Collections.swap ( conversationList, 0, j );
            }
        }
        conversationListView.init ( conversationList );
        int countTotal = EMClient.getInstance ().chatManager ().getUnreadMessageCount ();
        EventBus.getDefault ().post ( countTotal, EventBusHub.EVENTBUS_IM_UNREAD_COUNT );
    }

    @Override
    public void joinRoomSuccessfully(ChatRoomBean result) {
        ChatActivity.start ( getActivity (), result );
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.sendRefreshOrder ();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mRefreshLayout.finishRefresh ();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
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

    @OnClick(R2.id.iv_right)
    public void onViewClicked() {
        View contentView = View.inflate ( getActivity (),R.layout.popupwindow_conversation,null );
        PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( getActivity (),1,ivRight,contentView,true );
        popuWindowsUtils.showViewBottom_AlignRight(ivRight);
        contentView.findViewById ( R.id.ll_add_firend ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //添加好友
                ARouter.getInstance ().build ( RouterHub.ADD_FRIEND_ACTIVITY ).navigation ();
            }
        } );
        contentView.findViewById ( R.id.ll_scan ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //扫一扫
                QrConfig qrConfig = new QrConfig.Builder()
                        .setLooperWaitTime(5*1000)//连续扫描间隔时间
                        .create();
                QrManager.getInstance().init(qrConfig).startScan(getActivity (), new QrManager.OnScanResultCallback() {
                    @Override
                    public void onScanSuccess(ScanResult result) {
                        ARouter.getInstance ().build ( RouterHub.SCAN_ACTIVITY ).withString ( "account",result.getContent () ).navigation ();
                    }
                });
            }
        } );
        contentView.findViewById ( R.id.ll_qr ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //二维码
                ARouter.getInstance ().build ( RouterHub.QR_ACTIVITY ).navigation ();
            }
        } );
    }
}
