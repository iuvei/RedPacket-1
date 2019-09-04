package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.RoomBean;
import com.haisheng.easeim.mvp.ui.adapter.RoomListAdapter;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

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

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


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
    List<RoomBean> mDatas;
    @Inject
    RoomListAdapter mAdapter;

    private boolean mIsInit = false;
    private final static int MSG_REFRESH = 2;

    @Inject
    public RoomListPresenter(RoomListContract.View rootView) {
        super(rootView);
    }

    public void initDatas(){
        if(!mIsInit)
            requestDatas();
    }

    public void initMessageListener(){
        EMClient.getInstance().addConnectionListener(connectionListener);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    public void requestDatas(){
        mRedpacketModel.roomList()
                .subscribeOn(Schedulers.io())
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RoomBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RoomBean>> response) {
                        if (response.isSuccess()) {
                            mIsInit = true;
                            mDatas.clear();
                            List<RoomBean> roomBeans = response.getResult();
                            if(null!=roomBeans && roomBeans.size()>0){
                                mDatas.addAll(roomBeans);
                            }else{
                                mRootView.showEmptyView();
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }


    public void joinRoom(final String roomId){
        mChatRoomModel.joinChatRoom(roomId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EMChatRoom>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EMChatRoom> response) {
                        if (response.isSuccess()) {
                            mRootView.joinRoomSuccessfully(roomId);

                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
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
                    mAdapter.notifyDataSetChanged();
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

    @Subscriber(tag = EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION)
    private void updateWithTag(String msg){
        sendRefreshOrder();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null !=messageListener){
            EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        }
        if(null != connectionListener){
            EMClient.getInstance().removeConnectionListener(connectionListener);
        }

        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
