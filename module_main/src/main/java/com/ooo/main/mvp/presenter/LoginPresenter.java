package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.component.im.service.IMService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.contract.LoginContract;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.entity.LoginResultInfo;

import org.simple.eventbus.EventBus;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/27/2019 14:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<IModel, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MemberModel mLoginModel;

    @Autowired(name = RouterHub.IM_SERVICE_IMSERVICE)
    public IMService mIMService;

    private String mToken;
    private String id;
    private long uid;

    @Inject
    public LoginPresenter(LoginContract.View rootView) {
        super(rootView);
        ARouter.getInstance().inject(this);
    }

    public void login(String phone, String password) {
        mLoginModel.login(phone, password)
                .concatMap(response -> {
                    if(!response.isSuccess()){
                        EventBus.getDefault ().post ( "","login" );
                        throw new Exception(response.getMessage());
                    }
                    LoginResultInfo resultInfo = response.getResult();
                    mToken = resultInfo.getToken();
                    String avatarUrl = resultInfo.getAvatarUrl();
                    String nickname = resultInfo.getNickname();
                    String hxUsername = resultInfo.getHxUsername();
                    id = resultInfo.getHxUsername ();
                    uid = resultInfo.getAccount ();
                    int gender = resultInfo.getGender ();
                    AppLifecyclesImpl.getUserinfo ().setAvatarUrl ( avatarUrl );
                    AppLifecyclesImpl.getUserinfo ().setNickname ( nickname );
                    AppLifecyclesImpl.getUserinfo ().setHxUsername ( hxUsername );
                    AppLifecyclesImpl.getUserinfo ().setGender ( gender );
                    AppLifecyclesImpl.getUserinfo ().setAccount ( resultInfo.getAccount () );
                    UserPreferenceManager.getInstance().setCurrentUserHxId(hxUsername);
                    UserPreferenceManager.getInstance().setCurrentUserAvatarUrl(avatarUrl);
                    UserPreferenceManager.getInstance().setCurrentUserNick(nickname);
                    return mIMService.loginIM(resultInfo.getHxUsername(),resultInfo.getHxPassword());
                })
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                            UserPreferenceManager.getInstance().setCurrentUserToken(mToken);
                            mRootView.loginSuccessful(id, uid );
                        }
                    }
                });
    }

    public void sendSms(String phoneNumber, boolean isRegister){
        mLoginModel.sendSms(phoneNumber,isRegister)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                            mRootView.sendSmsSuccessful();
                        }else{
                            mRootView.sendSmsFail();
                        }
                    }
                });
    }

    public void forgotPassword(String phoneNumber,String verificationCode,String password){
        mLoginModel.forgotPassword(phoneNumber,verificationCode,password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                           mRootView.killMyself();
                        }
                    }
                });
    }

    public void register(String phoneNumber,String password,String verificationCode,String nickname,String invitationCode){
        mLoginModel.register(phoneNumber,password,verificationCode,nickname,invitationCode)
                .concatMap(response -> {
                    if(!response.isSuccess()){
                        throw new Exception(response.getMessage());
                    }
                    LoginResultInfo resultInfo = response.getResult();
                    mToken = resultInfo.getToken();
                    String avatarUrl = resultInfo.getAvatarUrl();
                    String hxUsername = resultInfo.getHxUsername();
                    UserPreferenceManager.getInstance().setCurrentUserHxId(hxUsername);
                    UserPreferenceManager.getInstance().setCurrentUserAvatarUrl(avatarUrl);
                    UserPreferenceManager.getInstance().setCurrentUserNick(nickname);
                    uid = resultInfo.getAccount ();
                    return mIMService.loginIM(resultInfo.getHxUsername(),resultInfo.getHxPassword());
                })
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        mRootView.showMessage(response.getMessage());
                        if (response.isSuccess()) {
                            UserPreferenceManager.getInstance().setCurrentUserToken(mToken);
                            mRootView.loginSuccessful( id,uid );
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
