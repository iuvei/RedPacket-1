package com.haisheng.easeim.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.app.IMHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAChatRoomManagerListener;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseChatRoomListener;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.jessyan.armscomponent.commonsdk.entity.GiftEntity;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.ChatContract;

import java.io.File;
import java.util.List;


@ActivityScope
public class ChatPresenter extends BasePresenter<ChatContract.Model, ChatContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    protected EMConversation conversation;
    // "正在输入"功能的开关，打开后本设备发送消息将持续发送cmd类型消息通知对方"正在输入"
    private boolean turnOnTyping = false;
    protected static final int MSG_TYPING_BEGIN = 0;
    protected static final int MSG_TYPING_END = 1;
    protected static final String ACTION_TYPING_BEGIN = "TypingBegin";
    protected static final String ACTION_TYPING_END = "TypingEnd";
    protected static final int TYPING_SHOW_TIME = 5000;

    private int chatType;
    private String toChatUsername;
    private boolean isRoaming = false;
    private boolean haveMoreData = true;
    private int pagesize = 20;

    @Inject
    public ChatPresenter(ChatContract.Model model, ChatContract.View rootView) {
        super(model, rootView);
    }

    public void setBundle(Bundle bundle){
        chatType = bundle.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        toChatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID);
        isRoaming = IMHelper.getInstance().getModel().isMsgRoaming() && (chatType != EaseConstant.CHATTYPE_CHATROOM);
    }

    public void sendMsgTypingBegin(){
        mTypingHandler.sendEmptyMessage(MSG_TYPING_BEGIN);
    }
    // to handle during-typing actions.
    private Handler mTypingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TYPING_BEGIN: // Notify typing start
                    if (!turnOnTyping) return;
                    // Only support single-chat type conversation.
                    if (chatType != EaseConstant.CHATTYPE_SINGLE)
                        return;
                    if (hasMessages(MSG_TYPING_END)) {
                        // reset the MSG_TYPING_END handler msg.
                        removeMessages(MSG_TYPING_END);
                    } else {
                        // Send TYPING-BEGIN cmd msg
                        EMMessage beginMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                        EMCmdMessageBody body = new EMCmdMessageBody(ACTION_TYPING_BEGIN);
                        // Only deliver this cmd msg to online users
                        body.deliverOnlineOnly(true);
                        beginMsg.addBody(body);
                        beginMsg.setTo(toChatUsername);
                        EMClient.getInstance().chatManager().sendMessage(beginMsg);
                    }
                    sendEmptyMessageDelayed(MSG_TYPING_END, TYPING_SHOW_TIME);
                    break;

                case MSG_TYPING_END:
                    if (!turnOnTyping) return;
                    // Only support single-chat type conversation.
                    if (chatType != EaseConstant.CHATTYPE_SINGLE)
                        return;
                    // remove all pedding msgs to avoid memory leak.
                    removeCallbacksAndMessages(null);
                    // Send TYPING-END cmd msg
                    EMMessage endMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                    EMCmdMessageBody body = new EMCmdMessageBody(ACTION_TYPING_END);
                    // Only deliver this cmd msg to online users
                    body.deliverOnlineOnly(true);
                    endMsg.addBody(body);
                    endMsg.setTo(toChatUsername);
                    EMClient.getInstance().chatManager().sendMessage(endMsg);
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    public void onConversationInit(){
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        conversation.markAllMessagesAsRead();
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }
    }

    public void addMessageListener(){
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    public void removeMessageListener(){
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }


    public void fetchHistoryMessages(){
        if (!isRoaming) {
            loadMoreLocalMessage();
        } else {
            loadMoreRoamingMessages();
        }
    }

    private String getMsgId(){
        return (conversation.getAllMessages() != null && conversation.getAllMessages().size() > 0) ? conversation.getAllMessages().get(0).getMsgId() : "";
    }

    public void loadMoreLocalMessage() {
        if (!haveMoreData) {
            mRootView.hideLoading();
            mRootView.showMessage(mApplication.getString(R.string.no_more_messages));
            return;
        }
//        listView.getFirstVisiblePosition() == 0
        mModel.loadMoreMsgFromDB(conversation,getMsgId(),pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EMMessage>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<EMMessage>> baseResponse) {
                        List<EMMessage> messages = baseResponse.getResult();
                        if (null!=messages && messages.size() > 0) {
                            mRootView.refreshSelectLast();
                            if (messages.size() != pagesize) {
                                haveMoreData = false;
                            }
                        } else {
                            haveMoreData = false;
                        }
                    }
                });
    }

    public void loadMoreRoamingMessages() {
        if (!haveMoreData) {
            mRootView.hideLoading();
            mRootView.showMessage(mApplication.getString(R.string.no_more_messages));
            return;
        }
        mModel.fetchHistoryMessages( toChatUsername, chatType, pagesize, getMsgId())
                .concatMap(baseResponse -> mModel.loadMoreMsgFromDB(conversation, getMsgId(), pagesize))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EMMessage>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<EMMessage>> baseResponse) {
                        List<EMMessage> messages = baseResponse.getResult();
                        if (null!=messages && messages.size() > 0) {
                            mRootView.refreshSelectLast();
                            if (messages.size() != pagesize) {
                                haveMoreData = false;
                            }
                        } else {
                            haveMoreData = false;
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    private EMMessageListener mMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            for (EMMessage message : messages) {
                String username = null;
                // group message
                if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                    username = message.getTo();
                } else {
                    // single chat message
                    username = message.getFrom();
                }
                // if the message is for current conversation
                if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername) || message.conversationId().equals(toChatUsername)) {
                    mRootView.refreshSelectLast();
                    conversation.markMessageAsRead(message.getMsgId());
                }
                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            for (final EMMessage msg : messages) {
                final EMCmdMessageBody body = (EMCmdMessageBody) msg.getBody();
                EMLog.i(TAG, "Receive cmd message: " + body.action() + " - " + body.isDeliverOnlineOnly());
                if (ACTION_TYPING_BEGIN.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                    mRootView.setBarTitle(mApplication.getString(R.string.alert_during_typing));
                } else if (ACTION_TYPING_END.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                    mRootView.setBarTitle(toChatUsername);
                }
            }
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
            mRootView.refreshSelectLast();
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            mRootView.refreshList();
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
            mRootView.refreshList();
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
            mRootView.refreshList();
        }
    };


    /**
     * send image
     *
     * @param selectedImage
     */
    public void sendPicByUri(int chargeCoins,Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = mApplication.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                mRootView.showMessage(mApplication.getString(R.string.cant_find_pictures));
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                mRootView.showMessage(mApplication.getString(R.string.cant_find_pictures));
                return;
            }
            sendImageMessage(file.getAbsolutePath());
        }
    }

    //send message
    public void sendTextMessage(String content) {
        if(EaseAtMessageHelper.get().containsAtUsername(content)){
            sendAtMessage(content);
        }else{
            EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
//            int chargeCoins = UserPreferenceManager.getInstance().getCurrentUserMessageCoinPM();
//            message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS,chargeCoins);
            sendMessage(message);
        }
    }

    /**
     * send @ message, only support group chat message
     * @param content
     */
    @SuppressWarnings("ConstantConditions")
    public void sendAtMessage(String content){
        if(chatType != EaseConstant.CHATTYPE_GROUP){
            EMLog.e(TAG, "only support group chat message");
            return;
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
        if(EMClient.getInstance().getCurrentUser().equals(group.getOwner()) && EaseAtMessageHelper.get().containsAtAll(content)){
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL);
        }else {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG,
                    EaseAtMessageHelper.get().atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)));
        }
        sendMessage(message);
    }

    //发送邀请
    public void sendInviteMessage(int inviteType){
        String content;
        if(inviteType == IMConstants.CALL_TYPE_VOICE ){
            content = mApplication.getString(R.string.send_voice_invite);
        }else{
            content = mApplication.getString(R.string.send_video_invite);
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        // 增加自己特定的属性
        message.setAttribute(IMConstants.MESSAGE_ATTR_IS_CALL_INVITE,true);
        message.setAttribute(IMConstants.MESSAGE_ATTR_CALL_INVITE_TYPE, inviteType);
        sendMessage(message);
    }
    public void sendGiftMessage(GiftEntity entity){
        EMMessage message = EMMessage.createTxtSendMessage(entity.getName(), toChatUsername);
        // 增加自己特定的属性
        message.setAttribute(IMConstants.MESSAGE_ATTR_IS_GIFT, true);
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_ID, entity.getId());
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_NAME, entity.getName());
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_ICON_URL, entity.getIconUrl());
        message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS, entity.getNeedCoin());
        sendMessage(message);
    }

    public void sendGiftAskMessage(GiftEntity entity){
        EMMessage message = EMMessage.createTxtSendMessage(entity.getName(), toChatUsername);
        // 增加自己特定的属性
        message.setAttribute(IMConstants.MESSAGE_ATTR_IS_GIFT_ASK, true);
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_ID, entity.getId());
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_NAME, entity.getName());
        message.setAttribute(IMConstants.MESSAGE_ATTR_GIFT_ICON_URL, entity.getIconUrl());
        message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS, entity.getNeedCoin());
        sendMessage(message);
    }

    public void sendBigExpressionMessage(String name, String identityCode){
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        sendMessage(message);
    }

    public void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    public void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
//        message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS,chargeCoins);
        sendMessage(message);
    }

    public void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    public void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
//        message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS,chargeCoins);
        sendMessage(message);
    }

    public void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        sendMessage(message);
    }


    public void sendMessage(EMMessage message){
        if (message == null) {
            return;
        }

        if (chatType == EaseConstant.CHATTYPE_GROUP){
            message.setChatType(EMMessage.ChatType.GroupChat);
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }

        message.setMessageStatusCallback(messageStatusCallback);

        // Send message.
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        mRootView.refreshSelectLast();
    }

    protected EMCallBack messageStatusCallback = new EMCallBack() {
        @Override
        public void onSuccess() {
            mRootView.refreshList();
        }

        @Override
        public void onError(int code, String error) {
            Log.i("EaseChatRowPresenter", "onError: " + code + ", error: " + error);
            mRootView.refreshList();
        }

        @Override
        public void onProgress(int progress, String status) {
            Log.i(TAG, "onProgress: " + progress);
            mRootView.refreshList();
        }
    };

    public void emptyHistory(){
        if (conversation != null) {
            conversation.clearAllMessages();
        }
        haveMoreData = true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
