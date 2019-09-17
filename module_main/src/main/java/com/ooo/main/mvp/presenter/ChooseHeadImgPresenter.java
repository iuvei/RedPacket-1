package com.ooo.main.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.ChooseHeadImgContract;
import com.ooo.main.mvp.model.MemberModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


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

    //更新头像
    public void upLoadPic(String imgPath){
        File imgFile = new File(imgPath);
        if(!imgFile.exists()){
            mRootView.showMessage("文件损坏请重试~~");
            return;
        }
        compressImg(imgPath)
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

    public Observable <BaseResponse<File>> compressImg(String imgPath) {
        return Observable.create(new ObservableOnSubscribe <BaseResponse<File>> () {
            @Override
            public void subscribe(ObservableEmitter <BaseResponse<File>> emitter) {
                Context context = Utils.getApp();
                Luban.with(context)
                        .load(imgPath)
                        .ignoreBy(100)//低于100的图片不用压缩
                        .putGear(3)//设置压缩级别 默认是3
                        .setCompressListener(new OnCompressListener () {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                BaseResponse<File> response = new BaseResponse<>();
                                response.setStatus( Api.REQUEST_SUCCESS_CODE);
                                response.setMessage("压缩成功...");
                                response.setResult(file);
                                emitter.onNext(response);
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                                emitter.onComplete();
                            }
                        }).launch();
            }
        });
    }
}
