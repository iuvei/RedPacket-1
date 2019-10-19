package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerUnderLineListComponent;
import com.ooo.main.mvp.contract.UnderLineListContract;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;
import com.ooo.main.mvp.presenter.UnderLineListPresenter;
import com.ooo.main.mvp.ui.adapter.UnderLineListAdapter;
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
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
 * 下线列表
 */
public class UnderLineListActivity extends BaseActivity <UnderLineListPresenter> implements UnderLineListContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.btn_pre)
    Button btnPre;
    @BindView(R2.id.btn_next)
    Button btnNext;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List <UnderPayerBean.ResultBean.ListBean> underLineBeans;
    private UnderLineListAdapter recycleAdapter;
    private int page = 1;
    private int level = 0;
    private String agenid = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUnderLineListComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_under_line_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "下线列表" );
        underLineBeans = new ArrayList <> ();
        getUnderLine ( page, level, agenid );
        recycleAdapter = new UnderLineListAdapter ( this, underLineBeans );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager ( this, LinearLayoutManager.VERTICAL, false );
        //设置布局管理器
        recyclerView.setLayoutManager ( linearLayoutManager );
        //设置为垂直布局，这也是默认的
        linearLayoutManager.setOrientation ( OrientationHelper.VERTICAL );
        //设置Adapter
        recyclerView.setAdapter ( recycleAdapter );
        //设置分隔线
        recyclerView.addItemDecoration ( new DividerGridItemDecoration ( this ) );
        setListener ();
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener ( new OnRefreshListener () {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getUnderLine ( page, level, agenid );
                recycleAdapter.setDatas ( underLineBeans );
            }
        } );
        refreshLayout.setOnLoadMoreListener ( new OnLoadMoreListener () {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                getUnderLine ( page, level, agenid );
            }
        } );

        recycleAdapter.setItemClickListener ( new UnderLineListAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <UnderPayerBean.ResultBean.ListBean> data, int position) {
                page = 1;
                getUnderLine ( page, level, agenid );
            }
        } );
    }

    public void getUnderLine(int page, int level, String agenid) {
        mPresenter.getUnderLineList ( "", "", page, level, agenid );
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

    @OnClick({R2.id.btn_pre, R2.id.btn_next})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.btn_pre) {
            page= 1;

        } else if (i == R.id.btn_next) {
            page= 1;

        }
    }

    @Override
    public void getUnderLineRefrestSuccess(UnderPayerBean underPayerBean) {
        refreshLayout.finishRefresh ();
        if (underPayerBean.getResult ().getList () != null) {
            recycleAdapter.setDatas ( underPayerBean.getResult ().getList () );
        }
    }

    @Override
    public void getUnderLineLoadMoreSuccess(UnderPayerBean underPayerBean) {
        refreshLayout.finishLoadMore ();
        if (underPayerBean.getResult ().getList () != null && underPayerBean.getResult ().getList ().size ()>0) {
            recycleAdapter.addData ( underPayerBean.getResult ().getList () );
        }else{
            ToastUtils.showShort ( "没有更多数据了" );
        }
    }

    @Override
    public void getUnderLineRefrestFail() {
        refreshLayout.finishRefresh ();
    }

    @Override
    public void getUnderLineLoadMoreFail() {
        refreshLayout.finishLoadMore ();
    }
}
