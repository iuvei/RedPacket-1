package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.RewardFragmentContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.GameRuleBean;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 10:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class RewardFragmentPresenter extends BasePresenter<IModel, RewardFragmentContract.View> {
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
    public RewardFragmentPresenter(RewardFragmentContract.View rootView) {
        super(rootView);
    }


    public void getGameRule(  ){
        apiModel.getGameRule ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <GameRuleBean> (mErrorHandler) {
                    @Override
                    public void onNext(GameRuleBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getGameRuleSuccess ( bean.getResult () );
                        }else {
                            mRootView.getGameRuleFail ();
                        }
                    }
                } );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
