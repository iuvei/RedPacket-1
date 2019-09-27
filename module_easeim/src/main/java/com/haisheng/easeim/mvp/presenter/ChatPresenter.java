package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.AppLifecyclesImpl;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.contract.ChatContract;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.CheckRedpacketInfo;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.entity.GiftEntity;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.http.CommonModel;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
    @Inject
    ChatRoomModel mChatRoomModel;
    @Inject
    RedpacketModel mRedpacketModel;
    @Inject
    CommonModel mCommonModel;

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

    public void initDatas(String toChatUsername,int chatType){
        this.toChatUsername = toChatUsername;
        this.chatType = chatType;
    }

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

    public void joinRoom(final String roomId){
        mChatRoomModel.joinChatRoom(roomId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EMChatRoom>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EMChatRoom> response) {
                        if (response.isSuccess()) {
                            mRootView.joinRoomSuccessfully();

                        }else{
                            mRootView.showMessage(response.getMessage());
                            mRootView.killMyself();
                        }
                    }
                });
    }

    public void getBalanceInfo(boolean isShowDialog){
        mCommonModel.getBalance()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if(isShowDialog)
                        mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(isShowDialog)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Double>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Double> response) {
                        if (response.isSuccess()) {
                            mRootView.setBalanceInfo(response.getResult());
                            AppLifecyclesImpl.setBalance ( response.getResult () );
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }

    private String getMsgId(){
        if (conversation==null){
            return "";
        }
        return (conversation.getAllMessages() != null && conversation.getAllMessages().size() > 0) ? conversation.getAllMessages().get(0).getMsgId() : "";
    }

    public void loadMoreLocalMessage() {
        if (!haveMoreData) {
            mRootView.hideLoading();
            mRootView.showMessage(mApplication.getString(R.string.no_more_messages));
            mRootView.finishRefresh();//隐藏下拉刷新的进度条
            return;
        }
        mModel.loadMoreMsgFromDB(conversation,getMsgId(),pagesize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.finishRefresh();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
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
            mRootView.finishRefresh();//隐藏下拉刷新的进度条
            return;
        }
        mModel.fetchHistoryMessages( toChatUsername, chatType, pagesize, getMsgId())
                .concatMap(baseResponse -> mModel.loadMoreMsgFromDB(conversation, getMsgId(), pagesize))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.finishRefresh();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
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
//                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
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

    public void sendRedpacketMessage(RedpacketBean redpacketInfo){
        int type = redpacketInfo.getType();
        String sMessage = "[红包]";
        if(type == IMConstants.MSG_TYPE_MINE_REDPACKET){
            sMessage = "[扫雷红包]";
        }else if(type == IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET){
            sMessage = "[禁抢红包]";
        }else if(type == IMConstants.MSG_TYPE_NIUNIU_REDPACKET){
            sMessage = "[牛牛红包]";
        }else if(type == IMConstants.MSG_TYPE_WELFARE_REDPACKET){
            sMessage = "[福利红包]";
        }
        EMMessage message = EMMessage.createTxtSendMessage(sMessage, toChatUsername);
        // 增加自己特定的属性
        message.setAttribute(IMConstants.MESSAGE_ATTR_TYPE, redpacketInfo.getType());
        try {
            message.setAttribute(IMConstants.MESSAGE_ATTR_CONENT, new JSONObject(new Gson().toJson(redpacketInfo)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        message.setAttribute(EaseConstant.MESSAGE_ATTR_AVATARURL, UserPreferenceManager.getInstance().getCurrentUserAvatarUrl());
        message.setAttribute(EaseConstant.MESSAGE_ATTR_NICKNAME, UserPreferenceManager.getInstance().getCurrentUserNick());
        message.setMessageStatusCallback(messageStatusCallback);
        // Send message.
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        mRootView.refreshSelectLast();
    }

    //发送领取红包信息
    public void sendGetRedPacketMessage(Context context,RedpacketBean redpacketBean) {
        EMMessage message = EMMessage.createTxtSendMessage ("领取红包", toChatUsername);
        //领取红包的类型
        message.setAttribute ( IMConstants.MESSAGE_ATTR_TYPE,IMConstants.MSG_TYPE_GET_REDPACKET );
        String nickname = SpUtils.getValue ( context,"nickname","" );
        String id = SpUtils.getValue ( context,"hxid","" );
        if (id.equals ( redpacketBean.getHxid () )){
            //自己领取自己发出的红包
            message.setAttribute ( IMConstants.GET_REDPACKET_MSG_SENDNAME,nickname );
            message.setAttribute ( IMConstants.GET_REDPACKET_MSG_SENDHXID,id );
        }else{
            //领取了别人的包
            message.setAttribute ( IMConstants.GET_REDPACKET_MSG_SENDNAME,redpacketBean.getNickname () );
            message.setAttribute ( IMConstants.GET_REDPACKET_MSG_SENDHXID,redpacketBean.getHxid () );
        }
        message.setAttribute ( IMConstants.GET_REDPACKET_MSG_GETNAME,nickname);
        message.setAttribute ( IMConstants.GET_REDPACKET_MSG_GETHXID,id);
        message.setAttribute ( IMConstants.GET_REDPACKET_MSG_TYPE,IMConstants.GET_REDPACKET_MSG_CLUES );
//        message.setAttribute(IMConstants.MESSAGE_ATTR_CHARGE_COINS,chargeCoins);
        message.setChatType(EMMessage.ChatType.ChatRoom);
        EMClient.getInstance().chatManager ().sendMessage ( message );
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

    public void checkRedpacket(Long roomId,RedpacketBean redpacketBean,EMMessage message){
        mRedpacketModel.checkRedpacket(roomId,redpacketBean.getId(),redpacketBean.getWelfareStatus())
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CheckRedpacketInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CheckRedpacketInfo> response) {
                        if (response.isSuccess()) {
                            CheckRedpacketInfo checkRedpacketInfo = response.getResult();
                            if(checkRedpacketInfo.getStatus() != 0){
                                message.setAttribute(IMConstants.MESSAGE_ATTR_REDPACKET_STATUS,response.getStatus());
                                EMClient.getInstance().chatManager().saveMessage(message);
                                mRootView.refreshList();
                            }
                            mRootView.showRedPacket(checkRedpacketInfo,message);
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }

    public void grabRedpacket(Long roomId, RedpacketBean redpacketBean, EMMessage message, View animView){
        mRedpacketModel.grabRedpacket(roomId,redpacketBean.getId(),redpacketBean.getWelfareStatus())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.openAnimation(animView);
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.closeAnimation(animView);
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            message.setAttribute(IMConstants.MESSAGE_ATTR_REDPACKET_STATUS,1);
                            EMClient.getInstance().chatManager().saveMessage(message);
                            mRootView.refreshList();

                            mRootView.grabRedpacketSuccessfully(redpacketBean.getId(),redpacketBean.getWelfareStatus(),redpacketBean.getRoomType (),redpacketBean);
                        }else{
                            mRootView.grabRedpacketFail();
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.grabRedpacketFail();
                    }
                });
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
