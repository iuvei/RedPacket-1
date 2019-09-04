package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.AdNoticeContract;
import com.ooo.main.mvp.model.OtherModel;


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
public class AdNoticePresenter extends BasePresenter<IModel, AdNoticeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    OtherModel mOtherModel;

    private boolean mIsInit=false;

    @Inject
    public AdNoticePresenter(AdNoticeContract.View rootView) {
        super(rootView);
    }

    public void initDatas(){
        if(!mIsInit)
            adBannerInfo();
    }

    public void adBannerInfo(){
        mOtherModel.adBannerInfo()
                .compose(RxUtils.io_main(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AdBannerInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AdBannerInfo> response) {
                        if (response.isSuccess()) {
                            mIsInit = true;
                            mRootView.setAdBannerInfo(response.getResult());
                        }
                    }
                });
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
