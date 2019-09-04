package com.haisheng.easeim.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.contract.ChatContract;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


public class ChatRoomModel extends BaseModel {

    @Inject
    public ChatRoomModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    public Observable<BaseResponse<EMChatRoom>> joinChatRoom(String roomId) {
        return Observable.create(emitter -> {
            EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {

                @Override
                public void onSuccess(EMChatRoom value) {
                    //加入聊天室成功
                    BaseResponse response = new BaseResponse();
                    response.setStatus(Api.REQUEST_SUCCESS_CODE);
                    emitter.onNext(response);
                    emitter.onComplete();
                }

                @Override
                public void onError(final int error, String errorMsg) {
                    //加入聊天室失败
                    emitter.onError(new Throwable(errorMsg));
                    emitter.onComplete();
                }
            });
        });
    }
}