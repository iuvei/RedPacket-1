package com.haisheng.easeim.component.service;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.Utils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.app.IMHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.component.im.service.IMService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;

@Route(path = RouterHub.IM_SERVICE_IMSERVICE, name = "IM 信息服务")
public class IMServiceImpl implements IMService {
    private final static String TAG = IMServiceImpl.class.getSimpleName();

    @Override
    public Observable<BaseResponse> loginIM(String username, String password) {
        return Observable.create(emitter -> {
            EMClient.getInstance().login(username, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "login: onSuccess");
                    // ** manually load all local groups and conversation
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
//                    UserModel.getInstance().setCurrUser(userEntity);

                    BaseResponse<UserInfo> myResponse = new BaseResponse<>();
                    myResponse.setStatus( Api.REQUEST_SUCCESS_CODE);
                    myResponse.setMessage(Utils.getApp().getString(R.string.public_login_success));
                    emitter.onNext(myResponse);
                    emitter.onComplete();
                }

                @Override
                public void onError(final int code, final String message) {
                    emitter.onError(new Throwable(message));
                    emitter.onComplete();
                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        });
    }

    @Override
    public Observable<BaseResponse> logoutIM() {
        return   Observable.create(emitter -> {
            IMHelper.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {
                    BaseResponse<UserInfo> myResponse = new BaseResponse<>();
                    myResponse.setStatus( Api.REQUEST_SUCCESS_CODE);
                    myResponse.setMessage(Utils.getApp().getString(R.string.public_load_success));
                    emitter.onNext(myResponse);
                    emitter.onComplete();
                }

                @Override
                public void onError(int i, String s) {
                    emitter.onError(new Throwable(Utils.getApp().getString(R.string.public_load_fail)));
                    emitter.onComplete();
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        });
    }

    @Override
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    @Override
    public int getUnreadIMMsgCountTotal() {
        int count = 0;
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        if(null!=conversations){
            synchronized (conversations) {
                for (EMConversation conversation : conversations.values()) {
                    count+= conversation.getUnreadMsgCount();
                }
            }
        }
        return  count;
//        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    @Override
    public void init(Context context) {

    }
}
