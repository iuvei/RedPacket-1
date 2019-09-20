package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.CommissionListContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.CommisonListBean;
import com.ooo.main.mvp.model.entity.RankingBean;
import com.ooo.main.mvp.model.entity.UserInfoFromIdBean;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 15:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CommissionListPresenter extends BasePresenter <IModel, CommissionListContract.View> {
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
    public CommissionListPresenter( CommissionListContract.View rootView) {
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

    public void getRankingList( String page ){
        apiModel.getRankingList (page)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <RankingBean> (mErrorHandler) {
                    @Override
                    public void onNext(RankingBean bean) {
                        mRootView.getRankingListSuccess ( bean.getResult () );
                        mRootView.getRankingListFail();
                    }
                } );
    }
}
