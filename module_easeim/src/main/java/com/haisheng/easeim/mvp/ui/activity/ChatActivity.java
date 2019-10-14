package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.AppLifecyclesImpl;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerChatComponent;
import com.haisheng.easeim.di.module.ChatModule;
import com.haisheng.easeim.mvp.contract.ChatContract;
import com.haisheng.easeim.mvp.model.db.UserDao;
import com.haisheng.easeim.mvp.model.entity.ChatExtendItemEntity;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckRedpacketInfo;
import com.haisheng.easeim.mvp.model.entity.NiuniuSettlementInfo;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.model.entity.UserInfoBean;
import com.haisheng.easeim.mvp.presenter.ChatPresenter;
import com.haisheng.easeim.mvp.ui.widget.ChatExtendMenu;
import com.haisheng.easeim.mvp.ui.widget.ChatInputMenu;
import com.haisheng.easeim.mvp.ui.widget.EaseChatVoiceCallPresenter;
import com.haisheng.easeim.mvp.ui.widget.dialog.CommonDialog;
import com.haisheng.easeim.mvp.ui.widget.message.ChatGetRedPacketPresenter;
import com.haisheng.easeim.mvp.ui.widget.message.ChatGetRedpacket;
import com.haisheng.easeim.mvp.ui.widget.message.ChatRedPacketPresenter;
import com.haisheng.easeim.mvp.ui.widget.message.ChatSettlementPresenter;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAChatRoomManagerListener;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseChatRoomListener;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ActionUtils;
import me.jessyan.armscomponent.commonres.utils.AndroidBug5497Workaround;
import me.jessyan.armscomponent.commonres.utils.ConfigUtil;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonres.view.popupwindow.NotescontactPopupWindow;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;
import me.jessyan.armscomponent.commonsdk.utils.MyFileUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

//import me.jessyan.armscomponent.commonres.utils.MyGlideEngine;

@Route ( path = RouterHub.IM_CHATACTIVITY )
public class ChatActivity extends BaseSupportActivity <ChatPresenter> implements ChatContract.View {

    @BindView(R2.id.message_list)
    EaseChatMessageList messageList;
    @BindView(R2.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorder;
    @BindView(R2.id.input_menu)
    ChatInputMenu inputMenu;
    @BindView(R2.id.ll_alert_kicked_off)
    LinearLayout llAlertKickedOff;
    @BindView(R2.id.ll_balance)
    LinearLayout llBalance;
    @BindView(R2.id.tv_balance)
    TextView tvBalance;


    private static final int ITEM_WELFARE = 100;
    private static final int ITEM_LEAGUE = 101;
    private static final int ITEM_REDPACKET = 102;

    private static final int ITEM_LUCKY_DRAW = 103;
    private static final int ITEM_BALANCE = 104;
    private static final int ITEM_LIST = 105;
    private static final int ITEM_HELP = 106;
    private static final int ITEM_CUSTOMER_SERVICE = 107;
    private static final int ITEM_PHOTO = 108;
    private static final int ITEM_CAMEAR = 109;
    private static final int ITEM_MAKE_MONEY = 110;
    private static final int ITEM_PROFIT = 111;//盈亏记录
    private static final int ITEM_REDPACKET_5 = 112;
    private static final int ITEM_REDPACKET_6 = 113;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_CONFERENCE_INVITE = 5;
    private static final int MESSAGE_TYPE_LIVE_INVITE = 6;
    private static final int MESSAGE_TYPE_RECALL = 9;
    private static final int MESSAGE_TYPE_SENT_REDPACKET = 10;
    private static final int MESSAGE_TYPE_RECV_REDPACKET = 11;
    private static final int MESSAGE_TYPE_RECV_SETTLEMENT = 12;

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialogUtils progressDialogUtils;

    private String toChatUsername;
    private int chatType;
    private ChatRoomBean mChatRoomBean;
    private String mCameraSavePath;

    private Handler handler = new Handler ();
    private boolean isMessageListInited;
    private GroupListener mGroupListener;
    private ChatRoomListener mChatRoomListener;

    public static void start(Context context, String toChatUsername) {
        start ( context, toChatUsername, EaseConstant.CHATTYPE_SINGLE );
    }
    public static void start(Context context, String toChatUsername,boolean isService) {
        Intent intent = new Intent ( context, ChatActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putString ( "userId", toChatUsername );
        bundle.putInt ( "chatType", EaseConstant.CHATTYPE_SINGLE );
        bundle.putBoolean ( "isService", isService );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    public static void start(Context context, String toChatUsername, int chatType) {
        Intent intent = new Intent ( context, ChatActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putString ( "userId", toChatUsername );
        bundle.putInt ( "chatType", chatType );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    public static void start(Context context, ChatRoomBean chatRoomInfo) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", chatRoomInfo.getHxId ()+"");
        bundle.putInt("chatType", EaseConstant.CHATTYPE_CHATROOM);
        bundle.putSerializable("chatRoomInfo", chatRoomInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChatComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .chatModule ( new ChatModule ( this ) )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chat; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Bundle bundle = getIntent ().getExtras ();
        toChatUsername = bundle.getString ( EaseConstant.EXTRA_USER_ID );
        chatType = bundle.getInt ( EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE );
        mChatRoomBean = (ChatRoomBean) bundle.getSerializable("chatRoomInfo");
        mPresenter.initDatas(toChatUsername, chatType);

        initView();
        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onMessageListInit();
            mPresenter.onConversationInit();
        }

        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        boolean isService = bundle.getBoolean ( "isService" );
        if (isService){
            ivRight.setVisibility ( View.GONE );
        }else {
            ivRight.setVisibility ( View.VISIBLE );
            ivRight.setImageResource ( R.drawable.ic_group_talking );
        }
    }

    private void initView() {
        initInputMenu();
        initExtendMenuItem();
        tvTitle.setText ( toChatUsername );
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            UserInfo userInfo = UserDao.getInstance ().getUserEntityByHxId ( toChatUsername );
            mPresenter.getUserInfo ( toChatUsername );
            if (userInfo != null) {
                tvTitle.setText (  userInfo.getNickname () );
            } else {
                tvTitle.setText ( toChatUsername );
            }
        } else {
            messageList.setShowUserNick ( true );
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EMGroup group = EMClient.getInstance ().groupManager ().getGroup ( toChatUsername );
                if (group != null)
                tvTitle.setText ( group.getGroupName () );
                mGroupListener = new GroupListener ();
                EMClient.getInstance ().groupManager ().addGroupChangeListener ( mGroupListener );
            } else {
                tvTitle.setText ( mChatRoomBean.getName () );
                mChatRoomListener = new ChatRoomListener ();
                EMClient.getInstance ().chatroomManager ().addChatRoomChangeListener ( mChatRoomListener );
                mPresenter.joinRoom(toChatUsername);
                inputMenu.setTalkingEnable(false);
            }
            llBalance.setVisibility(View.VISIBLE);
        }
//        listView = messageList.getListView();
        llAlertKickedOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.joinRoom(mChatRoomBean.getHxId());
            }
        });
        initRefreshLayout();

    }

    @OnClick({R2.id.tv_balance,R2.id.iv_back, R2.id.iv_right})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_balance) {
            mPresenter.getBalanceInfo(true);

        }else if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_right) {
            //聊天详情
            if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                //单聊
                UserInfo userInfo = UserDao.getInstance ().getUserEntityByHxId ( toChatUsername );
                ChatDetailsActivity.start (this,userInfo);
            }else{
                //群聊
                GroupInfoActivity.start(mContext, mChatRoomBean);
            }
        }
    }

  /*  *//**
     * 修改群昵称
     * {@link GroupInfoActivity#setRoomNickNameSuccess(java.lang.String)}
     * @param nickname
     *//*
    @Subscriber(tag = "setRoomNickNameSuccess")
    public void updateGroupNickName(String nickname){
        if (mChatRoomBean!=null){
            String uid = SpUtils.getValue ( this,"uid", "" );
            for (int i = 0;i<mChatRoomBean.getUserInfos ().size ();i++){
                if (mChatRoomBean.getUserInfos ().get ( i ).getUid ().equals ( uid )){
                    mChatRoomBean.getUserInfos ().get ( i ).setNickname ( nickname );
                    break;
                }
            }
        }
    }*/


    private void initInputMenu() {
        inputMenu.init ( null );
        inputMenu.setChatInputMenuListener ( new ChatInputMenu.ChatInputMenuListener () {
            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {
//                mPresenter.sendMsgTypingBegin ();
            }

            @Override
            public void onSendMessage(String content) {
                if(null!=mChatRoomBean && mChatRoomBean.getBannedStatus() == 1){
                    showMessage("本群已禁止聊天");
                    return;
                }
                mPresenter.sendTextMessage ( content );
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorder.onPressToSpeakBtnTouch ( v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback () {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        mPresenter.sendVoiceMessage ( voiceFilePath, voiceTimeLength );
                    }
                } );
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                mPresenter.sendBigExpressionMessage ( emojicon.getName (), emojicon.getIdentityCode () );
            }
        } );
    }

    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void initExtendMenuItem() {
        List <ChatExtendItemEntity> entities = new ArrayList <> ();
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            entities.add(new ChatExtendItemEntity(ITEM_PHOTO, getString(R.string.chat_photo), R.drawable.ic_tab_photo));
            entities.add(new ChatExtendItemEntity(ITEM_CAMEAR, getString(R.string.chat_camera), R.drawable.ic_tab_camera));
        }else{
            if (mChatRoomBean.getType() == IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET){
                //禁抢房
                //发五包
                entities.add(new ChatExtendItemEntity(ITEM_REDPACKET_5, getString(R.string.chat_redpacket_5), R.drawable.ic_tab_red));
                //发六包
                entities.add(new ChatExtendItemEntity(ITEM_REDPACKET_6, getString(R.string.chat_redpacket_6), R.drawable.ic_tab_red));
            }else{
                entities.add(new ChatExtendItemEntity(ITEM_REDPACKET, getString(R.string.chat_redpacket), R.drawable.ic_tab_red));
            }
            entities.add(new ChatExtendItemEntity( ITEM_LUCKY_DRAW, getString(R.string.chat_recharge), R.drawable.ic_fount_lucky));
            entities.add(new ChatExtendItemEntity( ITEM_BALANCE, getString(R.string.chat_game_rules), R.drawable.ic_balance));
            entities.add(new ChatExtendItemEntity(ITEM_CUSTOMER_SERVICE, getString(R.string.chat_customer_service), R.drawable.ic_talk_service));
            entities.add(new ChatExtendItemEntity( ITEM_LIST, getString(R.string.chat_group_rules), R.drawable.ic_fount_glod_list));
            if (mChatRoomBean.getType () == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET
                    || mChatRoomBean.getType () == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET){
                entities.add(new ChatExtendItemEntity( ITEM_PROFIT, getString(R.string.chat_group_profit), R.drawable.icon_yk_record));
            }
        }

        inputMenu.initExtendMenuItem ( entities, mOnItemClickListener );
    }

    private ChatExtendMenu.OnItemClickListener mOnItemClickListener = new ChatExtendMenu.OnItemClickListener () {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ChatExtendItemEntity entity = (ChatExtendItemEntity) adapter.getItem ( position );
            switch (entity.getId()) {
                case ITEM_LIST:
                    //排行榜
                    ARouterUtils.navigation ( mContext,RouterHub.MAIN_COMMISSIONLISTACTIVITY );
                    break;
                case ITEM_REDPACKET_5:
                    //5红包
                    sendRedpacket( 5 );
                    break;
                case ITEM_REDPACKET_6:
                    //6红包
                    sendRedpacket(6);
                    break;
                case ITEM_REDPACKET:
                    //红包
                    sendRedpacket(0);
                    break;
                case ITEM_LUCKY_DRAW:
                    //抽奖
                    ARouterUtils.navigation(mContext,RouterHub.MAIN_LUCKYWHEELACTIVITY);
                    break;
                case ITEM_BALANCE:
                    //余额
                    mPresenter.getBalanceInfo(true);
                    break;
                case ITEM_CUSTOMER_SERVICE:
                    //客服
                    int roomType = mChatRoomBean.getType();
                    if (roomType == IMConstants.ROOM_TYPE_MINE_REDPACKET) {
                        //扫雷详情
                        ChatActivity.start ( ChatActivity.this,ConfigUtil.SERVICE_GAME_SAOLEI_INSIDE,true );
                    } else if (roomType == IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET) {
                        //禁抢详情
                        ChatActivity.start ( ChatActivity.this, ConfigUtil.SERVICE_GAME_CONTROL_INSIDE,true );
                    } else if (roomType == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET || roomType == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET) {
                        //牛牛详情
                        ChatActivity.start ( ChatActivity.this,ConfigUtil.SERVICE_GAME_NIUNIU_INSIDE,true );
                    } else if (roomType == IMConstants.ROOM_TYPE_WELFARE_REDPACKET) {
                        //福利详情
                        ChatActivity.start ( ChatActivity.this,ConfigUtil.SERVICE_GAME_FULI_INSIDE,true );
                    }
                    break;
                case ITEM_PROFIT:
                    //盈亏记录
                    openActivity ( NiuNiuRecordActivity.class );
                    break;
                case ITEM_PHOTO:
                    //照片
                    if(null!=mChatRoomBean && mChatRoomBean.getBannedStatus() == 1){
                        showMessage("本群已禁止聊天");
                        return;
                    }
                    ActionUtils.openSystemAblum((Activity) mContext);
                    break;
                case ITEM_CAMEAR:
                    //拍照
                    if(null!=mChatRoomBean && mChatRoomBean.getBannedStatus() == 1){
                        showMessage("本群已禁止聊天");
                        return;
                    }
                    mCameraSavePath = MyFileUtils.getNewCacheFilePath(mContext, Constants.IMAGE_CODE);
                    ActionUtils.openCamera(mContext,mCameraSavePath);
                    break;
            }
        }
    };

    private NotescontactPopupWindow mNotescontactPopupWindow;
    private void showNotescontactPopupWindow(View view){
        if( null == mNotescontactPopupWindow ){
            mNotescontactPopupWindow = new NotescontactPopupWindow(mContext);
        }
        mNotescontactPopupWindow.openPopWindow(view);
    }


    private void initRefreshLayout() {
        swipeRefreshLayout = messageList.getSwipeRefreshLayout ();
        swipeRefreshLayout.setColorSchemeResources ( R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light );
        swipeRefreshLayout.setOnRefreshListener ( () -> handler.postDelayed ( () -> {
            mPresenter.fetchHistoryMessages ();
        }, 600 ) );
    }

    private void onMessageListInit() {
        messageList.init ( toChatUsername, chatType, new CustomChatRowProvider () );
        setListItemClickListener ();
        messageList.getListView().setOnTouchListener((v, event) -> {
            KeyboardUtils.hideSoftInput(mContext);
            inputMenu.hideExtendMenuContainer();
            return false;
        });

        isMessageListInited = true;
    }

    private void setListItemClickListener() {
        messageList.setItemClickListener ( new EaseChatMessageList.MessageListItemClickListener () {

            @Override
            public void onUserAvatarClick(String username) {
//                if (username != EMClient.getInstance().getCurrentUser())
//                    PersonInfoActivity.start(mContext, username);
            }

            @Override
            public boolean onResendClick(final EMMessage message) {
                new AlertDialog.Builder ( mContext )
                        .setMessage ( R.string.confirm_resend )
                        .setNegativeButton ( R.string.public_cancel, new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss ();
                            }
                        } )
                        .setPositiveButton ( R.string.resend, new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                message.setStatus ( EMMessage.Status.CREATE );
                                mPresenter.sendMessage ( message );
                            }
                        } ).show ();
                return true;
            }

            @Override
            public void onUserAvatarLongClick(String username) {
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
//                return onMessageBubbleClick(message);
                int type = message.getIntAttribute(IMConstants.MESSAGE_ATTR_TYPE, -1);
                if (type != -1) {
                    if (type == IMConstants.MSG_TYPE_MINE_REDPACKET || type == IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET
                            || type == IMConstants.MSG_TYPE_NIUNIU_REDPACKET || type == IMConstants.MSG_TYPE_WELFARE_REDPACKET) {
                        String sRedpacketInfo = message.getStringAttribute(IMConstants.MESSAGE_ATTR_CONENT, "");
                        if (!TextUtils.isEmpty(sRedpacketInfo)) {
                            RedpacketBean redpacketBean = new Gson().fromJson(sRedpacketInfo, RedpacketBean.class);
                            mPresenter.checkRedpacket(mChatRoomBean.getId(), redpacketBean, message);
                        }
                    } else if (type == IMConstants.MSG_TYPE_NIUNIU_SETTLEMENT) {
                        String sSettlementInfo = message.getStringAttribute(IMConstants.MESSAGE_ATTR_CONENT, "");
                        if (!TextUtils.isEmpty(sSettlementInfo)) {
                            NiuniuSettlementInfo settlementInfo = new Gson().fromJson(sSettlementInfo, NiuniuSettlementInfo.class);
                            RedpacketDetailActivity.start(mContext, mChatRoomBean.getId(), settlementInfo.getRedpacketId(), 0,mChatRoomBean.getType ());
                        }
                    }
                    return true;
                }

                return false;
            }

            @Override
            public void onMessageInProgress(EMMessage message) {
                message.setMessageStatusCallback ( messageStatusCallback );
            }
        } );
    }

    private EMCallBack messageStatusCallback = new EMCallBack () {
        @Override
        public void onSuccess() {
            if (isMessageListInited) {
                messageList.refresh ();
            }
        }

        @Override
        public void onError(int code, String error) {
            if (isMessageListInited) {
                messageList.refresh ();
            }
        }

        @Override
        public void onProgress(int progress, String status) {
            if (isMessageListInited) {
                messageList.refresh ();
            }
        }
    };

    @Override
    public void setBarTitle(String title) {

    }

    @Override
    public void refreshSelectLast() {
        if (isMessageListInited)
            messageList.refreshSelectLast ();
    }

    @Override
    public void refreshList() {
        if (isMessageListInited)
            messageList.refresh ();
    }

    @Override
    public void setBalanceInfo(double balance) {
        tvBalance.setText(String.format("%.2f元",balance));
    }

    @Override
    public void joinRoomSuccessfully() {
        mPresenter.onConversationInit();
        onMessageListInit();
        llAlertKickedOff.setVisibility(View.GONE);
    }

    CommonDialog.Builder builder;
    private boolean isRabShow = false;

    /**
     * 显示红包
     */
    @Override
    public void showRedPacket(CheckRedpacketInfo checkRedpacketInfo, EMMessage emMessage) {
        String sRedpacketInfo = emMessage.getStringAttribute(IMConstants.MESSAGE_ATTR_CONENT, "");
        if (TextUtils.isEmpty(sRedpacketInfo)) return;
        RedpacketBean redpacketBean = new Gson().fromJson(sRedpacketInfo, RedpacketBean.class);
        final int status = emMessage.getIntAttribute(IMConstants.MESSAGE_ATTR_REDPACKET_STATUS, checkRedpacketInfo.getStatus());
        final String remark = checkRedpacketInfo.getMessage();
        final String avatarUrl = redpacketBean.getAvatarUrl();
        final String nickname = redpacketBean.getNickname();
        final Long redpacketId = redpacketBean.getId();
        String hxID = redpacketBean.getHxid ();
        if (builder != null) {
            builder.dismiss();
        }
        builder = new CommonDialog.Builder(getActivity())
                .center().loadAniamtion()
                .setView(R.layout.dialog_red_packet);
        builder.setOnClickListener(R.id.ibtn_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setText(R.id.tv_nick, nickname);
        builder.setText(R.id.tv_message, redpacketBean.getMoney ()+"-"+redpacketBean.getBoomNumbers ());
        builder.setOnClickListener(R.id.img_open, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRabShow) {
                    showMessage("抢红包中，请勿重复提交！");
                    return;
                }
                isRabShow = true;
                v.setVisibility(View.INVISIBLE);
                ImageView ivOpening = builder.getView(R.id.img_opening);
                mPresenter.grabRedpacket(mChatRoomBean.getId(), redpacketBean, emMessage, ivOpening);
            }
        });
        builder.setOnClickListener(R.id.tv_red_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder != null) {
                    builder.dismiss();
                }
                RedpacketDetailActivity.start(mContext, mChatRoomBean.getId(), redpacketId, redpacketBean.getWelfareStatus(),mChatRoomBean.getType ());
            }
        });
        CommonDialog commonDialog = builder.create();
        String myHxID = SpUtils.getValue ( this,"hxid", "" );
        if (myHxID.equals ( hxID )){
            //自己发包
            builder.getView(R.id.tv_red_detail).setVisibility(View.VISIBLE);
        }else {
            builder.getView ( R.id.tv_red_detail ).setVisibility ( View.GONE );
        }
        if (status == 0) {//红包未抢完 未过期 未参与
            builder.getView(R.id.rel_open).setVisibility(View.VISIBLE);
        } else if (status == 1) {//红包未抢完 未过期 已参与
            builder.getView(R.id.rel_open).setVisibility(View.INVISIBLE);
            builder.getView(R.id.tv_red_detail).setVisibility(View.VISIBLE);
        } else if (status == 3) {//红包未过期 已抢完 未参与
            builder.getView(R.id.rel_open).setVisibility(View.INVISIBLE);
            builder.getView(R.id.tv_red_detail).setVisibility(View.VISIBLE);
        } else if (status == 2) {//红包已过期
            builder.getView(R.id.rel_open).setVisibility(View.INVISIBLE);
        }
        ImageView imageView = builder.getView(R.id.img_head);
        ImageLoader.displayHeaderImage(mContext, avatarUrl, imageView);
        commonDialog.show();
    }

    @Override
    public void grabRedpacketSuccessfully(Long redpacketId, int welfareStatus, RedpacketBean redpacketBean) {
        isRabShow = false;
        playSound();
        //发送领取红包消息
        mPresenter.sendGetRedPacketMessage (this,redpacketBean);
        RedpacketDetailActivity.start(mContext, mChatRoomBean.getId(), redpacketId, welfareStatus,mChatRoomBean.getType ());
    }

    /**
     * 点击红包跳转到详情
     * {@link ChatGetRedpacket#onSetUpView()}
     * @param redpacketBean
     */
    @Subscriber(tag = "onSetUpView")
    public void clickGetRedPacket(RedpacketBean redpacketBean){
        RedpacketDetailActivity.start(mContext, mChatRoomBean.getId(), redpacketBean.getId (), redpacketBean.getWelfareStatus (),mChatRoomBean.getType ());
    }

    @Override
    public void grabRedpacketFail() {
        isRabShow = false;
    }

    /**
     * 红包开声效
     */
    protected void playSound() {
        MediaPlayer player = MediaPlayer.create(getActivity(), R.raw.red_sound);
        player.start();
    }

    // 开始动画
    @Override
    public void openAnimation(View view) {
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        if (animationDrawable != null) {
            view.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
    }

    // 关闭动画
    @Override
    public void closeAnimation(View view) {
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        if (animationDrawable != null) {
            view.setVisibility(View.GONE);
            animationDrawable.stop();
        }
        if (null != builder && builder.isShowing()) {
            builder.dismiss();
        }
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void finishRefresh() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showRedPacketDetail(RedpacketBean redpacketBean) {
        RedpacketDetailActivity.start(mContext, mChatRoomBean.getId(), redpacketBean.getId (), redpacketBean.getWelfareStatus (),mChatRoomBean.getType ());
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean.ResultBean result) {
        if(result!=null) {
            tvTitle.setText (result.getNickname ());
            if (result.getThumb ()!=null) {
                SpUtils.put ( ChatActivity.this, result.getHxid () + "head", result.getThumb () );
                SpUtils.put ( ChatActivity.this, result.getHxid () + "nickname", result.getNickname () );
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
//        checkNotNull ( message );
        ToastUtils.showShort ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
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
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra ( "userId" );
        if (toChatUsername.equals ( username ))
            super.onNewIntent ( intent );
        else {
            finish ();
            startActivity ( intent );
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (inputMenu.onBackPressed () && onHideKeyboard ()) {
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EaseAtMessageHelper.get ().removeAtMeGroup ( toChatUsername );
                EaseAtMessageHelper.get ().cleanToAtUserList ();
            }
//            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
//                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//            }
            if (EasyUtils.isSingleActivity ( this )) {
                ARouterUtils.navigation(mContext, RouterHub.APP_MAINACTIVITY);
            } else {
                super.onBackPressedSupport ();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        mPresenter.addMessageListener ();
        EaseUI.getInstance ().getNotifier ().reset ();
        if (isMessageListInited)
            messageList.refresh ();
        EaseUI.getInstance ().pushActivity ( this );
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get ().removeAtMeGroup ( toChatUsername );
        }
        if(chatType!=EaseConstant.CHATTYPE_SINGLE){
            mPresenter.getBalanceInfo(false);
        }

    }

    @Override
    public void onPause() {
        super.onPause ();
        mPresenter.removeMessageListener ();
        EaseUI.getInstance ().popActivity ( this );
        handler.removeCallbacksAndMessages ( null );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        if (mGroupListener != null) {
            EMClient.getInstance ().groupManager ().removeGroupChangeListener ( mGroupListener );
        }

        if (mChatRoomListener != null) {
            EMClient.getInstance ().chatroomManager ().removeChatRoomListener ( mChatRoomListener );
        }
        EventBus.getDefault ().unregister ( this );
//        if(chatType == EaseConstant.CHATTYPE_CHATROOM){
//            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == Constants.REQUEST_CODE_SYSTEM_ALBUM) {
            Uri uri = data.getData();
            String photoPath = MyFileUtils.getRealPathFromUri(mContext,uri);
            mPresenter.sendImageMessage(photoPath);

        } else if (requestCode == Constants.REQUEST_CODE_CAMERA) {
            mPresenter.sendImageMessage(mCameraSavePath);

        }else if (requestCode == Constants.REQUEST_CODE_VIDEO) {
            if (data != null) {
                int duration = data.getIntExtra ( "dur", 0 );
                String videoPath = data.getStringExtra ( "path" );
                File file = new File ( PathUtil.getInstance ().getImagePath (), "thvideo" + System.currentTimeMillis () );
                try {
                    FileOutputStream fos = new FileOutputStream ( file );
                    Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail ( videoPath, 3 );
                    ThumbBitmap.compress ( Bitmap.CompressFormat.JPEG, 100, fos );
                    fos.close ();
                    mPresenter.sendVideoMessage ( videoPath, file.getAbsolutePath (), duration );
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }

        } else if (requestCode == Constants.REQUEST_CODE_VIDEO_ALBUM) {
                       /*List<String> selectVideos = Matisse.obtainPathResult(data);
            if (selectVideos != null && selectVideos.size() > 0) {
                String videoPath = selectVideos.get(0);
                File videoFile = new File(videoPath);
                try {
                    MediaPlayer meidaPlayer = new MediaPlayer();
                    meidaPlayer.setDataSource(videoFile.getPath());
                    meidaPlayer.prepare();
                    int duration = meidaPlayer.getDuration();

                    File thumbFile = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    FileOutputStream fos = new FileOutputStream(thumbFile);
                    Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                    ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                    mPresenter.sendVideoMessage(videoPath, thumbFile.getAbsolutePath(), duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        } else if (requestCode == IMConstants.REQUEST_CODE_SEND_REDPACKET) {
            Bundle bundle = data.getExtras();
            RedpacketBean redpacketBean = (RedpacketBean) bundle.getSerializable("redpacketInfo");
            redpacketBean.setRoomid ( mChatRoomBean.getId ()+"" );
            //发送红包消息
            mPresenter.sendRedpacketMessage(redpacketBean);
           AppLifecyclesImpl.getUserInfo ().setAvatarUrl ( redpacketBean.getAvatarUrl () );
           AppLifecyclesImpl.getUserInfo ().setNickname ( redpacketBean.getNickname () );
        }

    }

    private void sendRedpacket(int redpacket_num) {
        int roomType = mChatRoomBean.getType();
        if (roomType == IMConstants.ROOM_TYPE_MINE_REDPACKET) {
            //扫雷详情
            SendMineRedpacketActivity.start(mContext, mChatRoomBean);
        } else if (roomType == IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET) {
            //禁抢详情
            SendGunControlRedpacketActivity.start(mContext, mChatRoomBean,redpacket_num);
        } else if (roomType == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET || roomType == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET) {
            //牛牛详情
            SendNiuniuRedpacketActivity.start(mContext, mChatRoomBean);
        } else if (roomType == IMConstants.ROOM_TYPE_WELFARE_REDPACKET) {
            //福利详情
            SendWelfarRedpacketActivity.start(mContext, mChatRoomBean);
        }
    }


    // 清空所有聊天记录
    protected void emptyHistory() {
        new AlertDialog.Builder ( mContext )
                .setMessage ( R.string.Whether_to_empty_all_chats )
                .setNegativeButton ( R.string.public_cancel, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } )
                .setPositiveButton ( R.string.public_confirm, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.emptyHistory ();
                        messageList.refresh ();
                    }
                } ).show ();
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance ().isConnected ()) {
            showMessage ( getString ( R.string.not_connect_to_server ) );
        } else {
            VoiceCallActivity.start ( mContext, toChatUsername, false );
            inputMenu.hideExtendMenuContainer ();
        }
    }


     // make a video call

    protected void startVideoCall() {
        if (!EMClient.getInstance ().isConnected ())
            showMessage ( mContext.getString ( R.string.not_connect_to_server ) );
        else {
            VideoCallActivity.start ( mContext, toChatUsername, false );
            inputMenu.hideExtendMenuContainer ();

        }
    }

    //hide Keyboard
    private boolean onHideKeyboard() {
        if (KeyboardUtils.isSoftInputVisible ( mContext )) {
            KeyboardUtils.hideSoftInput ( mContext );
            return false;
        }
        return true;
    }


    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 17;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType () == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute ( IMConstants.MESSAGE_ATTR_IS_VOICE_CALL, false )) {
                    return message.direct () == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute ( IMConstants.MESSAGE_ATTR_IS_VIDEO_CALL, false )) {
                    //video call
                    return message.direct () == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //messagee recall
                else if (message.getBooleanAttribute ( IMConstants.MESSAGE_TYPE_RECALL, false )) {
                    return MESSAGE_TYPE_RECALL;
                } else if (!"".equals ( message.getStringAttribute ( IMConstants.MSG_ATTR_CONF_ID, "" ) )) {
                    return MESSAGE_TYPE_CONFERENCE_INVITE;
                } else if (IMConstants.OP_INVITE.equals ( message.getStringAttribute ( IMConstants.EM_CONFERENCE_OP, "" ) )) {
                    return MESSAGE_TYPE_LIVE_INVITE;
                } else if (message.getIntAttribute(IMConstants.MESSAGE_ATTR_TYPE, -1) != -1) {
                    int type = message.getIntAttribute(IMConstants.MESSAGE_ATTR_TYPE, -1);
                    //红包消息
                    if (type == IMConstants.MSG_TYPE_MINE_REDPACKET || type == IMConstants.MSG_TYPE_WELFARE_REDPACKET
                            || type == IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET || type == IMConstants.MSG_TYPE_NIUNIU_REDPACKET) {
                        return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_REDPACKET : MESSAGE_TYPE_SENT_REDPACKET;
                        ///结算消息
                    } else if (type == IMConstants.MSG_TYPE_GUN_CONTROL_SETTLEMENT || type == IMConstants.MSG_TYPE_NIUNIU_SETTLEMENT) {
                        return MESSAGE_TYPE_RECV_SETTLEMENT;
                    }

                }
            }
            return 0;
        }

        @Override
        public EaseChatRowPresenter getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(IMConstants.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(IMConstants.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    EaseChatRowPresenter presenter = new EaseChatVoiceCallPresenter();
                    return presenter;

                }
                int type = message.getIntAttribute(IMConstants.MESSAGE_ATTR_TYPE, -1);
                String clus = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_TYPE,"" );
                //红包消息
                if (type == IMConstants.MSG_TYPE_MINE_REDPACKET || type == IMConstants.MSG_TYPE_WELFARE_REDPACKET
                        || type == IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET || type == IMConstants.MSG_TYPE_NIUNIU_REDPACKET) {
                    EaseChatRowPresenter presenter = new ChatRedPacketPresenter();
                    return presenter;
                    ///结算消息
                } else if (type == IMConstants.MSG_TYPE_GUN_CONTROL_SETTLEMENT || type == IMConstants.MSG_TYPE_NIUNIU_SETTLEMENT) {
                    EaseChatRowPresenter presenter = new ChatSettlementPresenter();
                    return presenter;

                }else if (type == IMConstants.MSG_TYPE_GET_REDPACKET || IMConstants.GET_REDPACKET_MSG_CLUES.equals ( clus )){
                    //领取红包信息
                    EaseChatRowPresenter presenter = new ChatGetRedPacketPresenter ();
                    return presenter;
                }
            }
            return null;
        }
    }


    /**
     * listen the group event
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            runOnUiThread ( () -> {
                if (toChatUsername.equals ( groupId )) {
                    showMessage ( getString ( R.string.you_are_group ) );
                    Activity activity = getActivity ();
                    if (activity != null && !activity.isFinishing ()) {
                        activity.finish ();
                    }
                }
            } );
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            runOnUiThread ( () -> {
                if (toChatUsername.equals ( groupId )) {
                    showMessage ( getString ( R.string.the_current_group_destroyed ) );
                    Activity activity = getActivity ();
                    if (activity != null && !activity.isFinishing ()) {
                        activity.finish ();
                    }
                }
            } );
        }
    }

    /**
     * listen chat room event
     */
    class ChatRoomListener extends EaseChatRoomListener {

        @Override
        public void onChatRoomDestroyed(final String roomId, final String roomName) {
            runOnUiThread ( () -> {
                if (toChatUsername.equals ( roomId )) {
                    showMessage ( getString ( R.string.the_current_chat_room_destroyed ) );
                    Activity activity = getActivity ();
                    if (activity != null && !activity.isFinishing ()) {
                        activity.finish ();
                    }
                }
            } );
        }

        @Override
        public void onRemovedFromChatRoom(final int reason, final String roomId, final String roomName, final String participant) {
            runOnUiThread ( new Runnable () {
                public void run() {
                    if (roomId.equals ( toChatUsername )) {
                        if (reason == EMAChatRoomManagerListener.BE_KICKED) {
                            showMessage ( getString ( R.string.quiting_the_chat_room ) );
                            Activity activity = getActivity ();
                            if (activity != null && !activity.isFinishing ()) {
                                activity.finish ();
                            }
                        } else { // BE_KICKED_FOR_OFFLINE
                            showMessage ( getString ( R.string.alert_kicked_for_offline ) );
                            llAlertKickedOff.setVisibility ( View.VISIBLE );
                        }
                    }
                }
            } );
        }

        @Override
        public void onMemberJoined(final String roomId, final String participant) {
        }

        @Override
        public void onMemberExited(final String roomId, final String roomName, final String participant) {
        }
    }

}
