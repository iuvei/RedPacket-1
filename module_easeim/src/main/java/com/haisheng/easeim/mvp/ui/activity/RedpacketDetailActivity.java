package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

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
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.tv_my_getmoney)
    TextView tv_my_getmoney;
    @BindView(R2.id.ll_my_getmoney)
    LinearLayout ll_my_getmoney;
    @BindView(R2.id.ll_niuniu_result)
    LinearLayout ll_niuniu_result;
    @BindView(R2.id.tv_banker_win)
    TextView tv_banker_win;
    @BindView(R2.id.tv_buy_win)
    TextView tv_buy_win;
    @BindView(R2.id.tv_niuniu_num)
    TextView tv_niuniu_num;


    private Long mRoomId, mRedpacketId;
    private int mWelfareStatus;
    private int paytype = 0; //红包房间类型
    private RedpacketBean redpacketInfo;
    private String blankMoney;
    private Integer niuniuNum;

    public static void start(Activity context, Long roomId, Long redpacketId, int welfareStatus, int redType) {
        Intent intent = new Intent ( context, RedpacketDetailActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putLong ( "roomId", roomId );
        bundle.putLong ( "redpacketId", redpacketId );
        bundle.putInt ( "welfareStatus", welfareStatus );
        bundle.putInt ( "paytype", redType );
        intent.putExtras ( bundle );
        context.startActivityForResult ( intent, IMConstants.REQUEST_CODE_SEND_REDPACKET );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRedpacketDetailComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_redpacket_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "红包详情" );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            mRoomId = bundle.getLong ( "roomId" );
            mRedpacketId = bundle.getLong ( "redpacketId" );
            mWelfareStatus = bundle.getInt ( "welfareStatus" );
            paytype = bundle.getInt ( "paytype" );
            if (paytype == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET ||
                    paytype == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET) {
                tvRight.setText ( "账单记录" );
            } else {
                tvRight.setText ( "红包记录" );
            }
        }
        cdvTime.setOnCountdownEndListener ( new CountdownView.OnCountdownEndListener () {
            @Override
            public void onEnd(CountdownView cv) {
                llCountdown.setVisibility ( View.GONE );
                tvSettlementStatus.setText ( "本包游戏已截止" );
//                mPresenter.requestDatas();
                refreshLayout.autoRefresh ( 2000 );
            }
        } );

        initRecyclerView ();
        mPresenter.initDatas ( mRoomId, mRedpacketId, mWelfareStatus );
        ivBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                finish ();
            }
        } );
        tvRight.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (paytype == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET ||
                        paytype == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET) {
                    //盈亏记录
                    openActivity ( NiuNiuRecordActivity.class );
                } else {
                    //红包记录
                    RedPacketRecordActivity.start ( RedpacketDetailActivity.this, paytype, redpacketInfo );
                }
            }
        } );
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        rvGarbRedpacket.addItemDecoration ( new DividerItemDecoration ( mContext, DividerItemDecoration.VERTICAL ) );
        refreshLayout.setRefreshHeader ( new ClassicsHeader ( mContext ) );
        refreshLayout.setOnRefreshListener ( this );

        ArmsUtils.configRecyclerView ( rvGarbRedpacket, mLayoutManager );
        rvGarbRedpacket.setAdapter ( mAdapter );
        //用户id
        String uid = SpUtils.getValue ( this, "uid", "" );
        mAdapter.setUid ( uid );
        View emptyView = LayoutInflater.from ( mContext ).inflate ( R.layout.public_empty_page, null, false );
        mAdapter.setEmptyView ( emptyView );
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDatas ();
    }

    @Override
    public void showLoading() {
        refreshLayout.autoRefreshAnimationOnly ();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh ();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {
        finish ();
    }


    @Override
    public void setRedpacketInfo(RedpacketBean redpacketInfo) {
        this.redpacketInfo = redpacketInfo;
        ImageLoader.displayHeaderImage ( mContext, redpacketInfo.getAvatarUrl (), ivAvatar );
        tvRight.setVisibility ( View.VISIBLE );
        tvNickname.setText ( redpacketInfo.getNickname () );
        ll_niuniu_result.setVisibility ( View.GONE );
        int alreadyNumber = 0;
        double alreadyMoney = 0;
        //用户id
        String uid = SpUtils.getValue ( this, "uid", "" );
        List <GarbRedpacketBean> garbRedpackets = redpacketInfo.getGarbRedpackets ();
        if (null != garbRedpackets && garbRedpackets.size () > 0) {
            alreadyNumber = garbRedpackets.size ();
            for (GarbRedpacketBean garbRedpacket : garbRedpackets) {
                try {
                    alreadyMoney += Double.valueOf ( garbRedpacket.getMoney () );
                } catch (Exception e) {
                    alreadyMoney += 0;
                }
                if (paytype == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET || paytype == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET){
                    //牛牛房
                    if (uid.equals ( garbRedpacket.getId () )){
                        blankMoney = garbRedpacket.getMoney ();
                        ll_my_getmoney.setVisibility ( View.VISIBLE );
                        niuniuNum = garbRedpacket.getNiuniuNumber ();
                        if (garbRedpacket.getBankerStatus ()!=0) {
                            //牛牛庄家
                            tv_my_getmoney.setText ( blankMoney.substring ( 0,blankMoney.length ()-1 )+"*");
                        }else{
                            tv_my_getmoney.setText ( garbRedpacket.getMoney () );
                        }
                    }
                    if (garbRedpacket.getBankerStatus () != 0) {
                        //牛牛庄家id
                        mAdapter.setBlankId ( garbRedpacket.getId () );
                    }
                }else{
                    //不是牛牛房 显示自己的金额
                    if (uid.equals ( garbRedpacket.getId () )){
                        ll_my_getmoney.setVisibility ( View.VISIBLE );
                        blankMoney = garbRedpacket.getMoney ();
                        tv_my_getmoney.setText ( garbRedpacket.getMoney () );
                    }
                }
            }
        }
        tvMessage.setText ( String.format ( "已领取%d/%d个，共%.2f/%.2f元",
                alreadyNumber, redpacketInfo.getNumber (), alreadyMoney, redpacketInfo.getMoney () ) );
        if (!TextUtils.isEmpty ( redpacketInfo.getGetedtime () )) {
            //已经抢完
            tvMessage.append ( "，" + redpacketInfo.getGetedtime () + "被抢光" );
            tvMessage.setText ( String.format ( "已领取%d/%d个，共%.2f/%.2f元",
                    alreadyNumber, redpacketInfo.getNumber (), alreadyMoney, redpacketInfo.getMoney () ) );
            mAdapter.setGetAll ( true );
        } else {
            //红包还没抢完
            if (redpacketInfo.isOverTime ()) {
                //已经过期
                tvMessage.setText ( String.format ( "已领取%d/%d个，共%.2f/%.2f元",
                        alreadyNumber, redpacketInfo.getNumber (), alreadyMoney, redpacketInfo.getMoney () ) );
                mAdapter.setGetAll ( true );
            } else {
                //未过期
                tvMessage.setText ( String.format ( "已领取%d/%d个，共****/%.2f元",
                        alreadyNumber, redpacketInfo.getNumber (), redpacketInfo.getMoney () ) );
                mAdapter.setGetAll ( false );
            }
        }

        if (paytype == IMConstants.ROOM_TYPE_MINE_REDPACKET) {
            //扫雷房
            tvMoneyNumber.setText ( String.format ( "%.2f-%s", redpacketInfo.getMoney (), redpacketInfo.getBoomNumbers () ) );

        } else if (paytype == IMConstants.ROOM_TYPE_NIUNIU_REDPACKET || paytype == IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET) {
            //牛牛房
            long creatTimestamp = Long.valueOf ( redpacketInfo.getCreatTime () );
            long countDown = creatTimestamp + redpacketInfo.getCountdown () - System.currentTimeMillis () / 1000;
            if (null != niuniuNum) {
                int iconRedId = R.drawable.ic_cow_0;
                switch (niuniuNum) {
                    case 1:
                        iconRedId = R.drawable.ic_cow_1;
                        break;
                    case 2:
                        iconRedId = R.drawable.ic_cow_2;
                        break;
                    case 3:
                        iconRedId = R.drawable.ic_cow_3;
                        break;
                    case 4:
                        iconRedId = R.drawable.ic_cow_4;
                        break;
                    case 5:
                        iconRedId = R.drawable.ic_cow_5;
                        break;
                    case 6:
                        iconRedId = R.drawable.ic_cow_6;
                        break;
                    case 7:
                        iconRedId = R.drawable.ic_cow_7;
                        break;
                    case 8:
                        iconRedId = R.drawable.ic_cow_8;
                        break;
                    case 9:
                        iconRedId = R.drawable.ic_cow_9;
                        break;
                    case 0:
                        iconRedId = R.drawable.ic_cow_10;
                        break;
                }

                Drawable drawable = mContext.getResources ().getDrawable ( iconRedId );
                drawable.setBounds ( 0, 0, drawable.getMinimumWidth (), drawable.getMinimumHeight () );
                tv_niuniu_num.setText ( "" );
                tv_niuniu_num.setCompoundDrawables ( null, null, drawable, null );
            }
            if (redpacketInfo.getStatus () == 0 && countDown > 0) {
                llCountdown.setVisibility ( View.VISIBLE );
                cdvTime.start ( countDown * 1000 );

            } else {
                cdvTime.stop ();
                llCountdown.setVisibility ( View.GONE );
                tvSettlementStatus.setVisibility ( View.VISIBLE );
                tvSettlementStatus.setText ( "本包游戏已截止" );
                tvMessage.setText ( String.format ( "已领取%d/%d个，共%.2f/%.2f元",
                        alreadyNumber, redpacketInfo.getNumber (), alreadyMoney, redpacketInfo.getMoney () ) );
                tv_my_getmoney.setText ( blankMoney );
                ll_my_getmoney.setVisibility ( View.VISIBLE );
                tv_niuniu_num.setText ( "" );
                mAdapter.setGetAll ( true );
            }
            ll_niuniu_result.setVisibility ( View.VISIBLE );
            tv_banker_win.setText ( redpacketInfo.getVillagenums () + "" );
            tv_buy_win.setText ( redpacketInfo.getNotbuynums () + "" );
            tvMoneyNumber.setText ( String.format ( "%.2f-%d包", redpacketInfo.getMoney (), redpacketInfo.getNumber () ) );
            mAdapter.setNiuNiu ( true );

        } else if (paytype == IMConstants.ROOM_TYPE_WELFARE_REDPACKET || paytype == IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET) {
            //福利房 禁抢房
            tvMoneyNumber.setText ( String.format ( "%.2f-%d包", redpacketInfo.getMoney (), redpacketInfo.getNumber () ) );
            tvMessage.setText ( String.format ( "已领取%d/%d个，共%.2f/%.2f元",
                    alreadyNumber, redpacketInfo.getNumber (), alreadyMoney, redpacketInfo.getMoney () ) );
            mAdapter.setGetAll ( true );
        }

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }
}
