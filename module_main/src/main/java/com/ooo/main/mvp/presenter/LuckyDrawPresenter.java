package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.LuckyDrawContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.LuckyDrawListBean;
import com.ooo.main.mvp.model.entity.PostersBean;
import com.ooo.main.mvp.model.entity.PublicBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 18:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LuckyDrawPresenter extends BasePresenter <IModel, LuckyDrawContract.View> {
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
    public LuckyDrawPresenter(LuckyDrawContract.View rootView) {
        super (rootView );
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getTurnTableInfoList( ){
        apiModel.getTurnTableInfoList ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <LuckyDrawListBean> (mErrorHandler) {
                    @Override
                    public void onNext(LuckyDrawListBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getLuckyDrawListSuccess(bean.getResult ());
                        }else{
                            mRootView.getLuckyDrawListFail();
                        }
                    }
                } );
    }
}
