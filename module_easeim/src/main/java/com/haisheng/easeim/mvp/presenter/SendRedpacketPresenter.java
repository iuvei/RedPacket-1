package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.SendRedpacketContract;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.hyphenate.easeui.bean.RedpacketBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 18:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SendRedpacketPresenter extends BasePresenter <IModel, SendRedpacketContract.View> {
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
    public SendRedpacketPresenter(SendRedpacketContract.View rootView) {
        super(rootView);
    }

    public void sendRedpacket(Long roomId,String booms,int redpacketNumber,double money,int welfareStatus,String password){
        mRedpacketModel.sendRedpacket(roomId,booms,redpacketNumber,money,welfareStatus,password)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse <RedpacketBean>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse <RedpacketBean> response) {
                        if (response.isSuccess()) {
                            mRootView.sendSuccessfully(response.getResult());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }


    public void checkPayPasswrod(){
        mRedpacketModel.checkPayPasswrod()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <CheckPayPasswordBean> (mErrorHandler) {
                    @Override
                    public void onNext(CheckPayPasswordBean response) {
                        if (response.getStatus () == 1) {
                            mRootView.checkPayPasswordSuccessfully(response);
                        }else{
                            mRootView.checkPayPasswordFail();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
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
