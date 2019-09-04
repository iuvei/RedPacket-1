package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerChatComponent;
import com.haisheng.easeim.di.module.ChatModule;
import com.haisheng.easeim.mvp.contract.ChatContract;
import com.haisheng.easeim.mvp.model.db.UserDao;
import com.haisheng.easeim.mvp.model.entity.ChatExtendItemEntity;
import com.haisheng.easeim.mvp.presenter.ChatPresenter;
import com.haisheng.easeim.mvp.ui.widget.ChatExtendMenu;
import com.haisheng.easeim.mvp.ui.widget.ChatInputMenu;
import com.haisheng.easeim.mvp.ui.widget.EaseChatVoiceCallPresenter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAChatRoomManagerListener;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseChatRoomListener;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.jess.arms.di.component.AppComponent;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.utils.ActionUtils;
import me.jessyan.armscomponent.commonres.utils.MyGlideEngine;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ChatActivity extends BaseSupportActivity<ChatPresenter> implements ChatContract.View {

    @BindView(R2.id.public_toolbar_title)
    TextView publicToolbarTitle;
    @BindView(R2.id.message_list)
    EaseChatMessageList messageList;
    @BindView(R2.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorder;
    @BindView(R2.id.input_menu)
    ChatInputMenu inputMenu;
    @BindView(R2.id.ll_alert_kicked_off)
    LinearLayout llAlertKickedOff;

    private static final int ITEM_WELFARE= 100;
    private static final int ITEM_LEAGUE = 101;
    private static final int ITEM_REDPACKET = 102;
    private static final int ITEM_RECHARGE = 103;
    private static final int ITEM_GAME_RULES = 104;
    private static final int ITEM_GROUP_RULES = 105;
    private static final int ITEM_HELP = 106;
    private static final int ITEM_CUSTOMER_SERVICE = 107;
    private static final int ITEM_PHOTO = 108;
    private static final int ITEM_CAMEAR = 109;
    private static final int ITEM_MAKE_MONEY = 110;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_CONFERENCE_INVITE = 5;
    private static final int MESSAGE_TYPE_LIVE_INVITE = 6;
    private static final int MESSAGE_TYPE_RECALL = 9;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OptionsPickerView mPhotoChargePickerView;
    private OptionsPickerView mVideoChargePickerView;

    private String toChatUsername;
    private int chatType;

    private Handler handler = new Handler();
    private boolean isMessageListInited;
    private GroupListener mGroupListener;
    private ChatRoomListener mChatRoomListener;

    public static void start(Activity context, String toChatUsername) {
        start(context, toChatUsername, EaseConstant.CHATTYPE_SINGLE);
    }

    public static void start(Activity context, String toChatUsername, int chatType) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", toChatUsername);
        bundle.putInt("chatType", chatType);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChatComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chatModule(new ChatModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chat; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        toChatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID);
        chatType = bundle.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        mPresenter.setBundle(bundle);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        mPresenter.onConversationInit();
        onMessageListInit();
        initExtendMenuItem();
    }

    private void initView() {
        setTitle(toChatUsername);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            UserInfo userInfo = UserDao.getInstance().getUserEntityByHxId(toChatUsername);
            if (userInfo != null) {
                setTitle(userInfo.getNickname());
            }else{
                setTitle(toChatUsername);
            }
        } else {
            messageList.setShowUserNick(true);
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                if (group != null)
                    setTitle(group.getGroupName());
                mGroupListener = new GroupListener();
                EMClient.getInstance().groupManager().addGroupChangeListener(mGroupListener);
            } else {
                 EMChatRoom chatRoom=  EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                if (chatRoom != null)
                    setTitle(chatRoom.getName());
                mChatRoomListener = new ChatRoomListener();
                EMClient.getInstance().chatroomManager().addChatRoomChangeListener(mChatRoomListener);
                onChatRoomViewCreation();
            }
        }
//        listView = messageList.getListView();
        initInputMenu();
        initRefreshLayout();
    }

    private void initInputMenu() {
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new ChatInputMenu.ChatInputMenuListener() {
            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {
                mPresenter.sendMsgTypingBegin();
            }

            @Override
            public void onSendMessage(String content) {
                mPresenter.sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorder.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        mPresenter.sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                mPresenter.sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });
    }

    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void initExtendMenuItem(){
        List<ChatExtendItemEntity> entities = new ArrayList<>();
        if(chatType != EaseConstant.CHATTYPE_SINGLE ){
            entities.add(new ChatExtendItemEntity(ITEM_WELFARE,getString(R.string.chat_welfare),R.drawable.ic_tab_reward));
            entities.add(new ChatExtendItemEntity(ITEM_GROUP_RULES,getString(R.string.chat_group_rules),R.drawable.ic_tab_rule));
            entities.add(new ChatExtendItemEntity(ITEM_REDPACKET,getString(R.string.chat_redpacket),R.drawable.ic_tab_red));
        }
        entities.add(new ChatExtendItemEntity(ITEM_LEAGUE,getString(R.string.chat_league),R.drawable.ic_tab_join));
        entities.add(new ChatExtendItemEntity(ITEM_RECHARGE,getString(R.string.chat_recharge),R.drawable.ic_tab_recharge));
        entities.add(new ChatExtendItemEntity(ITEM_HELP,getString(R.string.chat_help),R.drawable.ic_tab_help));
        entities.add(new ChatExtendItemEntity(ITEM_GAME_RULES,getString(R.string.chat_game_rules),R.drawable.icon_plugin_rp));
        entities.add(new ChatExtendItemEntity(ITEM_CUSTOMER_SERVICE,getString(R.string.chat_customer_service),R.drawable.ic_tab_custom));
        entities.add(new ChatExtendItemEntity(ITEM_PHOTO,getString(R.string.chat_photo),R.drawable.ic_tab_photo));
        entities.add(new ChatExtendItemEntity(ITEM_CAMEAR,getString(R.string.chat_camera),R.drawable.ic_tab_camera));
        entities.add(new ChatExtendItemEntity(ITEM_MAKE_MONEY,getString(R.string.chat_make_money),R.drawable.ic_tab_money));

        inputMenu.initExtendMenuItem(entities,mOnItemClickListener);
    }

    private ChatExtendMenu.OnItemClickListener mOnItemClickListener = new ChatExtendMenu.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ChatExtendItemEntity entity = (ChatExtendItemEntity) adapter.getItem(position);
            switch (entity.getId()){
                case ITEM_WELFARE:
                    break;
            }
            showMessage(entity.getName());
        }
    };


    private void initRefreshLayout() {
        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(() -> handler.postDelayed(() -> {
            mPresenter.fetchHistoryMessages();
        }, 600));
    }

    private void onMessageListInit() {
        messageList.init(toChatUsername, chatType, new CustomChatRowProvider());
        setListItemClickListener();
        messageList.getListView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtils.hideSoftInput(mContext);
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });
        isMessageListInited = true;
    }

    private void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
//                if (username != EMClient.getInstance().getCurrentUser())
//                    PersonInfoActivity.start(mContext, username);
            }

            @Override
            public boolean onResendClick(final EMMessage message) {
                new AlertDialog.Builder(mContext)
                        .setMessage(R.string.confirm_resend)
                        .setNegativeButton(R.string.public_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.resend, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                message.setStatus(EMMessage.Status.CREATE);
                                mPresenter.sendMessage(message);
                            }
                        }).show();
                return true;
            }

            @Override
            public void onUserAvatarLongClick(String username) { }

            @Override
            public void onBubbleLongClick(EMMessage message) { }

            @Override
            public boolean onBubbleClick(EMMessage message) {
//                return onMessageBubbleClick(message);
                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    int chargeCoins = message.getIntAttribute(EaseConstant.MESSAGE_ATTR_CHARGE_COINS, 0);
                    if (chargeCoins > 0) {
                        boolean isPaid = message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_PAID, false);
                        if (!isPaid) {
                            showPayDialog(message);
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onMessageInProgress(EMMessage message) {
                message.setMessageStatusCallback(messageStatusCallback);
            }
        });
    }

    private EMCallBack messageStatusCallback = new EMCallBack() {
        @Override
        public void onSuccess() {
            if (isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onError(int code, String error) {
            Log.i("EaseChatRowPresenter", "onError: " + code + ", error: " + error);
            if (isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onProgress(int progress, String status) {
            Log.i(TAG, "onProgress: " + progress);
            if (isMessageListInited) {
                messageList.refresh();
            }
        }
    };

    @Override
    public void setBarTitle(String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                publicToolbarTitle.setText(title);
            }
        });
    }

    @Override
    public void refreshSelectLast() {
        if (isMessageListInited)
            messageList.refreshSelectLast();
    }

    @Override
    public void refreshList() {
        if (isMessageListInited)
            messageList.refresh();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void hideLoading() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (chatType != EaseConstant.CHATTYPE_SINGLE)
            getMenuInflater().inflate(R.menu.room_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。 TODO Auto-generated
         * method stub
         */
        int i = item.getItemId();
        if (i == R.id.item_redpacket) {
//            item.setTitle(R.string.canle_attention);

        } else if (i == R.id.item_room_info) {
            openActivity(GroupInfoActivity.class);


        } else if (i == android.R.id.home) {
            onBackPressedSupport();
        }

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (inputMenu.onBackPressed() && onHideKeyboard()) {
            if(chatType == EaseConstant.CHATTYPE_GROUP){
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
//            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
//                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//            }
            if (EasyUtils.isSingleActivity(this)) {
                ARouterUtils.navigation(RouterHub.MAIN_MAINACTIVITY);
            } else {
                super.onBackPressedSupport();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register the event listener when enter the foreground
        mPresenter.addMessageListener();
        // cancel the notification
        EaseUI.getInstance().getNotifier().reset();
        if (isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(this);
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister this event listener when this activity enters the background
        mPresenter.removeMessageListener();
        // remove activity from foreground activity list
        EaseUI.getInstance().popActivity(this);
        // Remove all padding actions in handler
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGroupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(mGroupListener);
        }

        if (mChatRoomListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomListener(mChatRoomListener);
        }

//        if(chatType == EaseConstant.CHATTYPE_CHATROOM){
//            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == Constants.REQUEST_CODE_MULTI_ALBUM) {
            List<String> selectPhotos = Matisse.obtainPathResult(data);
            if (selectPhotos != null && selectPhotos.size() > 0) {
                mPresenter.sendImageMessage(selectPhotos.get(0));
            }
        } else if (requestCode == Constants.REQUEST_CODE_VIDEO) {
            if (data != null) {
                int duration = data.getIntExtra("dur", 0);
                String videoPath = data.getStringExtra("path");
                File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                    ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    mPresenter.sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == Constants.REQUEST_CODE_VIDEO_ALBUM) {
            List<String> selectVideos = Matisse.obtainPathResult(data);
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
            }
        }
    }
    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(mContext, "", "Joining......");
        EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                runOnUiThread(() -> {
                    if(isFinishing() || !toChatUsername.equals(value.getId()))
                        return;
                    pd.dismiss();
                    EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                    if (room != null) {
                        setTitle(room.getName());
                        EMLog.d(TAG, "join room success : " + room.getName());
                    } else {
                        setTitle(toChatUsername);
                    }
                    mPresenter.onConversationInit();
                    onMessageListInit();

                    // Dismiss the click-to-rejoin layout.
                    llAlertKickedOff.setVisibility(View.GONE);
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                EMLog.d(TAG, "join room failure : " + error);
                runOnUiThread(() -> pd.dismiss());
                finish();
            }
        });
    }


    /**
     * clear the conversation history
     */
    protected void emptyHistory() {
        new AlertDialog.Builder(mContext)
                .setMessage(R.string.Whether_to_empty_all_chats)
                .setNegativeButton(R.string.public_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.public_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.emptyHistory();
                        messageList.refresh();
                    }
                }).show();
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            showMessage(getString(R.string.not_connect_to_server));
        } else {
//            Integer userSex = UserPreferenceManager.getInstance().getCurrentUserSex();
//            if (BaseUserEntity.FAMALE == userSex) {
//                mPresenter.sendInviteMessage(IMConstants.CALL_TYPE_VOICE);
//                return;
//            }
            VoiceCallActivity.start(mContext, toChatUsername, false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            showMessage(mContext.getString(R.string.not_connect_to_server));
        else {
//            int sexType = UserPreferenceManager.getInstance().getCurrentUserSex();
//            if (BaseUserEntity.FAMALE == sexType) {
//                mPresenter.sendInviteMessage(IMConstants.CALL_TYPE_VIDEO);
//                return;
//            }
            VideoCallActivity.start(mContext, toChatUsername, false);
            inputMenu.hideExtendMenuContainer();

        }
    }

    private void showPayDialog(EMMessage message) {
        int coins = message.getIntAttribute(EaseConstant.MESSAGE_ATTR_CHARGE_COINS, 0);
        new AlertDialog.Builder(mContext)
                .setMessage(String.format(getString(R.string.check_and_pay_coins), coins))
                .setNegativeButton(R.string.public_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.public_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        message.setAttribute(IMConstants.MESSAGE_ATTR_IS_PAID, true);
                        EMClient.getInstance().chatManager().updateMessage(message);
                        messageList.refresh();
                    }
                }).show();
    }

    private void showVideoFormSelectDialog() {
        final String[] videoSource = {"我用录制", "打开相册"};
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.public_hint)
                .setItems(videoSource, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ActionUtils.openVideoRecorder(mContext);
                        } else if (which == 1) {
                            selectVideoAlbum();
                        }
                    }
                }).show();
    }

    private void selectImageAlbum() {
        Matisse.from(mContext)
                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                .showSingleMediaType(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, AppUtils.getAppPackageName() + ".my.fileProvider"))
                .maxSelectable(1) // 图片选择的最多数量
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .theme(R.style.public_matisse)
                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
                .forResult(Constants.REQUEST_CODE_MULTI_ALBUM); // 设置作为标记的请求码
    }

    private void selectVideoAlbum() {
        Matisse.from(mContext)
                .choose(MimeType.ofVideo(), true) // 选择 mime 的类型
                .showSingleMediaType(true)
                .captureStrategy(new CaptureStrategy(true, AppUtils.getAppPackageName() + ".my.fileProvider"))
                .maxSelectable(1) // 图片选择的最多数量
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .theme(R.style.public_matisse)
                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
                .forResult(Constants.REQUEST_CODE_VIDEO_ALBUM); // 设置作为标记的请求码
    }

    //hide Keyboard
    private boolean onHideKeyboard() {
        if (KeyboardUtils.isSoftInputVisible(mContext)) {
            KeyboardUtils.hideSoftInput(mContext);
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
            return 14;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(IMConstants.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(IMConstants.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //messagee recall
                else if (message.getBooleanAttribute(IMConstants.MESSAGE_TYPE_RECALL, false)) {
                    return MESSAGE_TYPE_RECALL;
                } else if (!"".equals(message.getStringAttribute(IMConstants.MSG_ATTR_CONF_ID, ""))) {
                    return MESSAGE_TYPE_CONFERENCE_INVITE;
                } else if (IMConstants.OP_INVITE.equals(message.getStringAttribute(IMConstants.EM_CONFERENCE_OP, ""))) {
                    return MESSAGE_TYPE_LIVE_INVITE;
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
//                //recall message
//                else if(message.getBooleanAttribute(IMConstants.MESSAGE_TYPE_RECALL, false)){
//                    EaseChatRowPresenter presenter = new EaseChatRecallPresenter();
//                    return presenter;
//                } else if (!"".equals(message.getStringAttribute(IMConstants.MSG_ATTR_CONF_ID,""))) {
//                    return new ChatRowConferenceInvitePresenter();
//                } else if (IMConstants.OP_INVITE.equals(message.getStringAttribute(IMConstants.EM_CONFERENCE_OP, ""))) {
//                    return new ChatRowLivePresenter();
//                }
            }
            return null;
        }
    }


    /**
     * listen the group event
     *
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            runOnUiThread(() -> {
                if (toChatUsername.equals(groupId)) {
                    showMessage(getString(R.string.you_are_group));
                    Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            runOnUiThread(() -> {
                if (toChatUsername.equals(groupId)) {
                    showMessage(getString(R.string.the_current_group_destroyed));
                    Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }
    }

    /**
     * listen chat room event
     */
    class ChatRoomListener extends EaseChatRoomListener {

        @Override
        public void onChatRoomDestroyed(final String roomId, final String roomName) {
            runOnUiThread(() -> {
                if (toChatUsername.equals(roomId)) {
                    showMessage(getString(R.string.the_current_chat_room_destroyed));
                    Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }

        @Override
        public void onRemovedFromChatRoom(final int reason, final String roomId, final String roomName, final String participant) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (roomId.equals(toChatUsername)) {
                        if (reason == EMAChatRoomManagerListener.BE_KICKED) {
                            showMessage(getString(R.string.quiting_the_chat_room));
                            Activity activity = getActivity();
                            if (activity != null && !activity.isFinishing()) {
                                activity.finish();
                            }
                        } else { // BE_KICKED_FOR_OFFLINE
                            showMessage(getString(R.string.alert_kicked_for_offline));
                            llAlertKickedOff.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        @Override
        public void onMemberJoined(final String roomId, final String participant) {
            if (roomId.equals(toChatUsername)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        showMessage("member join:"+participant);
                    }
                });
            }
        }

        @Override
        public void onMemberExited(final String roomId, final String roomName, final String participant) {
            if (roomId.equals(toChatUsername)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        showMessage("member exit:"+participant);
                    }
                });
            }
        }
    }

}
