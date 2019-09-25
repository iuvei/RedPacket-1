package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.RedPacketRecordContract;
import com.jess.arms.mvp.IModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/25/2019 12:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RedPacketRecordPresenter extends BasePresenter <IModel, RedPacketRecordContract.View> {
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
    public RedPacketRecordPresenter(RedPacketRecordContract.View rootView) {
        super ( rootView );
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getSendRedPacketRecord(String paytype, int page){
        mRedpacketModel.getRedPacketRecord(paytype,page)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <RedPacketRecordBean> (mErrorHandler) {
                    @Override
                    public void onNext(RedPacketRecordBean response) {
                        if (response.getStatus ()==1) {
                            if (page==1) {
                                mRootView.getSendRedPacketRecordRefreashSuccessfully ( response.getResult () );
                            }else{
                                mRootView.getSendRedPacketRecordLoadMoreSuccess ( response.getResult () );
                            }
                        }else{
                            mRootView.getSendRedPacketRecordFail();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }
    public void getGrapRedPacketRecord(String paytype, int page){
        mRedpacketModel.getGrapRedPacketRecord (paytype,page)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <RedPacketRecordBean> (mErrorHandler) {
                    @Override
                    public void onNext(RedPacketRecordBean response) {
                        if (response.getStatus ()==1) {
                            if (page==1) {
                                mRootView.getGrapRedPacketRecordRefreashSuccessfully ( response.getResult () );
                            }else{
                                mRootView.getGrapRedPacketRecordLoadMoreSuccess ( response.getResult () );
                            }
                        }else{
                            mRootView.getGrapRedPacketRecordFail();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }
}
