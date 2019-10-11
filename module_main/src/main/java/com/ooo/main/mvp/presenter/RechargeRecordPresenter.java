package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.RechargeRecordContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.RechargeRecordBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RechargeRecordPresenter extends BasePresenter <IModel, RechargeRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ApiModel apiModel;

    @Inject
    public RechargeRecordPresenter(RechargeRecordContract.View rootView) {
        super ( rootView );
        ARouter.getInstance ().inject ( this );
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getRechargeRecord( int page){
        apiModel.getRechargeRecord (page,"线下充值")
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <RechargeRecordBean> (mErrorHandler) {
                    @Override
                    public void onNext(RechargeRecordBean bean) {
                        if (bean.getStatus ()==1) {
                            if (page==1) {
                                mRootView.getRechargeRecordRefreshSuccess ( bean.getResult () );
                            }else{
                                mRootView.getRechargeRecordLoadMoreSuccess ( bean.getResult () );
                            }
                        }else{
                            mRootView.getRechargeRecordFail();
                        }
                    }
                } );
    }
}
