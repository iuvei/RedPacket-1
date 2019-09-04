package com.haisheng.easeim.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.db.CallRecordDao;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.ChatContract;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


@ActivityScope
public class ChatModel extends BaseModel implements ChatContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChatModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> fetchHistoryMessages(String toChatUsername, int chatType, int pagesize, String msgId) {
        return  Observable.create(emitter -> {
            try{
                EMClient.getInstance().chatManager().fetchHistoryMessages(
                        toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize, msgId);
                BaseResponse response = new BaseResponse();
                response.setStatus(Api.REQUEST_SUCCESS_CODE);
                emitter.onNext(response);
            }catch (HyphenateException e){
                emitter.onError(new Throwable(e.getMessage()));
            }finally {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<BaseResponse<List<EMMessage>>> loadMoreMsgFromDB(EMConversation conversation,String msgId, int pagesize) {
        return  Observable.create(emitter -> {
            try {
                BaseResponse response = new BaseResponse();
                response.setStatus(Api.REQUEST_SUCCESS_CODE);
                List<EMMessage> messages = conversation.loadMoreMsgFromDB(msgId, pagesize);
                response.setResult(messages);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(new Throwable(Utils.getApp().getString(R.string.public_load_fail)));
            }finally {
                emitter.onComplete();
            }
        });
    }
}