package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.CallListContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


@FragmentScope
public class CallListPresenter extends BasePresenter<CallListContract.Model, CallListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<CallRecordEntity> mDatas;
    @Inject
    RecyclerView.Adapter mAdapter;

    private boolean mIsInit = false;

    private int preEndIndex;

    @Inject
    public CallListPresenter(CallListContract.Model model, CallListContract.View rootView) {
        super(model, rootView);
    }

    public void initDatas(){
        if(!mIsInit)
            requestDatas(true);
    }

    public void requestDatas(final boolean pullToRefresh) {
        Long lastTimestamp = System.currentTimeMillis();
        if(!pullToRefresh && mDatas.size()>0){
            CallRecordEntity entity = mDatas.get(mDatas.size() -1);
            lastTimestamp = entity.getTimestamp();
        }

        mModel.getCallList(lastTimestamp, 20)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if(!mIsInit){
                        if (pullToRefresh)
                            mRootView.showLoading();//显示下拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(!mIsInit){
                        if (pullToRefresh)
                            mRootView.hideLoading();//隐藏下拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CallRecordEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CallRecordEntity>> data) {
                        if(data.isSuccess()){
                            if (pullToRefresh){
                                mDatas.clear();//如果是下拉刷新则清空列表
                            }
                            preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            List<CallRecordEntity> entities = data.getResult();
                            if(null!=entities && entities.size()>0)
                                mDatas.addAll(data.getResult());
                            if (pullToRefresh)
                                mAdapter.notifyDataSetChanged();
                            else
                                mAdapter.notifyItemRangeInserted(preEndIndex, data.getResult().size());
                        }
                        mRootView.showMessage(data.getMessage());
                    }

                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mDatas = null;
        this.mAdapter = null;
    }
}
