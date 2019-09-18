package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.CertificationContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.CertificationBean;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CertificationPresenter extends BasePresenter <IModel, CertificationContract.View> {
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
    public CertificationPresenter( CertificationContract.View rootView) {
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

    public void setCertification(String realname,String idnumber){
        apiModel.setCertification (realname,idnumber)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <CertificationBean> (mErrorHandler) {
                    @Override
                    public void onNext(CertificationBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getCertificationSuccess(bean.getResult ());
                        }else{
                            mRootView.getCertificationFail();
                        }
                    }
                } );
    }
}
