package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.SearchContactContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.ui.adapter.UserListAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 11:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchContactPresenter extends BasePresenter <IModel, SearchContactContract.View> {
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

    @Inject
    public SearchContactPresenter(SearchContactContract.View rootView) {
        super(rootView);
    }

    public void requestDatas(String field){
        mContactModel.searchContact(field)
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber <BaseResponse <List<UserInfo>>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse <List<UserInfo>> response) {
                        if (response.isSuccess()) {
                            mDatas.clear();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
