package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.ContactContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;

import java.util.ArrayList;
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
    public ContactPresenter(ContactContract.View rootView) {
        super(rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getContactList(int pageNumber){
        mContactModel.contactList ( pageNumber )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <BaseResponse<ContactInfo>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ContactInfo> contactInfo) {
                        if (contactInfo.getStatus ()==1) {
                            //返回所有好友
                            List<UserInfo> list = new ArrayList <> (  );
                            list.addAll ( contactInfo.getResult ().getInviteMyUsers () );
                            list.addAll ( contactInfo.getResult ().getMyInviteUsers () );
                            mRootView.getContactsListSuccess (list);
                        }else{
                            mRootView.getContactsListFail();
                        }
                    }
                } );
    }
}
