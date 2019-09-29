package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.LuckyWheelContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.LuckyDrawListBean;
import com.ooo.main.mvp.model.entity.LuckyDrawSettingBean;
import com.ooo.main.mvp.model.entity.StartLuckyDrawBean;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 17:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LuckyWheelPresenter extends BasePresenter <IModel, LuckyWheelContract.View> {
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
    public LuckyWheelPresenter(LuckyWheelContract.View rootView) {
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

    public void getTurnTableSettingInfo( ){
        apiModel.getTurnTableSettingInfo ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <LuckyDrawSettingBean> (mErrorHandler) {
                    @Override
                    public void onNext(LuckyDrawSettingBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getLuckyDrawSettingSuccess(bean.getResult ());
                        }else{
                            mRootView.getLuckyDrawSettingFail();
                        }
                    }
                } );
    }

    public void startLuckyDraw( ){
        apiModel.startLuckyDraw ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <StartLuckyDrawBean> (mErrorHandler) {
                    @Override
                    public void onNext(StartLuckyDrawBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getLuckyDrawResultSuccess(bean.getResult ());
                        }else{
                            ToastUtils.showShort ( bean.getMsg () );
                            mRootView.getLuckyDrawResultFail();
                        }
                    }
                } );
    }
}
