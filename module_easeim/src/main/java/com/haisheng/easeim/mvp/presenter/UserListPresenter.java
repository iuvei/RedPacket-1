package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.UserListContract;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
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
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 15:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserListPresenter extends BasePresenter <IModel, UserListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ChatRoomModel mChatRoomModel;

    @Inject
    List<UserInfo> mDatas;
    @Inject
    UserListAdapter mAdapter;

    private Long mRoomId;

    private boolean mIsInit = false;
    private int preEndIndex = 0;
    private int pageNumber = 0;

    @Inject
    public UserListPresenter(UserListContract.View rootView) {
        super(rootView);
    }

    public void initDatas(Long roomId){
        mRoomId = roomId;
        if(!mIsInit)
            requestDatas(true);
    }
    public void requestDatas(final boolean pullToRefresh){
        mChatRoomModel.roomUserList(mRoomId,pageNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn( AndroidSchedulers.mainThread())
                .observeOn( AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
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
                            preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            pageNumber++;//更新之前列表总长度,用于确定加载更多的起始位置
                            List<UserInfo> entities = response.getResult();
                            int itemCount = 0;
                            if(null != entities && entities.size()>0){
                                mDatas.addAll(entities);
                                itemCount = entities.size();
                            }
                            if (pullToRefresh)
                                mAdapter.notifyDataSetChanged();
                            else
                                mAdapter.notifyItemRangeInserted(preEndIndex, itemCount);
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
