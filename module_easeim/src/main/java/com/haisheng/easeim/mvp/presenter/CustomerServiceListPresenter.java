package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.CustomerServiceListContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.ui.adapter.UserListAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CustomerServiceListPresenter extends BasePresenter <IModel, CustomerServiceListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ContactModel mContactModel;

    @Inject
    List<UserInfo> mDatas;
    @Inject
    UserListAdapter mAdapter;

    private boolean mIsInit = false;

    @Inject
    public CustomerServiceListPresenter(CustomerServiceListContract.View rootView) {
        super(rootView);
    }

    public void initDatas(){
        if(!mIsInit)
            requestDatas(true);
    }
    public void requestDatas(final boolean pullToRefresh){
        mContactModel.serverList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (mIsInit)
                        mRootView.showRefresh();//显示下拉刷新的进度条
                }).subscribeOn( AndroidSchedulers.mainThread())
                .observeOn( AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.finishRefresh();//隐藏下拉刷新的进度条
                })
                .compose( RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber <BaseResponse <List<UserInfo>>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse <List<UserInfo>> response) {
                        if (response.isSuccess()) {
                            mIsInit = true;
                            if (pullToRefresh){
                                mDatas.clear();
                            }
                            List<UserInfo> entities = response.getResult();
                            if(null != entities && entities.size()>0){
                                mDatas.addAll(entities);
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }

    public void onlineServerUrl(){
        mContactModel.serverUrl()
                .compose( RxUtils.applySchedulers(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber <BaseResponse <String>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse <String> response) {
                        if (response.isSuccess()) {
                            mRootView.loadServerUrl(response.getResult());
                        }else{
                            mRootView.showMessage(response.getMessage());
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
