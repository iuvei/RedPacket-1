package com.haisheng.easeim.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.receiver.CallReceiver;
import com.haisheng.easeim.app.receiver.HeadsetReceiver;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.model.db.InviteMessgeDao;
import com.haisheng.easeim.mvp.model.db.UserDao;
import com.haisheng.easeim.mvp.model.entity.InviteMessage;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.haisheng.easeim.mvp.utils.PreferenceManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class IMHelper {
    private static final String TAG = IMHelper.class.getSimpleName();

    /**
     * data sync listener
     */
    public interface DataSyncListener {
        /**
         * sync complete
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }

    private static IMHelper instance = null;
    private ExecutorService executor;
    private Context mAppContext;

    private EaseUI easeUI;
    private IMModel mIMModel = null;

    //    private IRepositoryManager mRepositoryManager;
    private ContactModel mContactModel;

    private CallReceiver callReceiver;
    private LocalBroadcastManager broadcastManager;

    private List<UserInfo> mContactList;
    /**
     * sync groups/contacts/blacklist status listener
     */
    private boolean isGroupAndContactListenerRegisted;
    private List<DataSyncListener> syncGroupsListeners;
    private List<DataSyncListener> syncContactsListeners;
    private List<DataSyncListener> syncBlackListListeners;

    private boolean isSyncingGroupsWithServer = false;
    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingBlackListWithServer = false;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;


    private IMHelper() {
        executor = Executors.newCachedThreadPool();
    }

    public synchronized static IMHelper getInstance() {
        if (instance == null) {
            instance = new IMHelper();
        }
        return instance;
    }
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public void init(Context context){
        mAppContext = context;

//        AppComponent appComponent= ArmsUtils.obtainAppComponentFromContext(context);
//        mContactModel = new ContactModel(appComponent.repositoryManager());

        mIMModel = new IMModel(context);
        EMOptions options = initChatOptions();

//        FlowManager.initModule(IMGeneratedDatabaseHolder.class);
        if(EaseUI.getInstance().init(context,options)){
            EMClient.getInstance().setDebugMode(true);
            easeUI = EaseUI.getInstance();
            //to set user's profile and avatar
            setEaseUIProviders();
            //initialize preference manager
            PreferenceManager.init(context);
            //initialize profile manager
//            getUserProfileManager().init(context);
            //set Call options
            setCallOptions();
            setGlobalListeners();
            broadcastManager = LocalBroadcastManager.getInstance(mAppContext);
        }
    }

    /**
     * get instance of EaseNotifier
     * @return
     */
    public EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }

    public IMModel getModel(){
        return mIMModel;
    }

    private EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);
        /**
         * NOTE:你需要设置自己申请的Sender ID来使用Google推送功能，详见集成文档
         */
  /*      options.setFCMNumber("921300338324");
        //you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        // 设置是否使用 fcm，有些华为设备本身带有 google 服务，
        options.setUseFCM(mModel.isUseFCM());*/

        options.allowChatroomOwnerLeave(false);
        options.setDeleteMessagesAsExitGroup(true);
        options.setAutoAcceptGroupInvitation(true);
        // Whether the message attachment is automatically uploaded to the Hyphenate server,
        options.setAutoTransferMessageAttachments(true);
        // Set Whether auto download thumbnail, default value is true.
        options.setAutoDownloadThumbnail(true);
        return options;
    }

    private void setCallOptions() {
        HeadsetReceiver headsetReceiver = new HeadsetReceiver();
        IntentFilter headsetFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mAppContext.registerReceiver(headsetReceiver, headsetFilter);

        // min video kbps
        int minBitRate = -1;
        if (minBitRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMinVideoKbps(minBitRate);
        }

        // max video kbps
        int maxBitRate =-1;
        if (maxBitRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoKbps(maxBitRate);
        }

        // max frame rate
        int maxFrameRate = PreferenceManager.getInstance().getCallMaxFrameRate();
        if (maxFrameRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoFrameRate(maxFrameRate);
        }

        // audio sample rate
        int audioSampleRate = PreferenceManager.getInstance().getCallAudioSampleRate();
        if (audioSampleRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setAudioSampleRate(audioSampleRate);
        }

        /**
         * This function is only meaningful when your app need recording
         * If not, remove it.
         * This function need be called before the video stream started, so we set it in onCreate function.
         * This method will set the preferred video record encoding codec.
         * Using default encoding format, recorded file may not be played by mobile player.
         */
        //EMClient.getInstance().callManager().getVideoCallHelper().setPreferMovFormatEnable(true);

        // resolution
        String resolution = PreferenceManager.getInstance().getCallBackCameraResolution();
        if (resolution.equals("")) {
            resolution = PreferenceManager.getInstance().getCallFrontCameraResolution();
        }
        String[] wh = resolution.split("x");
        if (wh.length == 2) {
            try {
                EMClient.getInstance().callManager().getCallOptions().setVideoResolution(new Integer(wh[0]).intValue(), new Integer(wh[1]).intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // enabled fixed sample rate
        boolean enableFixSampleRate = PreferenceManager.getInstance().isCallFixedVideoResolution();
        EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(enableFixSampleRate);

        // Offline call push
        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(mIMModel.isPushCall());
    }

    protected void setEaseUIProviders() {
        //set user avatar to circle shape
        EaseAvatarOptions avatarOptions = new EaseAvatarOptions();
        avatarOptions.setAvatarShape(1);
        easeUI.setAvatarOptions(avatarOptions);

        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                // To get instance of EaseUser, here we get it from the user list in memory
                // You'd better cache it if you get it from your server
                EaseUser user = mIMModel.getEaseUserByUsername(username);
                return user;
//                if(username.equals(EMClient.getInstance().getCurrentUser())){
//                    BaseUserEntity
//                }else{
//
//                }
//                    return UserPreferenceManager.getInstance().getCurrUser();
//                user = getContactList().get(username);
//                if(user == null && getRobotList() != null){
//                    user = getRobotList().get(username);
//                }

                // if user is not in your contacts, set inital letter for him/her
//                if(user == null){
//                    user = new EaseUser(username);
//                }
//                return user;
            }
        });

        //set options
        easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return mIMModel.getSettingMsgSpeaker();
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
//                return mIMModel.getSettingMsgVibrate();
                return false;
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
//                return mIMModel.getSettingMsgSound();
                boolean isAllowed =  (boolean) SpUtils.getValue (mAppContext, Constants.IM.SHARED_KEY_SETTING_SOUND,true);
                if(message == null){
                    return isAllowed;
                }
                if(!isAllowed){
                    return false;
                }else{
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = mIMModel.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = mIMModel.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                return false;
               /* if(message == null){
                    return mIMModel.getSettingMsgNotification();
                }
                if(!mIMModel.getSettingMsgNotification()){
                    return false;
                }else{
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = mIMModel.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = mIMModel.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }*/
            }
        });
        //set emoji icon provider
        easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
//                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
//                for(EaseEmojicon emojicon : data.getEmojiconList()){
//                    if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
//                        return emojicon;
//                    }
//                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                return null;
            }
        });

        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, mAppContext);
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if(user != null){
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(mAppContext.getString(R.string.at_your_in_group), user.getNickname());
                    }
                    return user.getNickname() + ": " + ticker;
                }else{
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(mAppContext.getString(R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click the notification
                Intent intent = new Intent(mAppContext, ChatActivity.class);
                // open calling activity if there is call
                if(isVideoCalling){
//                    intent = new Intent(mAppContext, VideoCallActivity1.class);
                }else if(isVoiceCalling){
//                    intent = new Intent(mAppContext, VoiceCallActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // single chat message
                        bundle.putString("userId", message.getFrom());
                        bundle.putInt("chatType", IMConstants.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() is the group id
                        bundle.putString("userId", message.getTo());
                        if(chatType == ChatType.GroupChat){
                            bundle.putInt("chatType", IMConstants.CHATTYPE_GROUP);
                        }else{
                            bundle.putInt("chatType", IMConstants.CHATTYPE_CHATROOM);
                        }
                    }
                    intent.putExtras(bundle);
                }
                return intent;
            }
        });
    }

    EMConnectionListener connectionListener;
    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        syncGroupsListeners = new ArrayList<>();
        syncContactsListeners = new ArrayList<>();
        syncBlackListListeners = new ArrayList<>();

        isGroupsSyncedWithServer = mIMModel.isGroupsSynced();
        isContactsSyncedWithServer = mIMModel.isContactSynced();
        isBlackListSyncedWithServer = mIMModel.isBacklistSynced();

        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                if (error == EMError.USER_REMOVED) {
                    onUserException(IMConstants.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onUserException(IMConstants.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    onUserException(IMConstants.ACCOUNT_FORBIDDEN);
                } else if (error == EMError.USER_KICKED_BY_CHANGE_PASSWORD) {
                    onUserException(IMConstants.ACCOUNT_KICKED_BY_CHANGE_PASSWORD);
                } else if (error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                    onUserException(IMConstants.ACCOUNT_KICKED_BY_OTHER_DEVICE);
                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
                    EMLog.d(TAG, "group and contact already synced with servre");
                } else {
                    if (!isGroupsSyncedWithServer) {
                        asyncFetchGroupsFromServer(null);

                    }

                    if (!isContactsSyncedWithServer) {
                        asyncFetchContactsFromServer(null);
                    }

                    if (!isBlackListSyncedWithServer) {
//                        asyncFetchBlackListFromServer(null);
                    }
                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }
        //会议 事件监听
  /*      EMClient.getInstance().conferenceManager().addConferenceListener(new EMConferenceListener() {
            @Override public void onMemberJoined(EMConferenceMember member) {
                EMLog.i(TAG, String.format("member joined username: %s, member: %d", member.memberName,
                        EMClient.getInstance().conferenceManager().getConferenceMemberList().size()));
            }

            @Override public void onMemberExited(EMConferenceMember member) {
                EMLog.i(TAG, String.format("member exited username: %s, member size: %d", member.memberName,
                        EMClient.getInstance().conferenceManager().getConferenceMemberList().size()));
            }

            @Override public void onStreamAdded(EMConferenceStream stream) {
                EMLog.i(TAG, String.format("Stream added streamId: %s, streamName: %s, memberName: %s, username: %s, extension: %s, videoOff: %b, mute: %b",
                        stream.getStreamId(), stream.getStreamName(), stream.getMemberName(), stream.getUsername(),
                        stream.getExtension(), stream.isVideoOff(), stream.isAudioOff()));
                EMLog.i(TAG, String.format("Conference stream subscribable: %d, subscribed: %d",
                        EMClient.getInstance().conferenceManager().getAvailableStreamMap().size(),
                        EMClient.getInstance().conferenceManager().getSubscribedStreamMap().size()));
            }

            @Override public void onStreamRemoved(EMConferenceStream stream) {
                EMLog.i(TAG, String.format("Stream removed streamId: %s, streamName: %s, memberName: %s, username: %s, extension: %s, videoOff: %b, mute: %b",
                        stream.getStreamId(), stream.getStreamName(), stream.getMemberName(), stream.getUsername(),
                        stream.getExtension(), stream.isVideoOff(), stream.isAudioOff()));
                EMLog.i(TAG, String.format("Conference stream subscribable: %d, subscribed: %d",
                        EMClient.getInstance().conferenceManager().getAvailableStreamMap().size(),
                        EMClient.getInstance().conferenceManager().getSubscribedStreamMap().size()));
            }

            @Override public void onStreamUpdate(EMConferenceStream stream) {
                EMLog.i(TAG, String.format("Stream added streamId: %s, streamName: %s, memberName: %s, username: %s, extension: %s, videoOff: %b, mute: %b",
                        stream.getStreamId(), stream.getStreamName(), stream.getMemberName(), stream.getUsername(),
                        stream.getExtension(), stream.isVideoOff(), stream.isAudioOff()));
                EMLog.i(TAG, String.format("Conference stream subscribable: %d, subscribed: %d",
                        EMClient.getInstance().conferenceManager().getAvailableStreamMap().size(),
                        EMClient.getInstance().conferenceManager().getSubscribedStreamMap().size()));
            }

            @Override public void onPassiveLeave(int error, String message) {
                EMLog.i(TAG, String.format("passive leave code: %d, message: %s", error, message));
            }

            @Override public void onConferenceState(ConferenceState state) {
                EMLog.i(TAG, String.format("State code=%d", state.ordinal()));
            }

            @Override public void onStreamStatistics(EMStreamStatistics statistics) {
                EMLog.d(TAG, statistics.toString());
            }

            @Override public void onStreamSetup(String streamId) {
                EMLog.i(TAG, String.format("Stream id - %s", streamId));
            }

            @Override
            public void onSpeakers(List<String> speakers) {}

            @Override public void onReceiveInvite(String confId, String password, String extension) {
                EMLog.i(TAG, String.format("Receive conference invite confId: %s, password: %s, extension: %s", confId, password, extension));
                goConference(confId, password, extension);
            }

            @Override
            public void onRoleChanged(EMConferenceManager.EMConferenceRole role) {
            }
        });*/
        //register incoming call receiver
        mAppContext.registerReceiver(callReceiver, callFilter);
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
        //register group and contact event listener
        registerGroupAndContactListener();
        //register message event listener
        registerMessageListener();

    }

    /**
     * register group and contact listener, you need register when login
     */
    public void registerGroupAndContactListener(){
        if(!isGroupAndContactListenerRegisted){
            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
//            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
//            EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
            isGroupAndContactListenerRegisted = true;
        }
    }
    /**
     * group change listener
     */
    class MyGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {

//            new InviteMessgeDao(appContext).deleteMessage(groupId);
            InviteMessgeDao.getInstance().deleteMessage(groupId);
            // user invite you to join group
            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            msg.setGroupInviter(inviter);
            showToast("receive invitation to join the group：" + groupName);
            msg.setStatus(InviteMessage.InviteMessageStatus.GROUPINVITATION.ordinal());
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onInvitationAccepted(String groupId, String invitee, String reason) {

//            new InviteMessgeDao(appContext).deleteMessage(groupId);
            InviteMessgeDao.getInstance().deleteMessage(groupId);

            //user accept your invitation
            boolean hasGroup = false;
            EMGroup _group = null;
            for (EMGroup group : EMClient.getInstance().groupManager().getAllGroups()) {
                if (group.getGroupId().equals(groupId)) {
                    hasGroup = true;
                    _group = group;
                    break;
                }
            }
            if (!hasGroup)
                return;

            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(_group == null ? groupId : _group.getGroupName());
            msg.setReason(reason);
            msg.setGroupInviter(invitee);
            showToast(invitee + "Accept to join the group：" + _group == null ? groupId : _group.getGroupName());
            msg.setStatus(InviteMessage.InviteMessageStatus.GROUPINVITATION_ACCEPTED.ordinal());
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {

//            new InviteMessgeDao(mAppContext).deleteMessage(groupId);
            InviteMessgeDao.getInstance().deleteMessage(groupId);
            //user declined your invitation
            EMGroup group = null;
            for (EMGroup _group : EMClient.getInstance().groupManager().getAllGroups()) {
                if (_group.getGroupId().equals(groupId)) {
                    group = _group;
                    break;
                }
            }
            if (group == null)
                return;

            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(group.getGroupName());
            msg.setReason(reason);
            msg.setGroupInviter(invitee);
            showToast(invitee + "Declined to join the group：" + group.getGroupName());
            msg.setStatus(InviteMessage.InviteMessageStatus.GROUPINVITATION_DECLINED.ordinal());
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            //user is removed from group
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
            showToast("current user removed, groupId:" + groupId);
        }

        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            // group is dismissed,
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
            showToast("group destroyed, groupId:" + groupId);
        }

        @Override
        public void onRequestToJoinReceived(String groupId, String groupName, String applyer, String reason) {

            // user apply to join group
            InviteMessage msg = new InviteMessage();
            msg.setFrom(applyer);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            showToast(applyer + " Apply to join group：" + groupId);
            msg.setStatus(InviteMessage.InviteMessageStatus.BEAPPLYED.ordinal());
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {

            String st4 = mAppContext.getString(R.string.Agreed_to_your_group_chat_application);
            // your application was accepted
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(accepter + " " +st4));
            msg.setStatus(EMMessage.Status.SUCCESS);
            // save accept message
            EMClient.getInstance().chatManager().saveMessage(msg);
            // notify the accept message
            getNotifier().vibrateAndPlayTone(msg);

            showToast("request to join accepted, groupId:" + groupId);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
            // your application was declined, we do nothing here in demo
            showToast("request to join declined, groupId:" + groupId);
        }

        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // got an invitation
            String st3 = mAppContext.getString(R.string.Invite_you_to_join_a_group_chat);
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(inviter + " " +st3));
            msg.setStatus(EMMessage.Status.SUCCESS);
            // save invitation as messages
            EMClient.getInstance().chatManager().saveMessage(msg);
            // notify invitation message
            getNotifier().vibrateAndPlayTone(msg);
            showToast("auto accept invitation from groupId:" + groupId);
            broadcastManager.sendBroadcast(new Intent(IMConstants.ACTION_GROUP_CHANAGED));
        }

        // ============================= group_reform new add api begin
        @Override
        public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
            StringBuilder sb = new StringBuilder();
            for (String member : mutes) {
                sb.append(member).append(",");
            }
            showToast("onMuterListAdded: " + sb.toString());
        }


        @Override
        public void onMuteListRemoved(String groupId, final List<String> mutes) {
            StringBuilder sb = new StringBuilder();
            for (String member : mutes) {
                sb.append(member).append(",");
            }
            showToast("onMuterListRemoved: " + sb.toString());
        }


        @Override
        public void onAdminAdded(String groupId, String administrator) {
            showToast("onAdminAdded: " + administrator);
        }

        @Override
        public void onAdminRemoved(String groupId, String administrator) {
            showToast("onAdminRemoved: " + administrator);
        }

        @Override
        public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
            showToast("onOwnerChanged new:" + newOwner + " old:" + oldOwner);
        }

        @Override
        public void onMemberJoined(String groupId, String member) {
            showToast("onMemberJoined: " + member);
        }

        @Override
        public void onMemberExited(String groupId, String member) {
            showToast("onMemberExited: " + member);
        }

        @Override
        public void onAnnouncementChanged(String groupId, String announcement) {
            showToast("onAnnouncementChanged, groupId" + groupId);
        }

        @Override
        public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {
            showToast("onSharedFileAdded, groupId" + groupId);
        }

        @Override
        public void onSharedFileDeleted(String groupId, String fileId) {
            showToast("onSharedFileDeleted, groupId" + groupId);
        }
        // ============================= group_reform new add api end
    }


    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // 判断一下是否是会议邀请
//                    String confId = message.getStringAttribute(IMConstants.MSG_ATTR_CONF_ID, "");
//                    if(!"".equals(confId)){
//                        String password = message.getStringAttribute(IMConstants.MSG_ATTR_CONF_PASS, "");
//                        String extension = message.getStringAttribute(IMConstants.MSG_ATTR_EXTENSION, "");
//                        goConference(confId, password, extension);
//                    }
                    // in background, do not refresh UI, notify it in notification bar
                    if(!easeUI.hasForegroundActivies()){
//                        getNotifier().notify(message);
                        getNotifier().vibrateAndPlayTone(message);
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action,message.toString()));
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                for (EMMessage msg : messages) {
                    if(msg.getChatType() == ChatType.GroupChat && EaseAtMessageHelper.get().isAtMeMsg(msg)){
                        EaseAtMessageHelper.get().removeAtMeGroup(msg.getTo());
                    }
                    EMMessage msgNotification = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
                    EMTextMessageBody txtBody = new EMTextMessageBody(String.format(mAppContext.getString(R.string.msg_recall_by_user), msg.getFrom()));
                    msgNotification.addBody(txtBody);
                    msgNotification.setFrom(msg.getFrom());
                    msgNotification.setTo(msg.getTo());
                    msgNotification.setUnread(false);
                    msgNotification.setMsgTime(msg.getMsgTime());
                    msgNotification.setLocalTime(msg.getMsgTime());
                    msgNotification.setChatType(msg.getChatType());
                    msgNotification.setAttribute(IMConstants.MESSAGE_TYPE_RECALL, true);
                    msgNotification.setStatus(EMMessage.Status.SUCCESS);
                    EMClient.getInstance().chatManager().saveMessage(msgNotification);
                }
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                EMLog.d(TAG, "change:");
                EMLog.d(TAG, "change:" + change);
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    void showToast(final String message) {
        Log.d(TAG, "receive invitation to join the group：" + message);

//		ToastUtils.showShort(message);
    }
    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * update contact list
     *
     * @param userEntities
     */
    public void setContactList(List<UserInfo> userEntities) {
        if(mContactList == null){
            if (mContactList != null) {
                mContactList.clear();
            }
            return;
        }
        mContactList = userEntities;
    }

    /**
     * save single contact
     */
    public void saveContact(UserInfo user){
        if(null == mContactList){
            mContactList = new ArrayList<>();
        }
        mContactList.add(user);
        UserDao.getInstance().saveContact(user);
    }

    /**
     * get contact list
     *
     * @return
     */
    public List<UserInfo> getContactList() {
        if (isLoggedIn() && mContactList == null) {
            mContactList = UserDao.getInstance().getContactList();
        }
        // return a empty non-null object to avoid app crash
        if(mContactList == null){
            return new ArrayList<>();
        }
        return mContactList;
    }

    /**
     * update user list to cache and database
     *
     * @param userEntities
     */
    public void updateContactList(List<UserInfo> userEntities) {
        mContactList = userEntities;
        UserDao.getInstance().saveContactList(userEntities);
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * logout
     *
     * @param unbindDeviceToken
     *            whether you need unbind your device token
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * save and notify invitation message
     * @param msg
     */
    private void notifyNewInviteMessage(InviteMessage msg){

        InviteMessgeDao.getInstance().saveMessage(msg);
        InviteMessgeDao.getInstance().saveUnreadMessageCount(1);
        // notify there is new message
        getNotifier().vibrateAndPlayTone(null);
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    @SuppressLint("WrongConstant")
    protected void onUserException(String exception){
        EMLog.e(TAG, "onUserException: " + exception);
        Bundle bundle = new Bundle();
        bundle.putBoolean(exception,true);

        ARouter.getInstance()
                .build(RouterHub.APP_MAINACTIVITY)
                .with( bundle)  //参数：键：key 值：123
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                .navigation();
    }

    /**
     * Get group list from server
     * This method will save the sync state
     * @throws HyphenateException
     */
    public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback){
        if(isSyncingGroupsWithServer){
            return;
        }
        isSyncingGroupsWithServer = true;
        new Thread(){
            @Override
            public void run(){
                try {
                    List<EMGroup> groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    // in case that logout already before server returns, we should return immediately
                    if(!isLoggedIn()){
                        isGroupsSyncedWithServer = false;
                        isSyncingGroupsWithServer = false;
                        noitifyGroupSyncListeners(false);
                        return;
                    }
                    mIMModel.setGroupsSynced(true);

                    isGroupsSyncedWithServer = true;
                    isSyncingGroupsWithServer = false;
                    //notify sync group list success
                    noitifyGroupSyncListeners(true);

                    if(callback != null){
                        callback.onSuccess();
                    }
                } catch (HyphenateException e) {
                    mIMModel.setGroupsSynced(false);
                    isGroupsSyncedWithServer = false;
                    isSyncingGroupsWithServer = false;
                    noitifyGroupSyncListeners(false);
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }
            }
        }.start();
    }

    public void noitifyGroupSyncListeners(boolean success){
        for (DataSyncListener listener : syncGroupsListeners) {
            listener.onSyncComplete(success);
        }
    }

    public void asyncFetchContactsFromServer(final EMValueCallBack<List<String>> callback){
        if(isSyncingContactsWithServer){
            return;
        }
        isSyncingContactsWithServer = true;

        new Thread(){
            @Override
            public void run(){
                List<String> usernames = null;
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                    selfIds = EMClient.getInstance().contactManager().getSelfIdsOnOtherPlatform();
                    // in case that logout already before server returns, we should return immediately
                    if(!isLoggedIn()){
                        isContactsSyncedWithServer = false;
                        isSyncingContactsWithServer = false;
                        notifyContactsSyncListener(false);
                        return;
                    }
//                    if (selfIds.size() > 0) {
//                        usernames.addAll(selfIds);
//                    }
                    List<UserInfo> userEntities = new ArrayList<>();
//                    Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
                    for (String username : usernames) {
                        UserInfo user = new UserInfo();
                        user.setHxId(username);
                        userEntities.add(user);
                    }

                    // save the contact list to cache
                    updateContactList(userEntities);

                    mIMModel.setContactSynced(true);
                    EMLog.d(TAG, "set contact syn status to true");

                    isContactsSyncedWithServer = true;
                    isSyncingContactsWithServer = false;

                    //notify sync success
                    notifyContactsSyncListener(true);

                    if(callback != null){
                        callback.onSuccess(usernames);
                    }
                } catch (HyphenateException e) {
                    mIMModel.setContactSynced(false);
                    isContactsSyncedWithServer = false;
                    isSyncingContactsWithServer = false;
                    notifyContactsSyncListener(false);
                    e.printStackTrace();
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    public void  notifyContactsSyncListener(boolean success){
        for (DataSyncListener listener : syncContactsListeners) {
            listener.onSyncComplete(success);
        }
    }

    synchronized void reset(){
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        mIMModel.setGroupsSynced(false);
        mIMModel.setContactSynced(false);
        mIMModel.setBlacklistSynced(false);

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        isGroupAndContactListenerRegisted = false;

//        setContactList(null);
//        setRobotList(null);
//        getUserProfileManager().reset();
    }

    /**
     * 获取所有会话
     * @return
     */
    public List<EMConversation> getAllConversations() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<EMConversation> lists = new ArrayList <> (  );
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet())
        {
            lists.add ( entry.getValue() );
        }
        return lists;
    }

    public void delectAllMessage(){
        List <com.hyphenate.chat.EMConversation> allConversation = getAllConversations();
        for (int i=0;i<allConversation.size ();i++) {
            //删除和某个user会话，如果需要保留聊天记录，传false
            allConversation.get ( i ).clearAllMessages ();
        }
    }

    /**
     * 删除好友
     */
    public void delectContact(String username){
        try {
            EMClient.getInstance().contactManager().deleteContact(username);
        } catch (HyphenateException e) {
            e.printStackTrace ();
        }
    }
    /**
     * 删除会话记录
     */
    public void delectMessageRecord(String username){
            //删除和某个user会话，如果需要保留聊天记录，传false
            EMClient.getInstance().chatManager().deleteConversation(username, true);
    }

}
