package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerBillRecordComponent;
import com.ooo.main.mvp.contract.BillRecordContract;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.BillRecordInfo;
import com.ooo.main.mvp.presenter.BillRecordPresenter;
import com.ooo.main.mvp.ui.adapter.BillRecordAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.popupwindow.SelectItemPopupWindow;
import me.jessyan.armscomponent.commonres.view.recyclerview.SpaceItemDecoration;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class BillRecordActivity extends BaseSupportActivity<BillRecordPresenter> implements BillRecordContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private LinearLayout llSelectType;
    private TextView tvTotalMoney,tvSelectType,tvStartTime,tvEndTime;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    BillRecordAdapter mAdapter;

//    public static final String

    private int mTitleResId;
    private int mTotalTextResId;
    private String mType;
    private String mStartTime,mEndTime;

    private List<String> mTypes;

    private SimpleDateFormat mDateFormat= new SimpleDateFormat("yyyy-MM-dd");

    public static void start(Activity context,int titleResId) {
        Intent intent = new Intent(context, BillRecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("titleResId",titleResId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBillRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bill_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mTitleResId = bundle.getInt("titleResId");
        }
        setTitle(mTitleResId);
        if (mTitleResId == R.string.reward_record) {
            mTotalTextResId = R.string.total_reward_money;

        } else if (mTitleResId == R.string.recharge_record) {
            mTotalTextResId = R.string.total_recharge_money;

        } else if (mTitleResId == R.string.withdraw_record) {
            mTotalTextResId = R.string.total_withdraw_money;

        } else if (mTitleResId == R.string.send_redpacket_record) {
            mTotalTextResId = R.string.total_send_redpacket_money;

        } else if (mTitleResId == R.string.grab_redpacket_record) {
            mTotalTextResId = R.string.total_grab_redpacket_money;

        } else if (mTitleResId == R.string.profit_loss_record) {
            mTotalTextResId = R.string.total_profit_loss_money;

        } else if (mTitleResId == R.string.commission_income_record) {
            mTotalTextResId = R.string.total_commission_income_money;

        } else if (mTitleResId == R.string.fruit_machine_record) {
            mTotalTextResId = R.string.total_profit_loss_money;
        }else{
            mTotalTextResId = R.string.balance_money;
        }

        initRefreshView();
        initRecyclerView();
        long currentTime = System.currentTimeMillis();
        mStartTime = mDateFormat.format(currentTime);
        mEndTime = mDateFormat.format(currentTime);

        tvStartTime.setText(String.format(getString(R.string.start_time),mStartTime));
        tvEndTime.setText(String.format(getString(R.string.start_time),mEndTime));

        mPresenter.initDatas(mTitleResId,mType,mStartTime,mEndTime);
    }

    private void initRefreshView(){
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setOnRefreshListener(this);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        int space = ConvertUtils.dp2px(getResources().getDimension(R.dimen.spacing_xs));
        recyclerView.addItemDecoration(new SpaceItemDecoration(space, SpaceItemDecoration.VERTICAL));
        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(getHeadView());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BillBean billBean = (BillBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putLong("roomId",billBean.getRoomId());
                bundle.putLong("redpacketId",billBean.getRedPacketId());
                ARouterUtils.navigation(mContext,RouterHub.IM_REDPACKETDETAILACTIVITY,bundle);
            }
        });
    }

    private View getHeadView() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.view_bill_header,null,false);
        llSelectType = headView.findViewById(R.id.ll_select_type);
        tvTotalMoney = headView.findViewById(R.id.tv_total_money);
        tvSelectType = headView.findViewById(R.id.tv_select_type);
        if(mTitleResId == R.string.all_record){
            llSelectType.setVisibility(View.GONE);
        }
        tvStartTime = headView.findViewById(R.id.tv_start_time);
        tvEndTime = headView.findViewById(R.id.tv_end_time);

        tvSelectType.setOnClickListener(mOnClickListener);
        tvStartTime.setOnClickListener(mOnClickListener);
        tvEndTime.setOnClickListener(mOnClickListener);
        return headView;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.tv_select_type){
                showSelectTypePopupWindow(v);
            }else if(id == R.id.tv_start_time){
                showStartTimePickerView();
            }else if(id == R.id.tv_end_time){
                showEndTimePickerView();
            }
        }
    };

    private SelectItemPopupWindow mSelectTypePopupWindow;
    private void showSelectTypePopupWindow(View v){
        if(null!=mTypes ){
            if(null == mSelectTypePopupWindow){
                mSelectTypePopupWindow = new SelectItemPopupWindow<String>(mContext, "请选择账单类型", mTypes, new SelectItemPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mType = (String) adapter.getItem(position);
                        tvSelectType.setText(mType);
                        refreshLayout.autoRefresh();
                    }
                }) {
                    @Override
                    public void setItemInfo(BaseViewHolder helper, String item) {
                        helper.setText(R.id.tv_content,item);
                    }
                };
            }
            mSelectTypePopupWindow.openPopWindow(v);
        }else{
            showMessage("网络错误！");
        }
    }

    private TimePickerView mStartTimePickerView;
    private void showStartTimePickerView(){
        if(null == mStartTimePickerView){
            mStartTimePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mStartTime = mDateFormat.format(date);
                    tvStartTime.setText(String.format(getString(R.string.start_time),mStartTime));
                    refreshLayout.autoRefresh();
                }
            }).setTitleText("请选择开始时间").build();
        }
        mStartTimePickerView.show();
    }

    private TimePickerView mEndTimePickerView;
    private void showEndTimePickerView(){
        if(null == mEndTimePickerView){
            mEndTimePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mEndTime = mDateFormat.format(date);
                    tvEndTime.setText(String.format(getString(R.string.start_time),mEndTime));
                    refreshLayout.autoRefresh();
                }
            }).setTitleText("请选择结束时间").build();
        }
        mEndTimePickerView.show();
    }


    @Override
    public void showLoading() { }

    @Override
    public void hideLoading() { }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas(true,mType,mStartTime,mEndTime);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas(false,mType,mStartTime,mEndTime);
    }

    @Override
    public void setBillRecordInfo(BillRecordInfo billRecordInfo) {
        mTypes = billRecordInfo.getTypes();
        tvTotalMoney.setText(String.format(getString(mTotalTextResId),billRecordInfo.getTotalMoney()));
    }

    @Override
    public void showRefresh() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void startLoadMore() {
        refreshLayout.autoLoadMore();
    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadMore();
    }


    @Override
    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public Activity getActivity() {
        return this;
    }

}
