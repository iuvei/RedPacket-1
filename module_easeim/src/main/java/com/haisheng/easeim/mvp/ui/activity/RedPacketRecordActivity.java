package com.haisheng.easeim.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerRedPacketRecordComponent;
import com.haisheng.easeim.mvp.contract.RedPacketRecordContract;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.RedPacketRecordPresenter;
import com.haisheng.easeim.mvp.ui.adapter.SendRedPacketRecordAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/25/2019 12:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RedPacketRecordActivity extends BaseActivity <RedPacketRecordPresenter> implements RedPacketRecordContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.tv_redpacket_num)
    TextView tvRedpacketNum;
    @BindView(R2.id.tv_redpacket)
    TextView tvRedpacket;
    @BindView(R2.id.tv_redpacketTotal)
    TextView tvRedpacketTotal;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int payType;
    private String payTypeString;
    private int page = 1;
    private List <RedPacketRecordBean.ResultBean.ListBean> recordBeans;
    private SendRedPacketRecordAdapter recycleAdapter;
    private RedpacketBean redpacketBean;
    private int type = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRedPacketRecordComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_red_packet_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "收到的红包" );
        ivRight.setVisibility ( View.VISIBLE );
        ivRight.setImageResource ( R.drawable.ic_talk_add );
        recordBeans = new ArrayList <> (  );
        redpacketBean = (RedpacketBean) getIntent ().getSerializableExtra ( "redpacketInfo" );
        getBillingRecord(page);
        tvNickname.setText ( redpacketBean.getNickname ()+"共收到" );
        Glide.with ( this ).load ( redpacketBean.getAvatarUrl () ).into ( ivAvatar );
        recycleAdapter = new SendRedPacketRecordAdapter ( this, recordBeans,redpacketBean.getNickname ());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this,LinearLayoutManager.VERTICAL,false );
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置为垂直布局，这也是默认的
        linearLayoutManager.setOrientation( OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration( new DividerGridItemDecoration (this ));
        setListener();
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener () {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getBillingRecord (page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener () {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                getBillingRecord (page);
            }
        });
        recycleAdapter.setItemClickListener ( new SendRedPacketRecordAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <RedPacketRecordBean.ResultBean.ListBean> data, int position) {
                RedPacketRecordBean.ResultBean.ListBean bean = data.get ( position );
                RedpacketDetailActivity.start(RedPacketRecordActivity.this, ConvertNumUtils.stringToLong ( bean.getRoomid () ),
                        ConvertNumUtils.stringToLong ( bean.getSetid () ), redpacketBean.getWelfareStatus (),payType);
            }
        } );
    }

    private void getBillingRecord(int page) {
        payType = getIntent ().getIntExtra ( "paytype",0 );
        if (type == 1){
           //收到的包
            switch (payType){
                case IMConstants.ROOM_TYPE_MINE_REDPACKET:
                    payTypeString="扫雷抢包";
                    break;
                case IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET:
                    payTypeString="无法抢包";
                    break;
                case IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET:
                case IMConstants.ROOM_TYPE_NIUNIU_REDPACKET:
                    payTypeString="牛牛抢包";
                    break;
                case IMConstants.ROOM_TYPE_WELFARE_REDPACKET:
                    payTypeString="福利抢包";
                    break;
            }
            mPresenter.getGrapRedPacketRecord ( payTypeString,page );
        }else{
            //发出的包
            switch (payType){
                case IMConstants.ROOM_TYPE_MINE_REDPACKET:
                    payTypeString="扫雷发包";
                    break;
                case IMConstants.ROOM_TYPE_GUN_CONTROL_REDPACKET:
                    payTypeString="禁抢发包";
                    break;
                case IMConstants.ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET:
                case IMConstants.ROOM_TYPE_NIUNIU_REDPACKET:
                    payTypeString="牛牛发包";
                    break;
                case IMConstants.ROOM_TYPE_WELFARE_REDPACKET:
                    payTypeString="福利发包";
                    break;
            }
            mPresenter.getSendRedPacketRecord ( payTypeString,page );
        }



    }

    public static void start(Context context, int payType, RedpacketBean redpacketInfo){
        Intent intent = new Intent ( context,RedPacketRecordActivity.class );
        intent.putExtra ( "paytype",payType );
        intent.putExtra ( "redpacketInfo",redpacketInfo );
        context.startActivity ( intent );
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.iv_right})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_right) {
            //保存图片
            View contentView = View.inflate ( this,R.layout.popuwindow_redpacket_record,null );
            PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( this,0.7f,ivRight,contentView,true );
            popuWindowsUtils.showAtLocation(view.getRootView (), Gravity.CENTER,Gravity.BOTTOM );
            contentView.findViewById ( R.id.tv_getredpack ).setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    popuWindowsUtils.dismiss ();
                    //收到红包
                    page = 1;
                    type = 1;
                    getBillingRecord ( 1 );
                }
            } );
            contentView.findViewById ( R.id.tv_sendredpack ).setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    popuWindowsUtils.dismiss ();
                    //发出红包
                    page = 1;
                    type = 2;
                    getBillingRecord ( 1 );
                }
            } );
            contentView.findViewById ( R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    popuWindowsUtils.dismiss ();
                    //取消
                }
            } );
        }
    }

    @Override
    public void getSendRedPacketRecordFail() {

    }

    @Override
    public void getSendRedPacketRecordRefreashSuccessfully(RedPacketRecordBean.ResultBean result) {
        refreshLayout.finishRefresh ();
        if (result!=null ){
            recycleAdapter.setDatas ( result.getList () );
        }
        tvRedpacketNum.setText ( recycleAdapter.getItemCount ()+"" );
        tvRedpacket.setText ( "发出的红包" );
        tvTitle.setText ( "发出的红包" );
        tvNickname.setText ( redpacketBean.getNickname ()+"共发出" );
        tvRedpacketTotal.setText ( Math.abs ( result.getAllmoney () )+"" );
    }

    @Override
    public void getSendRedPacketRecordLoadMoreSuccess(RedPacketRecordBean.ResultBean result) {
        refreshLayout.finishLoadMore ();
        if (result!=null ){
            recycleAdapter.addData ( result.getList () );
            if (result.getList ()==null ||result.getList ().size ()<=0){
                ToastUtils.showShort ( "没有更多数据" );
            }
        }
        tvRedpacketNum.setText ( recycleAdapter.getItemCount ()+"" );
    }

    @Override
    public void getGrapRedPacketRecordRefreashSuccessfully(RedPacketRecordBean.ResultBean result) {
        refreshLayout.finishRefresh ();
        if (result!=null ){
            recycleAdapter.setDatas ( result.getList () );
        }
        tvRedpacketNum.setText ( recycleAdapter.getItemCount ()+"" );
        tvRedpacket.setText ( "收到的红包" );
        tvTitle.setText ( "收到的红包" );
        tvNickname.setText ( redpacketBean.getNickname ()+"共收到" );
        tvRedpacketTotal.setText ( Math.abs ( result.getAllmoney () )+"" );
    }

    @Override
    public void getGrapRedPacketRecordLoadMoreSuccess(RedPacketRecordBean.ResultBean result) {
        refreshLayout.finishLoadMore ();
        if (result!=null ){
            recycleAdapter.addData ( result.getList () );
            if (result.getList ()==null ||result.getList ().size ()<=0){
                ToastUtils.showShort ( "没有更多数据" );
            }
        }
        tvRedpacketNum.setText ( recycleAdapter.getItemCount ()+"" );
    }

    @Override
    public void getGrapRedPacketRecordFail() {

    }

}
