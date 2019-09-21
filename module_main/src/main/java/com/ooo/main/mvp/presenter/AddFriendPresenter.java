package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.contract.AddFriendContract;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.entity.MemberInfo;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 11:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddFriendPresenter extends BasePresenter <IModel, AddFriendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    MemberModel memberModel;
    @Inject
    public AddFriendPresenter(AddFriendContract.View rootView) {
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

    public void requestDatas(){
        memberModel.getMemberInfo()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse <MemberInfo>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MemberInfo> response) {
                        if(response.isSuccess()){
                            AppLifecyclesImpl.getUserinfo ().setNickname ( response.getResult ().getNickname () );
                            AppLifecyclesImpl.getUserinfo ().setAvatarUrl ( response.getResult ().getAvatarUrl () );
                            AppLifecyclesImpl.getUserinfo ().setGender ( response.getResult ().getSex () );
                            AppLifecyclesImpl.getUserinfo ().setAccount ( response.getResult ().getId () );
                            AppLifecyclesImpl.getUserinfo ().setBalance ( response.getResult ().getBalance () );
                            AppLifecyclesImpl.getUserinfo ().setUsername ( response.getResult ().getPhoneNumber () );
                            AppLifecyclesImpl.getUserinfo ().setRealname ( response.getResult ().getRealname () );
                            AppLifecyclesImpl.getUserinfo ().setRechangemoney ( response.getResult ().getRechangemoney () );
                            AppLifecyclesImpl.getUserinfo ().setMobile ( response.getResult ().getPhoneNumber () );
                            mRootView.refreshMemberInfo(response.getResult());
                        }else {
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }
}
