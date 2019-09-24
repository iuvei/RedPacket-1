package com.haisheng.easeim.mvp.model;

import com.haisheng.easeim.mvp.model.api.service.ChatRoomService;
import com.haisheng.easeim.mvp.model.api.service.RedpacketService;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;


public class ChatRoomModel extends BaseModel {

    @Inject
    public ChatRoomModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    public Observable<BaseResponse<List<ChatRoomBean>>> roomList() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ChatRoomService.class)
                .roomList(token);
    }

    public Observable<BaseResponse<List<UserInfo>>> roomUserList(Long roomId, int pageNumber) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ChatRoomService.class)
                .roomUserList(token,roomId,pageNumber);
    }

    public Observable<BaseResponse<List<ChatRoomBean>>> messageList() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ChatRoomService.class)
                .messageList(token);
    }

    public Observable<BaseResponse<ChatRoomBean>> roomDetail(String roomId,String hxgroupid) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ChatRoomService.class)
                .roomDetail(token,roomId,hxgroupid);
    }

    public Observable<BaseResponse> quitRoom(Long roomId) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ChatRoomService.class)
                .quitRoom(token,roomId);
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