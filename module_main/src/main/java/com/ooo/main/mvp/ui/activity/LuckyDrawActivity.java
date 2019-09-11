package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerLuckyDrawComponent;
import com.ooo.main.mvp.contract.LuckyDrawContract;
import com.ooo.main.mvp.model.entity.LuckyBean;
import com.ooo.main.mvp.presenter.LuckyDrawPresenter;
import com.ooo.main.mvp.ui.adapter.LuckyRecordAdapter;
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
 * Created by MVPArmsTemplate on 09/11/2019 18:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LuckyDrawActivity extends BaseActivity <LuckyDrawPresenter> implements LuckyDrawContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<LuckyBean> luckyBeans;
    private LuckyRecordAdapter recycleAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLuckyDrawComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lucky_draw; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "抽奖记录" );
        luckyBeans = new ArrayList <> ();
        getWithdrawalRecord ();
        recycleAdapter = new LuckyRecordAdapter ( this, luckyBeans );
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
                refreshlayout.finishRefresh ( 2000/*,false*/ );//传入false表示加载失败
                luckyBeans.clear ();
                getWithdrawalRecord ();
                recycleAdapter.setDatas ( luckyBeans );
            }
        } );
        refreshLayout.setOnLoadMoreListener ( new OnLoadMoreListener () {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore ( 2000/*,false*/ );//传入false表示加载失败
                getWithdrawalRecord ();
                recycleAdapter.addData ( luckyBeans );
            }
        } );

    }

    private void getWithdrawalRecord() {
        for (int i = 0; i < 10; i++) {
            LuckyBean luckyBean = new LuckyBean ();
            luckyBean.setPrize ( "谢谢惠顾" );
            luckyBean.setAward ( "未中奖" );
            luckyBeans.add ( luckyBean );
        }
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
}
