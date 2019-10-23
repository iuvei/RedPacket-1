package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.os.Handler;

import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.ui.adapter.RoomListAdapter;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.RoomListContract;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;


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
@FragmentScope
public class RoomListPresenter extends BasePresenter<IModel, RoomListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RedpacketModel mRedpacketModel;
    @Inject
    ChatRoomModel mChatRoomModel;

    @Inject
    List<ChatRoomBean> mDatas;
    @Inject
    RoomListAdapter mAdapter;

    private int mTypeStatus;
    private boolean mIsInit = false;
    private final static int MSG_REFRESH = 2;

    @Inject
    public RoomListPresenter(RoomListContract.View rootView) {
        super(rootView);
    }

    public void initDatas(int typeStatus){
        mTypeStatus =typeStatus;
        if(!mIsInit){
            requestDatas();
        }
        if(mTypeStatus == Constants.IM.TYPE_MESSAGE){
            EMClient.getInstance().chatManager().addMessageListener(messageListener);
        }
    }

//    private List<ChatRoomBean> mHeaderDatas = new ArrayList<>();
//    private void initHeadrDatas(){
//        if(mHeaderDatas.isEmpty()){
//            ChatRoomBean customerServiceUser = new ChatRoomBean();
//            customerServiceUser.setName(mApplication.getString(R.string.official_customer_service));
//            customerServiceUser.setDescribe("有问题,找客服");
//            mHeaderDatas.add(customerServiceUser);
//            ChatRoomBean myFriendsUser = new ChatRoomBean();
//            myFriendsUser.setName("我的好友");
//            myFriendsUser.setDescribe(mApplication.getString(R.string.no_more_messages));
//            mHeaderDatas.add(customerServiceUser);
//            mHeaderDatas.add(myFriendsUser);
//        }
//    }

    public void requestDatas(){
        Observable<BaseResponse<List<ChatRoomBean>>> observable;
        if(mTypeStatus == Constants.IM.TYPE_ROOM){
            observable = mChatRoomModel.roomList();
        }else{
            observable = mChatRoomModel.messageList();
        }
        if(null != observable){
            observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                        if(mIsInit){
                            mRootView.showRefresh();//显示下拉刷新的进度条
                        }
                    }).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        if(mIsInit){
                            mRootView.finishRefresh();//隐藏下拉刷新的进度条
                        }
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ChatRoomBean>>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<List<ChatRoomBean>> response) {
                            if (response.isSuccess()) {
                                mIsInit = true;
                                mDatas.clear();
                                List<ChatRoomBean> roomBeans = response.getResult();
                                if(null!=roomBeans && roomBeans.size()>0){
                                    mDatas.addAll(roomBeans);
                                }
                                mAdapter.notifyDataSetChanged();
                            }else{
                                mRootView.showMessage(response.getMessage());
                            }
                        }
                    });
        }
    }

    public void roomDetail(String roomId,String hxgroupid){
        mChatRoomModel.roomDetail(roomId,hxgroupid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ChatRoomBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ChatRoomBean> response) {
                        if (response.isSuccess()) {
                            mRootView.joinRoomSuccessfully(response.getResult());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    mRootView.setMyFriendsUnreadCount(getMyFriendsUnreadCount());
                    mAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusHub.EVENTBUS_IM_UNREAD_COUNT,EventBusHub.EVENTBUS_IM_UNREAD_COUNT);
                    break;
            }
        }
    };

    private int getMyFriendsUnreadCount(){
        int unreadCount = 0;
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if(!conversation.isGroup()){
                    unreadCount+=conversation.getUnreadMsgCount();
                }
            }
        }
        return unreadCount;
    }

    private EMMessageListener messageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            sendRefreshOrder();
        }
        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {}
        @Override
        public void onMessageRead(List<EMMessage> messages) {}
        @Override
        public void onMessageDelivered(List<EMMessage> message) {}
        @Override
        public void onMessageRecalled(List<EMMessage> messages) { }
        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    public void sendRefreshOrder(){
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null !=messageListener){
            EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        }
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
