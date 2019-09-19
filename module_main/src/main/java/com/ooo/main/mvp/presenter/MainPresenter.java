package com.ooo.main.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonres.dialog.AppSettingsDialog;
import me.jessyan.armscomponent.commonsdk.component.im.service.IMService;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.PermissionUtil;
import com.ooo.main.mvp.contract.MainContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.AdvertisingBean;
import com.ooo.main.mvp.model.entity.AppVersionBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/28/2019 22:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends BasePresenter<IModel, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ApiModel apiModel;

    @Autowired(name = RouterHub.IM_SERVICE_IMSERVICE)
    public IMService mIMService;

    private RxPermissions mRxPermissions;
    private String[] mStrings;

    @Inject
    public MainPresenter(MainContract.View rootView) {
        super(rootView);
        mRxPermissions= new RxPermissions((FragmentActivity) mRootView);
        ARouter.getInstance().inject(this);
    }

    public void initUnreadIMMsgCountTotal(){
        int count = mIMService.getUnreadIMMsgCountTotal();
        mRootView.setUnreadIMMsgCountTotal(count);
    }

    @Subscriber(tag = EventBusHub.EVENTBUS_IM_UNREAD_COUNT)
    private void setUnreadIMMsgCountTotal(int countTotal) {
        mRootView.setUnreadIMMsgCountTotal(countTotal);
    }

    public void requestPermission(){
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                LogUtils.debugInfo("onRequestPermissionFailure:"+permissions.toString());
                if(null!=permissions && permissions.size()>0){
                    requestPermission();
                }
            }
            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                LogUtils.debugInfo("Need to go to the settings:"+permissions.toString());
                mRootView.showMessage("Need to go to the settings");
                new AppSettingsDialog.Builder((Activity) mRootView).build().show();
            }

        }, mRxPermissions, mErrorHandler,mStrings);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getAppVersion( ){
        apiModel.getAppVersion ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <AppVersionBean> (mErrorHandler) {
                    @Override
                    public void onNext(AppVersionBean bean) {
                        if (bean.getStatus ()==1) {
                            mRootView.getAppVersionSuccess(bean.getResult ());
                        }else{
                            mRootView.getAppVersionFail();
                        }
                    }
                } );
    }

    public void getAdvertising( ){
        apiModel.getAdvertising ()
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <AdvertisingBean> (mErrorHandler) {
                    @Override
                    public void onNext(AdvertisingBean bean) {
                        Log.e ( "tag","getAdvertising" );
                        if (bean.getStatus ()==1) {
                            mRootView.getAdvertisingSuccess(bean.getResult ());
                        }else{
                            mRootView.getAdvertisingFail();
                        }
                    }
                } );
    }
}
