package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.UserInfoContract;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.ooo.main.mvp.model.entity.MemberInfo;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 10:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserInfoPresenter extends BasePresenter <IModel, UserInfoContract.View> {
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
    public UserInfoPresenter(UserInfoContract.View rootView) {
        super (  rootView );
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

    public void updateMemberInfo(LoginResultInfo userInfo){
        //更新信息 获取昵称头像路劲，从images开始截取后面的路劲保存到数据库里
        String urlPath = userInfo.getAvatarUrl ();
        int firstPos = urlPath.indexOf ( "images" );
        String realUrl = urlPath.substring ( firstPos );
        userInfo.setAvatarUrl ( realUrl );
        mMemberModel.updateMemberInfo(userInfo.getNickname(),userInfo.getAvatarUrl(),userInfo.getGender (),null,null)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.saveSuccess();
                        }
                    }
                });
    }
}
