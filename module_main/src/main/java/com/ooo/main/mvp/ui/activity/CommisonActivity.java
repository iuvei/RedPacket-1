package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerCommisonComponent;
import com.ooo.main.mvp.contract.CommisonContract;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.CommisonListBean;
import com.ooo.main.mvp.model.entity.CommissionInfo;
import com.ooo.main.mvp.presenter.CommisonPresenter;
import com.ooo.main.mvp.ui.adapter.CommissionInfoAdapter;
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
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommisonActivity extends BaseActivity <CommisonPresenter> implements CommisonContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_week)
    TextView tvWeek;
    @BindView(R2.id.tv_day)
    TextView tvDay;
    @BindView(R2.id.tv_nodata)
    TextView tvNodata;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List <CommisonListBean.ResultBean.ListBean> commissionInfos = new ArrayList <> (  );
    private CommissionInfoAdapter recycleAdapter;
    private int pageNum = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommisonComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_commison; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "佣金列表" );
        getCommissionInfo(1);
        recycleAdapter = new CommissionInfoAdapter ( this, commissionInfos);
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
                commissionInfos.clear ();
                getCommissionInfo(1);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener () {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getCommissionInfo(pageNum);
            }
        });
        recycleAdapter.setItemClickListener ( new CommissionInfoAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <CommisonListBean.ResultBean.ListBean> data, int position) {
                CommisonListBean.ResultBean.ListBean bean = (CommisonListBean.ResultBean.ListBean) data.get ( position );
                Bundle bundle = new Bundle();
                bundle.putLong("roomId",ConvertNumUtils.stringToLong ( bean.getRoomid () ));
                bundle.putLong("redpacketId",ConvertNumUtils.stringToLong ( bean.getGoldid () ));
                ARouterUtils.navigation(CommisonActivity.this, RouterHub.IM_REDPACKETDETAILACTIVITY,bundle);
            }
        } );
    }

    private void getCommissionInfo(int pageNum){
        mPresenter.getCommissionList ( pageNum );
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

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish ();
    }

    @Override
    public void getCommissionListRefrestSuccess(CommisonListBean.ResultBean result) {
        refreshLayout.finishRefresh ();
        if (result!=null ){
            tvNodata.setVisibility ( View.GONE );
            recycleAdapter.setDatas ( result.getList () );
            tvDay.setText ( result.getDayprofit ()+"");
            tvWeek.setText ( result.getBeginThisweek ()+"" );
        }
    }

    @Override
    public void getCommissionListFail() {

    }

    @Override
    public void getCommissionListLoadMoreSuccess(CommisonListBean.ResultBean result) {
        refreshLayout.finishLoadMore ();
        if (result!=null ){
            recycleAdapter.addData ( result.getList () );
            if (result.getList ()==null ||result.getList ().size ()<=0){
                ToastUtils.showShort ( "没有更多数据" );
            }
        }
    }
}
