package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerRedpacketDetailComponent;
import com.haisheng.easeim.mvp.contract.RedpacketDetailContract;
import com.haisheng.easeim.mvp.model.entity.GarbRedpacketBean;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.RedpacketDetailPresenter;
import com.haisheng.easeim.mvp.ui.adapter.GarbRepacketAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
@Route(path = RouterHub.IM_REDPACKETDETAILACTIVITY)
public class RedpacketDetailActivity extends BaseSupportActivity <RedpacketDetailPresenter> implements RedpacketDetailContract.View, OnRefreshListener {

    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.tv_money_number)
    TextView tvMoneyNumber;
    @BindView(R2.id.tv_message)
    TextView tvMessage;
    @BindView(R2.id.rv_garb_redpacket)
    RecyclerView rvGarbRedpacket;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.cdv_time)
    CountdownView cdvTime;
    @BindView(R2.id.ll_countdown)
    LinearLayout llCountdown;
    @BindView(R2.id.tv_settlement_status)
    TextView tvSettlementStatus;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    GarbRepacketAdapter mAdapter;


    private Long mRoomId, mRedpacketId;
    private int mWelfareStatus;

    public static void start(Activity context, Long roomId, Long redpacketId, int welfareStatus) {
        Intent intent = new Intent(context, RedpacketDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("roomId", roomId);
        bundle.putLong("redpacketId", redpacketId);
        bundle.putInt("welfareStatus", welfareStatus);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, IMConstants.REQUEST_CODE_SEND_REDPACKET);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRedpacketDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_redpacket_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mRoomId = bundle.getLong("roomId");
            mRedpacketId = bundle.getLong("redpacketId");
            mWelfareStatus = bundle.getInt("welfareStatus");
        }
        cdvTime.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                llCountdown.setVisibility(View.GONE);
                tvSettlementStatus.setText("本包游戏已截止");
//                mPresenter.requestDatas();
                refreshLayout.autoRefresh(2000);
            }
        });

        initRecyclerView();
        mPresenter.initDatas(mRoomId, mRedpacketId, mWelfareStatus);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        rvGarbRedpacket.addItemDecoration(new DividerItemDecoration (mContext, DividerItemDecoration.VERTICAL));
        refreshLayout.setRefreshHeader(new ClassicsHeader (mContext));
        refreshLayout.setOnRefreshListener(this);

        ArmsUtils.configRecyclerView(rvGarbRedpacket, mLayoutManager);
        rvGarbRedpacket.setAdapter(mAdapter);

        View emptyView = LayoutInflater.from(mContext).inflate( R.layout.public_empty_page, null, false);
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.bill_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.item_bill_detail) {
//            sendRedpacket();
            ARouterUtils.navigation(mContext, RouterHub.MAIN_BILLLISTACTIVITY);

        } else if (i == android.R.id.home) {
            onBackPressedSupport();
        }

        return true;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas();
    }

    @Override
    public void showLoading() {
        refreshLayout.autoRefreshAnimationOnly();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
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
    public void setRedpacketInfo(RedpacketBean redpacketInfo) {
        ImageLoader.displayHeaderImage(mContext, redpacketInfo.getAvatarUrl(), ivAvatar);
        tvNickname.setText(redpacketInfo.getNickname());
        int type = redpacketInfo.getType();
        if(type == IMConstants.MSG_TYPE_MINE_REDPACKET){
            tvMoneyNumber.setText(String.format("￥%.2f-[%s]",redpacketInfo.getMoney(),redpacketInfo.getBoomNumbers()));

        }else if(type == IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET){
            tvMoneyNumber.setText(String.format("￥%.2f-%d包-[%s]",redpacketInfo.getMoney(),redpacketInfo.getNumber(),redpacketInfo.getBoomNumbers()));

        }else if(type == IMConstants.MSG_TYPE_NIUNIU_REDPACKET || type == IMConstants.MSG_TYPE_NIUNIU_SETTLEMENT){
            long creatTimestamp = Long.valueOf(redpacketInfo.getCreatTime());
            long countDown = creatTimestamp + redpacketInfo.getCountdown() -System.currentTimeMillis()/1000;
            if(redpacketInfo.getStatus()==0 && countDown > 0){
                llCountdown.setVisibility(View.VISIBLE);
                cdvTime.start(countDown*1000);

            }else{
                cdvTime.stop();
                llCountdown.setVisibility(View.GONE);
                tvSettlementStatus.setVisibility(View.VISIBLE);
                tvSettlementStatus.setText("本包游戏已截止");
            }
            tvMoneyNumber.setText(String.format("￥%.2f-%d包",redpacketInfo.getMoney(),redpacketInfo.getNumber()));

        }else if(type == IMConstants.MSG_TYPE_WELFARE_REDPACKET){
            tvMoneyNumber.setText(String.format("￥%.2f-%d包",redpacketInfo.getMoney(),redpacketInfo.getNumber()));
        }

        int alreadyNumber = 0;
        double alreadyMoney = 0;
        List<GarbRedpacketBean> garbRedpackets = redpacketInfo.getGarbRedpackets();
        if (null != garbRedpackets && garbRedpackets.size() > 0) {
            alreadyNumber = garbRedpackets.size();
            for (GarbRedpacketBean garbRedpacket : garbRedpackets) {
                try {
                    alreadyMoney += Double.valueOf(garbRedpacket.getMoney());
                }catch (Exception e){
                    alreadyMoney += 0;
                }
            }
        }
        tvMessage.setText(String.format("已领取%d/%d个，共%.2f/%.2f元",
                alreadyNumber, redpacketInfo.getNumber(), alreadyMoney, redpacketInfo.getMoney()));
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
