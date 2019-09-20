package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.InviteContactContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.GameRuleBean;
import com.ooo.main.mvp.model.entity.PublicBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 11:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InviteContactPresenter extends BasePresenter <IModel, InviteContactContract.View> {
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
    public InviteContactPresenter(InviteContactContract.View rootView) {
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

    public void inviteContact( String jsonString ){
        apiModel.inviteContact (jsonString)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <PublicBean> (mErrorHandler) {
                    @Override
                    public void onNext(PublicBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.inviteContactSuccess();
                        }else{
                            mRootView.inviteContactFail();
                            if (bean.getMsg ()!=null) {
                                ToastUtils.showShort ( bean.getMsg () );
                            }
                        }
                    }
                } );
    }
}
