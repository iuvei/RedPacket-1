package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.os.Handler;
import android.util.Pair;

import com.haisheng.easeim.mvp.model.ConversationModel;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.ConversationListContract;
import com.jess.arms.mvp.IModel;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@FragmentScope
public class ConversationListPresenter extends BasePresenter<IModel, ConversationListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ConversationModel mConversationModel;

    private int mTag;
    private final static int MSG_REFRESH = 2;
    private List<EMConversation> conversationList;

    @Inject
    public ConversationListPresenter( ConversationListContract.View rootView) {
        super(rootView);
    }

    public void initDatas(int tag){
        mTag =tag;
        EMClient.getInstance().addConnectionListener(connectionListener);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mRootView.onConnectionDisconnected();
                    break;
                case 1:
                    mRootView.onConnectionConnected();
                    break;

                case MSG_REFRESH:
                    refreshConversationList();
                    break;

                default:
                    break;
            }
        }
    };

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                mRootView.setConflict(true);
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    private EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {

            sendRefreshOrder();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
//            refresh();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {}

        @Override
        public void onMessageDelivered(List<EMMessage> message) {}

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            sendRefreshOrder();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    public void sendRefreshOrder(){
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    private void refreshConversationList(){
        if(null == conversationList){
            conversationList = new ArrayList<>();
        }else{
            conversationList.clear();
        }
        conversationList.addAll(mConversationModel.loadConversationListByTag(mTag));
        mRootView.setConversationList(conversationList);

        mRootView.hideLoading();
    }

    public void delectConversation(String username){
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(username, true);
    }

    @Subscriber(tag = EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION)
    private void updateWithTag(String msg){
        sendRefreshOrder();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeConnectionListener(connectionListener);
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
