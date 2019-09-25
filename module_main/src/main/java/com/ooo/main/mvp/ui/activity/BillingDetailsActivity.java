package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerBillingDetailsComponent;
import com.ooo.main.mvp.contract.BillingDetailsContract;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;
import com.ooo.main.mvp.presenter.BillingDetailsPresenter;
import com.ooo.main.mvp.ui.adapter.BillingRecordAdapter;
import com.ooo.main.mvp.ui.adapter.WithdrawalRecordAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 16:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * 账单明细
 */
@Route(path = RouterHub.MAIN_BILLLISTACTIVITY)
public class BillingDetailsActivity extends BaseActivity <BillingDetailsPresenter> implements BillingDetailsContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List <BillingDetailBean.ResultBean.ListBean> recordBeans;
    private BillingRecordAdapter recycleAdapter;
    private int page;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBillingDetailsComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_billing_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "账单" );
        recordBeans = new ArrayList <> (  );
        getBillingRecord(1);
        recycleAdapter = new BillingRecordAdapter ( this, recordBeans);
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
        recycleAdapter.setItemClickListener ( new BillingRecordAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <BillingDetailBean.ResultBean.ListBean> data, int position) {
                Intent intent = new Intent ( BillingDetailsActivity.this,BillingInfoActivity.class );
                intent.putExtra ( "info",data.get ( position ) );
                startActivity ( intent );
            }
        } );
    }

    private void getBillingRecord(int page) {
        mPresenter.getBillingDetails (page);
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
    public void getBillingRecordRecordLoadMoreSuccess(BillingDetailBean.ResultBean result) {
        refreshLayout.finishLoadMore ();
        if (result!=null ){
            recycleAdapter.addData ( result.getList () );
            if (result.getList ()==null ||result.getList ().size ()<=0){
                ToastUtils.showShort ( "没有更多数据" );
            }
        }
    }

    @Override
    public void getBillingRecordRefreashSuccessfully(BillingDetailBean.ResultBean result) {
        refreshLayout.finishRefresh ();
        if (result!=null ){
            recycleAdapter.setDatas ( result.getList () );
        }
    }

    @Override
    public void getBillingRecordRecordFail() {

    }
}
