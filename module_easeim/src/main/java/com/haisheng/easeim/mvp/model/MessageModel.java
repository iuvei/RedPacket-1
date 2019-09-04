package com.haisheng.easeim.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.db.CallRecordDao;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.MessageContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


@FragmentScope
public class MessageModel extends BaseModel implements MessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> clearAllConversations() {
        return  Observable.create(emitter -> {

            Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
            if(conversations.size()>0){
                for (String username : conversations.keySet()) {
                    EMClient.getInstance().chatManager().deleteConversation(username, true);
                }
            }

            BaseResponse response = new BaseResponse();
            response.setStatus(Api.REQUEST_SUCCESS_CODE);
            response.setMessage(mApplication.getString(R.string.public_load_success));
            emitter.onNext(response);
            emitter.onComplete();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


}