package com.ooo.main.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.RxUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.ooo.main.mvp.contract.UnderLineListContract;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.entity.UnderPayerBean;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UnderLineListPresenter extends BasePresenter <IModel, UnderLineListContract.View> {
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

    private List <String> underlines = new ArrayList <> (  );

    private int page = 1;
    private String chooseAgenId= "";

    @Inject
    public UnderLineListPresenter(UnderLineListContract.View rootView) {
        super ( rootView );
        underlines.add ( chooseAgenId );
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    String preAgenId = "";
    //返回上一级下线
    public void underLinePre(){
        page = 1;
        int size = underlines.size ();
        if (size>1){
            if (TextUtils.isEmpty ( preAgenId )){
                //将选择值为空,则移除最后一位
                preAgenId = underlines.get ( size-1 );
            }
            //选中最后一个列表中的最后一个下线
            getUnderLineList(underlines.get ( size-2 ),true);
        }
        if (underlines.size ()>1){
            mRootView.hasPre(true, underlines.size () );
        }else{
            mRootView.hasPre(false, underlines.size () );
        }
    }

    //刷新下线
    public void underLineRefresh(String agentid){
        page = 1;
        getUnderLineList(agentid,false);
    }

    //加载更多下线
    public void underLineloadMore(String agentid){
        page++;
        getUnderLineList(agentid,false);
    }

    private void getUnderLineList(String agentid,boolean isPre){
        apiModel.getUnderLineList ( "","",page,agentid )
                .compose( RxUtils.applySchedulers(mRootView))
                .subscribe ( new ErrorHandleSubscriber <UnderPayerBean> (mErrorHandler) {
                    @Override
                    public void onNext(UnderPayerBean underPayerBean) {
                        if (underPayerBean.getStatus ()==1) {
                            if (page == 1){
                                mRootView.getUnderLineRefrestSuccess(underPayerBean);
                            }else{
                                mRootView.getUnderLineLoadMoreSuccess(underPayerBean);
                            }
                            if (isPre){
                                if (!agentid.equals ( preAgenId )) {
                                    //如果当前选择的下线id不相等，则删除列表中的最后一个
                                    underlines.remove ( underlines.size ()-1 );
                                    //选择的下线id
                                    chooseAgenId = agentid;
                                    preAgenId = agentid;
                                }
                            }else {
                                if (!agentid.equals ( chooseAgenId )) {
                                    //如果当前选择的下线id不相等，则添加到列表中
                                    underlines.add ( agentid );
                                    //选择的下线id
                                    chooseAgenId = agentid;
                                    preAgenId = agentid;
                                }
                            }
                            mRootView.hasPre ( underlines.size ()>1,underlines.size () );

                        }else{
                            if (page==1){
                                mRootView.getUnderLineRefrestFail();
                            }else{
                                mRootView.getUnderLineLoadMoreFail();
                            }

                        }
                    }
                } );
    }
}
