package com.ooo.main.mvp.presenter;

import android.app.Application;

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
import com.ooo.main.mvp.contract.MemberInfoContract;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.ToolModel;
import com.ooo.main.mvp.model.entity.MemberInfo;

import java.io.File;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MemberInfoPresenter extends BasePresenter<IModel, MemberInfoContract.View> {
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
    ToolModel mToolModel;

    @Inject
    public MemberInfoPresenter(MemberInfoContract.View rootView) {
        super(rootView);
    }


    public void updateMemberInfo(MemberInfo memberInfo){
        mMemberModel.updateMemberInfo(memberInfo.getNickname(),memberInfo.getAvatarUrl(),memberInfo.getSex(),null,null)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if(response.isSuccess()){
                            mRootView.saveSuccessfully();
                        }
                    }
                });
    }

    public void updateAvatarUrl(String imgPath){
        File imgFile = new File(imgPath);
        if(!imgFile.exists()){
            mRootView.showMessage("文件损坏请重试~~");
            return;
        }
        mToolModel.compressImg(imgPath)
                .concatMap(response ->{
                    if(!response.isSuccess()){
                        throw new Exception(response.getMessage());
                    }
                    return mMemberModel.updateAvatarUrl(response.getResult());
                })
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.uploadImgSuccessfully(response.getResult());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
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
