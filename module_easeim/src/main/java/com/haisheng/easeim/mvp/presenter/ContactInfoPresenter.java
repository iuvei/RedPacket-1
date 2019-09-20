package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.ContactInfoContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 15:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ContactInfoPresenter extends BasePresenter <IModel, ContactInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ContactModel contactModel;

    @Inject
    public ContactInfoPresenter( ContactInfoContract.View rootView) {
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

    public void delectFriend(String fuid){
        contactModel.delFriend ( fuid )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <PublicResponseBean> (mErrorHandler) {
                    @Override
                    public void onNext(PublicResponseBean contactInfo) {
                        if (contactInfo.getStatus ()==1) {
                            mRootView.delectFriendSuccess ();
                        }else{
                            mRootView.delectFriendFail();
                        }
                    }
                } );
    }

    public void setRemark(String remarkName,String fuid) {
        contactModel.setRemark ( remarkName,fuid )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <PublicResponseBean> (mErrorHandler) {
                    @Override
                    public void onNext(PublicResponseBean contactInfo) {
                        if (contactInfo.getStatus ()==1) {
                            mRootView.setRemarkSuccess (remarkName);
                        }else{
                            mRootView.setRemarkFail();
                        }
                    }
                } );
    }
}
