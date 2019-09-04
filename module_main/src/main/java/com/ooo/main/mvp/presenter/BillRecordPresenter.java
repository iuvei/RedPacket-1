package com.ooo.main.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;
import com.ooo.main.R;
import com.ooo.main.mvp.contract.BillRecordContract;
import com.ooo.main.mvp.model.BillModel;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.BillRecordInfo;
import com.ooo.main.mvp.ui.adapter.BillRecordAdapter;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 16:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BillRecordPresenter extends BasePresenter<IModel, BillRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BillModel mBillModel;

    @Inject
    List<BillBean> mDatas;
    @Inject
    BillRecordAdapter mAdapter;

    private int mTag;
    private boolean mIsInit = false;
    private int preEndIndex = 0;
    private int pageNumber = 0;
    private String mType;
    private String mStartTime;
    private String mEndTime;

    @Inject
    public BillRecordPresenter(BillRecordContract.View rootView) {
        super(rootView);
    }

    public void initDatas(int tag, String type, String startTime, String endTime){
        mTag = tag;

        if(!mIsInit)
            requestDatas(true,type,startTime,endTime);
    }

    public void requestDatas(final boolean pullToRefresh,String type,String startTime,String endTime) {
        if (pullToRefresh)
            pageNumber = 1;
        mType = type;
        mStartTime = startTime;
        mEndTime = endTime;

        Observable<BaseResponse<BillRecordInfo>> observable = null;
        if (mTag == R.string.all_record) {
            observable =  mBillModel.allRecord(mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.reward_record) {
            observable =  mBillModel.rewardRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.recharge_record) {
            observable =  mBillModel.rechargeRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.withdraw_record) {
            observable =  mBillModel.withdrawRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.send_redpacket_record) {
            observable =  mBillModel.sendRedpacketRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.grab_redpacket_record) {
            observable =  mBillModel.grabRedpacketRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.profit_loss_record) {
            observable =  mBillModel.profitLossRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.commission_income_record) {
            observable =  mBillModel.commissionIncomeRecord(mType,mStartTime,mEndTime,pageNumber);

        } else if (mTag == R.string.fruit_machine_record) {
            observable =  mBillModel.fruitMachineRecord(mType,mStartTime,mEndTime,pageNumber);
        }
        if(null != observable){
            observable.subscribeOn(Schedulers.io())
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
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<BillRecordInfo>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<BillRecordInfo> data) {
                            if(data.isSuccess()){
                                mIsInit = true;
                                if (pullToRefresh){
                                    mDatas.clear();
                                }
                                preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                                pageNumber++;//更新之前列表总长度,用于确定加载更多的起始位置
                                BillRecordInfo billRecordInfo = data.getResult();
                                mRootView.setBillRecordInfo(billRecordInfo);

                                List<BillBean> entities = billRecordInfo.getBillBeans();
                                int itemCount = 0;
                                if(null != entities && entities.size()>0){
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
    }


    public void allRecord(final boolean pullToRefresh){
        mBillModel.allRecord(mStartTime,mEndTime,pageNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if(mIsInit){
                        if (pullToRefresh)
                            mRootView.showRefresh();//显示下拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mIsInit){
                        if (pullToRefresh)
                            mRootView.finishRefresh();//隐藏下拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BillRecordInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BillRecordInfo> data) {
                        if(data.isSuccess()){
                            mIsInit = true;
                            if (pullToRefresh){
                                mDatas.clear();
                            }
                            preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            pageNumber++;//更新之前列表总长度,用于确定加载更多的起始位置
                            BillRecordInfo billRecordInfo = data.getResult();
                            List<BillBean> entities = billRecordInfo.getBillBeans();
                            int itemCount = 0;
                            if(null != entities && entities.size()>0){
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
