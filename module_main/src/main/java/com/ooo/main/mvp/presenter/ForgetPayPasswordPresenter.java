package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.ForgetPayPasswordContract;
import com.ooo.main.mvp.model.MemberModel;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ForgetPayPasswordPresenter extends BasePresenter <IModel, ForgetPayPasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MemberModel mLoginModel;


    @Inject
    public ForgetPayPasswordPresenter( ForgetPayPasswordContract.View rootView) {
        super (rootView );
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

    public void sendSms(String phoneNumber, boolean isRegister){
        mLoginModel.sendSms(phoneNumber,isRegister)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                            mRootView.sendSmsSuccessful();
                        }else{
                            mRootView.sendSmsFail();
                        }
                    }
                });
    }
}
