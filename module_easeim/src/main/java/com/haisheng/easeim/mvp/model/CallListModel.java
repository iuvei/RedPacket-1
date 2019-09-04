package com.haisheng.easeim.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.db.CallRecordDao;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.FragmentScope;
import javax.inject.Inject;
import com.haisheng.easeim.mvp.contract.CallListContract;
import java.util.List;
import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


@FragmentScope
public class CallListModel extends BaseModel implements CallListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CallListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<List<CallRecordEntity>>> getCallList(long timestamp, int num) {
        return  Observable.create(emitter -> {
            BaseResponse response = new BaseResponse();
            response.setStatus(Api.REQUEST_SUCCESS_CODE);
            List<CallRecordEntity> entities = CallRecordDao.getInstance().selectCallRecordEntitesOrderByTimestamp(timestamp,num);
//            List<CallRecordEntity> entities = CallRecordDao.getInstance().getCallRecordEntites();
            response.setResult(entities);
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