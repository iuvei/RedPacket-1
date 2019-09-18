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
import com.ooo.main.mvp.contract.WithdrawalContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.TakeMoneyBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 11:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WithdrawalPresenter extends BasePresenter <IModel, WithdrawalContract.View> {
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
    public WithdrawalPresenter(WithdrawalContract.View rootView) {
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

    public void getBlankCardList( ){
        apiModel.getBlankCardList ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <BlankCardBean> (mErrorHandler) {
                    @Override
                    public void onNext(BlankCardBean blankCardBean) {
                        if (blankCardBean.getStatus ()==1) {
                            mRootView.getBlankCardSuccess(blankCardBean.getResult ());
                        }else{
                            mRootView.getBlankCardFail();
                        }
                    }
                } );
    }


    public void takeMoney( String goldmoney,String cardid ){
        apiModel.takeMoney (goldmoney,cardid)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <TakeMoneyBean> (mErrorHandler) {
                    @Override
                    public void onNext(TakeMoneyBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.takeMoneySuccess(bean,goldmoney);
                        }else{
                            mRootView.takeMoneyCardFail();
                        }
                    }
                } );
    }
}
