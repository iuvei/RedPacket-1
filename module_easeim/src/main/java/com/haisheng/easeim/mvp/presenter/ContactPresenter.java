package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.model.db.UserDao;
import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.haisheng.easeim.mvp.ui.adapter.ContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.ContactContract;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ContactPresenter extends BasePresenter<IModel, ContactContract.View> {
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
    ContactAdapter mAdapter;

    private boolean mIsInit = false;
    private int preEndIndex = 0;
    private int pageNumber = 0;

    @Inject
    public ContactPresenter(ContactContract.View rootView) {
        super(rootView);
    }

    public void initDatas(){
        if(!mIsInit)
            requestDatas(true);
    }

    public void requestDatas(final boolean pullToRefresh) {
        if (pullToRefresh)
            pageNumber = 1;
        mContactModel.contactList(pageNumber)
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showRefresh();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.finishRefresh();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ContactInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ContactInfo> data) {
                        if(data.isSuccess()){
                            mIsInit = true;
                            if (pullToRefresh){
                                mDatas.clear();
                            }
                            preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            pageNumber++;//更新之前列表总长度,用于确定加载更多的起始位置
                            ContactInfo contactInfo = data.getResult();

                            List<UserInfo> entities = new ArrayList<>();
                            if(pullToRefresh){
                                List<UserInfo> customerServiceUsers = contactInfo.getCustomerServiceUsers();
                                if(null!=customerServiceUsers && customerServiceUsers.size()>0){
                                    for (UserInfo customerServiceUser : customerServiceUsers)
                                        customerServiceUser.setType(UserInfo.TYPE_CUSTOMER_SERVICE);
                                    entities.addAll(customerServiceUsers);
                                }

                                List<UserInfo> inviteMyUsers = contactInfo.getInviteMyUsers();
                                if(null!=inviteMyUsers && inviteMyUsers.size()>0){
                                    for (UserInfo inviteMyUser : inviteMyUsers)
                                        inviteMyUser.setType(UserInfo.TYPE_INVITE_MY_FRIEND);
                                    entities.addAll(inviteMyUsers);
                                }
                            }

                            List<UserInfo> myInviteUsers = contactInfo.getMyInviteUsers();
                            if(null!=myInviteUsers && myInviteUsers.size()>0){
                                for (UserInfo myInviteUser : myInviteUsers)
                                    myInviteUser.setType(UserInfo.TYPE_MY_INVITE_FRIEND);
                                // sorting
                                Collections.sort(myInviteUsers, new Comparator<UserInfo>() {
                                    @Override
                                    public int compare(UserInfo lhs, UserInfo rhs) {
                                        if(lhs.getInitialLetter().equals(rhs.getInitialLetter())){
                                            return lhs.getNickname().compareTo(rhs.getNickname());
                                        }else{
                                            if("#".equals(lhs.getInitialLetter())){
                                                return 1;
                                            }else if("#".equals(rhs.getInitialLetter())){
                                                return -1;
                                            }
                                            return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                                        }
                                    }
                                });
                                entities.addAll(myInviteUsers);
                            }

                            int itemCount = 0;
                            if(null != entities && entities.size()>0){
                                UserDao.getInstance().saveContactList(entities);

                                mDatas.addAll(entities);
                                itemCount = entities.size();
                            }
                            if (pullToRefresh)
                                mAdapter.notifyDataSetChanged();
                            else
                                mAdapter.notifyItemRangeInserted(preEndIndex, itemCount);
                        }else{
                            mRootView.showMessage(data.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if(!mIsInit)
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
