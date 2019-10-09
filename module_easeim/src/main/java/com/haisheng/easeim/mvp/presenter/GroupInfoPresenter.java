package com.haisheng.easeim.mvp.presenter;

import android.app.Application;

import com.haisheng.easeim.mvp.contract.GroupInfoContract;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.GroupListBean;
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

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupInfoPresenter extends BasePresenter<IModel, GroupInfoContract.View> {
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
    public GroupInfoPresenter(GroupInfoContract.View rootView) {
        super(rootView);
    }

//    public void initDatas(Long roomId){
//        mChatRoomModel.roomDetail(roomId)
//                .compose(RxUtils.applySchedulers(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<ChatRoomBean>>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseResponse<ChatRoomBean> response) {
//                        if (response.isSuccess()) {
//                            ChatRoomBean chatRoomBean = response.getResult();
//                            if(null != chatRoomBean){
//                                mRootView.setBanner(cultureBean.getBanner());
//                            }
//                        }else{
//                            mRootView.showMessage(response.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        super.onError(t);
//                        mRootView.showMessage(t.getMessage());
//                    }
//                });
//    }

    public void quitRoom(Long roomId){
        mChatRoomModel.quitRoom(roomId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.quitSuccessful();
                        }
                        mRootView.showMessage(response.getMessage());
                    }
                });
    }

    public void setRoomNickName(String roomId,String nickname){
        mChatRoomModel.setRoomNickName(roomId,nickname)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.setRoomNickNameSuccess (nickname);
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }
    public void getGroupList(String roomId,int page){
        mChatRoomModel.getGroupList(roomId,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GroupListBean>(mErrorHandler) {
                    @Override
                    public void onNext(GroupListBean response) {
                        if (response.getStatus () == 1) {
                            mRootView.getGroupListSuccess (response);
                        }else{
                            mRootView.showMessage(response.getMsg ());
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
