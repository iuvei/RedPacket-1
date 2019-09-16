package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.ChooseHeadImgContract;
import com.ooo.main.mvp.model.MemberModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChooseHeadImgPresenter extends BasePresenter <IModel, ChooseHeadImgContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MemberModel mMemberModel;

    @Inject
    public ChooseHeadImgPresenter(ChooseHeadImgContract.View rootView) {
        super ( rootView );
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void upLoadPic(String picData){
        mMemberModel.upLoadPic( picData)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.saveSuccess("");
                        }
                    }
                });
    }
}
