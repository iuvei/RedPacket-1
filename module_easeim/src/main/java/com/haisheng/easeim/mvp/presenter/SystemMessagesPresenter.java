package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.model.entity.SystemMessage;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.SystemMessagesContract;

import java.util.List;


@ActivityScope
public class SystemMessagesPresenter extends BasePresenter<SystemMessagesContract.Model, SystemMessagesContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<SystemMessage> mDatas;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Inject
    public SystemMessagesPresenter(SystemMessagesContract.Model model, SystemMessagesContract.View rootView) {
        super(model, rootView);
    }

    public void requestDatas() {
        mModel.getSystemMessages()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SystemMessage>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<SystemMessage>> data) {
                        if(data.isSuccess()){
                            mDatas.clear();//如果是下拉刷新则清空列表
                            List<SystemMessage> systemMessages = data.getResult();
                            if(null != systemMessages && systemMessages.size()>0){
                                mDatas.addAll(systemMessages);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        mRootView.showMessage(data.getMessage());
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
