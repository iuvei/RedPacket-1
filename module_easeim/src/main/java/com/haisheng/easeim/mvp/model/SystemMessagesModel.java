package com.haisheng.easeim.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.db.CallRecordDao;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity_Table;
import com.haisheng.easeim.mvp.model.entity.SystemMessage;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.SystemMessagesContract;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


@ActivityScope
public class SystemMessagesModel extends BaseModel implements SystemMessagesContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SystemMessagesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<List<SystemMessage>>> getSystemMessages() {
        return  Observable.create(emitter -> {
            BaseResponse response = new BaseResponse();
            response.setStatus(Api.REQUEST_SUCCESS_CODE);
//            List<SystemMessage> systemMessages= SQLite.select().from(SystemMessage.class)
//                    .where(CallRecordEntity_Table.timestamp.lessThan(timestamp))
//                    .orderBy(CallRecordEntity_Table.timestamp,false)
//                    .limit(num)
//                    .queryList();
            List<SystemMessage> systemMessages = new ArrayList<>();
            for (int i = 6; i > 0; i--) {
                SystemMessage systemMessage = new SystemMessage();
                systemMessage.setTime(System.currentTimeMillis()-i*100_000);
                systemMessage.setMessage("messageaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+i);
                systemMessages.add(systemMessage);
            }

            for (int i = 6; i > 0; i--) {
                SystemMessage systemMessage = new SystemMessage();
                systemMessage.setTime(System.currentTimeMillis()-i*100_000);
                systemMessage.setMessage("messageaaaaaaaaaaaaanbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+i);
                systemMessages.add(systemMessage);
            }

            response.setResult(systemMessages);
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