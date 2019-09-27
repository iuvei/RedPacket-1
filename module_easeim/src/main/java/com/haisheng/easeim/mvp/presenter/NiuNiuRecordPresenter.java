package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.ProfitRecordBean;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.NiuNiuRecordContract;
import com.jess.arms.mvp.IModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 19:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class NiuNiuRecordPresenter extends BasePresenter <IModel, NiuNiuRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    RedpacketModel redpacketModel;

    @Inject
    public NiuNiuRecordPresenter( NiuNiuRecordContract.View rootView) {
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

    public void getProfitRecord(String time1,String time2, int page,String paytype){
        redpacketModel.getProfitRecord (time1,time2,page,paytype)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <ProfitRecordBean> (mErrorHandler) {
                    @Override
                    public void onNext(ProfitRecordBean response) {
                        if (response.getStatus ()==1) {
                            if (page==1) {
                                mRootView.getProfitRecorddRefreashSuccessfully ( response.getResult () );
                            }else{
                                mRootView.getProfitRecordLoadMoreSuccess ( response.getResult () );
                            }
                        }else{
                            mRootView.getProfitRecordFail();
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
