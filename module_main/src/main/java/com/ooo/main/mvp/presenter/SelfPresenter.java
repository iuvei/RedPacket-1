package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.component.im.service.IMService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.contract.SelfContract;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.entity.MemberInfo;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SelfPresenter extends BasePresenter<IModel, SelfContract.View> {
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

    @Autowired(name = RouterHub.IM_SERVICE_IMSERVICE)
    public IMService mIMService;

    private boolean mIsInit = false;

    @Inject
    public SelfPresenter(SelfContract.View rootView) {
        super(rootView);
        ARouter.getInstance().inject(this);
    }

    public void initDatas(){
        if(!mIsInit)
            requestDatas();
    }

    public void requestDatas(){
        mMemberModel.getMemberInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MemberInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MemberInfo> response) {
                        if(response.isSuccess()){
                            mRootView.refreshMemberInfo(response.getResult());
                            AppLifecyclesImpl.getUserinfo ().setNickname ( response.getResult ().getNickname () );
                            AppLifecyclesImpl.getUserinfo ().setAvatarUrl ( response.getResult ().getAvatarUrl () );
                            AppLifecyclesImpl.getUserinfo ().setGender ( response.getResult ().getSex () );
                            AppLifecyclesImpl.getUserinfo ().setAccount ( response.getResult ().getId () );
                            AppLifecyclesImpl.getUserinfo ().setBalance ( response.getResult ().getBalance () );
                            AppLifecyclesImpl.getUserinfo ().setUsername ( response.getResult ().getPhoneNumber () );
                            AppLifecyclesImpl.getUserinfo ().setRealname ( response.getResult ().getRealname () );
                            AppLifecyclesImpl.getUserinfo ().setRechangemoney ( response.getResult ().getRechangemoney () );
                            AppLifecyclesImpl.getUserinfo ().setMobile ( response.getResult ().getPhoneNumber () );
                        }else {
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }

    public void logout(){
        mIMService.logoutIM()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                            mRootView.logoutSuccess();
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
