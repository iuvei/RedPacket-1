package com.haisheng.easeim.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.haisheng.easeim.mvp.contract.RedpacketDetailContract;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.easeui.bean.GarbRedpacketBean;
import com.hyphenate.easeui.bean.RedpacketBean;
import com.haisheng.easeim.mvp.ui.adapter.GarbRepacketAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 21:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RedpacketDetailPresenter extends BasePresenter <IModel, RedpacketDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    RedpacketModel mRedpacketModel;
    @Inject
    List<GarbRedpacketBean> mDatas;
    @Inject
    GarbRepacketAdapter mAdapter;

    private boolean mIsInit = false;
    private Long mRoomId,mRedpacketId;
    private int mRedpacketType;

    @Inject
    public RedpacketDetailPresenter(RedpacketDetailContract.View rootView) {
        super(rootView);
    }

    public void initDatas(Long roomId,Long redpacketId,int redpacketType,boolean sort){
        mRoomId = roomId;
        mRedpacketId = redpacketId;
        mRedpacketType = redpacketType;
        if(!mIsInit) {
            requestDatas ( sort );
        }
    }

    public void requestDatas(boolean sort) {
        mRedpacketModel.redpacketDetail(mRoomId,mRedpacketId,mRedpacketType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if(mIsInit){
                        mRootView.showLoading();
                    }
                }).subscribeOn( AndroidSchedulers.mainThread())
                .observeOn( AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mIsInit){
                        mRootView.hideLoading();
                    }
                })
                .compose( RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber <BaseResponse <RedpacketBean>> (mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse <RedpacketBean> data) {
                        if(data.isSuccess()){
                            mIsInit = true;
                            RedpacketBean redpacketInfo = data.getResult();
                            mRootView.setRedpacketInfo(redpacketInfo);
                            mDatas.clear();//如果是下拉刷新则清空列表
                            List<GarbRedpacketBean> entities = redpacketInfo.getGarbRedpackets();
                            if(null != entities && entities.size() >0){
                                if (sort){
                                    sort(entities);
                                }
                                mDatas.addAll(entities);
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            mRootView.showMessage(data.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }

    //定时请求数据，是否已经抢完包
    public void timerRequestDatas(boolean sort) {
        mRedpacketModel.redpacketDetail(mRoomId,mRedpacketId,mRedpacketType)
                .compose( RxUtils.notAapplySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RedpacketBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RedpacketBean> data) {
                        if(data.isSuccess()){
                            mIsInit = true;
                            RedpacketBean redpacketInfo = data.getResult();
                            //如果已经抢完则刷新页面
                            mRootView.setRedpacketInfo(redpacketInfo);
                            mDatas.clear();//如果是下拉刷新则清空列表
                            List<GarbRedpacketBean> entities = redpacketInfo.getGarbRedpackets();
                            if(null != entities && entities.size() >0){
                                if (sort){
                                    sort(entities);
                                }
                                mDatas.addAll(entities);
                            }
                            mAdapter.notifyDataSetChanged();

                        }else{
                            mRootView.showMessage(data.getMessage());
                        }
                    }
                });
    }

    public void sort(List<GarbRedpacketBean> entities){
        for (int i=0;i<entities.size ();i++){
            entities.get ( i ).setNickname ( "盛煌"+(i+1)+"号" );
        }
        Collections.sort ( entities, new Comparator <GarbRedpacketBean> () {
            @Override
            public int compare(GarbRedpacketBean o1, GarbRedpacketBean o2) {
                String nickname1 = o1.getNickname ();
                String nickname2 = o2.getNickname ();
                int sort1 = ConvertNumUtils.stringToInt ( nickname1.substring ( 2,3 ) );
                int sort2 = ConvertNumUtils.stringToInt ( nickname2.substring ( 2,3 ) );
                return -sort1+sort2;
            }
        } );
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
