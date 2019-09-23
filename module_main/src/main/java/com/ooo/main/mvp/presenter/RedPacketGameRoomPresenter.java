package com.ooo.main.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.RedPacketGameRoomContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.RedPacketRoomModel;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/17/2019 12:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RedPacketGameRoomPresenter extends BasePresenter <IModel, RedPacketGameRoomContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    RedPacketRoomModel redPacketRoomModel;
    @Inject
    ApiModel apiModel;

    @Inject
    public RedPacketGameRoomPresenter(RedPacketGameRoomContract.View rootView) {
        super (  rootView );
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

    public void getRoomList(String type){
        redPacketRoomModel.redPacketGame ( type )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <RedPacketGameRomeBean> (mErrorHandler) {
                    @Override
                    public void onNext(RedPacketGameRomeBean redPacketGameRomeBean) {
                        if (redPacketGameRomeBean.getStatus ()==1) {
                            mRootView.getRoomListSuccess(redPacketGameRomeBean);
                        }else{
                            mRootView.getRoomListFail();
                        }
                    }
                } );
    }

    public void roomDetail(Long roomId){
        apiModel.roomDetail(roomId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ChatRoomBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ChatRoomBean> response) {
                        if (response.isSuccess()) {
                            mRootView.joinRoomSuccessfully(response.getResult());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }
                });
    }
}
