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
import com.ooo.main.mvp.contract.TurnToYinLianRechargeContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.GetRechargeInfoBean;
import com.ooo.main.mvp.model.entity.SubmitRechargeInfo;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TurnToYinLianRechargePresenter extends BasePresenter <IModel, TurnToYinLianRechargeContract.View> {
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
    public TurnToYinLianRechargePresenter(TurnToYinLianRechargeContract.View rootView) {
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

    public void getRechargeInfo(String money,String payType){
        apiModel.getRechargeInfo ( money,payType )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <GetRechargeInfoBean> (mErrorHandler) {
                    @Override
                    public void onNext(GetRechargeInfoBean bean) {
                       /* if (bean.getStatus ()==1) {
                            mRootView.getRechargeInfoSuccess(bean.getResult ().getWechat ());
                        }else{
                            mRootView.getRechargeInfoFail();
                        }*/
                    }
                } );
    }

    public void submitRechargeInfo(String payCodeID,String payMoney,String payName,String payImg){
        apiModel.submitRechargeInfo ( payCodeID,payMoney,payName,payImg )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <SubmitRechargeInfo> (mErrorHandler) {
                    @Override
                    public void onNext(SubmitRechargeInfo bean) {
                       /* if (bean.getStatus ()==1) {
                            mRootView.getRechargeInfoSuccess(bean.getResult ().getWechat ());
                        }else{
                            mRootView.getRechargeInfoFail();
                        }*/
                    }
                } );
    }
}
