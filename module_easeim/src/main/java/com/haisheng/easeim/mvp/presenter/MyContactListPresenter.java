package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
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

import com.haisheng.easeim.mvp.contract.MyContactListContract;
import com.jess.arms.mvp.IModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MyContactListPresenter extends BasePresenter <IModel, MyContactListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ContactModel contactModel;

    @Inject
    public MyContactListPresenter( MyContactListContract.View rootView) {
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

    public void getContactList(int pageNumber){
        contactModel.contactList ( pageNumber )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <BaseResponse <ContactInfo>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ContactInfo> contactInfo) {
                        if (contactInfo.getStatus ()==1) {
                            //返回所有好友
                            List <UserInfo> list = new ArrayList <> (  );
                            list.addAll ( contactInfo.getResult ().getInviteMyUsers () );
                            list.addAll ( contactInfo.getResult ().getMyInviteUsers () );
                            mRootView.getContactsListSuccess (list);
                        }else{
                            mRootView.getContactsListFail();
                        }
                    }
                } );
    }

    //邀请加入聊天室
    public void invitedJoinRoom(String roomId,String friendId){
        contactModel.invitedJoinRoom ( roomId,friendId )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <PublicResponseBean> (mErrorHandler) {
                    @Override
                    public void onNext(PublicResponseBean contactInfo) {
                        if (contactInfo.getStatus ()==1) {
                            //邀请成功
                            mRootView.invitedSuccess ();
                        }else{
                            mRootView.invitedFail();
                        }
                    }
                } );
    }
}
