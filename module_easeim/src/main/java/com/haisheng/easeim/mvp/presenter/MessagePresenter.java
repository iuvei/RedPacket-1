package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.MessageContract;

import org.simple.eventbus.EventBus;

import java.util.Map;


@FragmentScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    public void markAllConversationsAsRead(){
        //所有未读消息数清零
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
        EventBus.getDefault().post(EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION,EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION);
    }

    public void clearAllConversations(){
        mModel.clearAllConversations()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            EventBus.getDefault().post(EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION,EventBusHub.EVENTBUS_IM_REFRESH_CONVERSATION);
                        }
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
