package com.haisheng.easeim.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.AppLifecyclesImpl;
import com.haisheng.easeim.di.component.DaggerNiuNiuRecordComponent;
import com.haisheng.easeim.mvp.contract.NiuNiuRecordContract;
import com.haisheng.easeim.mvp.model.entity.ProfitRecordBean;
import com.haisheng.easeim.mvp.presenter.NiuNiuRecordPresenter;
import com.haisheng.easeim.mvp.ui.adapter.ProfitRecordAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.popupwindow.SelectItemPopupWindow;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 19:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NiuNiuRecordActivity extends BaseActivity <NiuNiuRecordPresenter> implements NiuNiuRecordContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R2.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R2.id.ll_select_type)
    LinearLayout llSelectType;
    @BindView(R2.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R2.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String mStartTime,mEndTime;
    private SimpleDateFormat mDateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private int page = 1;
    private String mType = "";
    private List <String> mTypes;
    private ProfitRecordAdapter recycleAdapter;
    private List<ProfitRecordBean.ResultBean.ListBean> recordBeans = new ArrayList <> (  );

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNiuNiuRecordComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_niu_niu_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "盈亏记录" );
        long currentTime = System.currentTimeMillis();
        mStartTime = mDateFormat.format(currentTime);
        mEndTime = mDateFormat.format(currentTime);
        tvTotalMoney.setText ( "余额："+ AppLifecyclesImpl.getBalance ()+"元" );
        tvStartTime.setText("开始时间："+mStartTime);
        tvEndTime.setText("结束时间："+mEndTime);
        initRecyclelayout();
        setListener();
        getProfitRecord(page);
    }

    public void getProfitRecord(int page){
        mPresenter.getProfitRecord (mStartTime,mEndTime,page,mType);
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener () {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getProfitRecord (page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener () {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                getProfitRecord (page);
            }
        });
        recycleAdapter.setItemClickListener ( new ProfitRecordAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <ProfitRecordBean.ResultBean.ListBean> data, int position) {

            }
        } );
    }

    private void initRecyclelayout() {
        recycleAdapter = new ProfitRecordAdapter ( this, recordBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this,LinearLayoutManager.VERTICAL,false );
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置为垂直布局，这也是默认的
        linearLayoutManager.setOrientation( OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable( ContextCompat.getDrawable(this,R.drawable.recycleview_line_gray_8 ));
        //设置分隔线
        recyclerView.addItemDecoration( divider);
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

    @OnClick({R2.id.iv_back, R2.id.ll_select_type, R2.id.tv_start_time, R2.id.tv_end_time})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.ll_select_type) {
            showSelectTypePopupWindow(llSelectType);
        } else if (i == R.id.tv_start_time) {
            showStartTimePickerView ();
        } else if (i == R.id.tv_end_time) {
            showEndTimePickerView();
        }
    }

    private TimePickerView mStartTimePickerView;
    private void showStartTimePickerView(){
        if(null == mStartTimePickerView){
            mStartTimePickerView = new TimePickerBuilder (this, new OnTimeSelectListener () {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mStartTime = mDateFormat.format(date);
                    tvStartTime.setText("开始时间："+mStartTime);
                    refreshLayout.autoRefresh();
                }
            }).setTitleText("请选择开始时间").build();
        }
        mStartTimePickerView.show();
    }

    private TimePickerView mEndTimePickerView;
    private void showEndTimePickerView(){
        if(null == mEndTimePickerView){
            mEndTimePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mEndTime = mDateFormat.format(date);
                    tvEndTime.setText("结束时间："+mEndTime);
                    refreshLayout.autoRefresh();
                }
            }).setTitleText("请选择结束时间").build();
        }
        mEndTimePickerView.show();
    }

    private SelectItemPopupWindow mSelectTypePopupWindow;
    private void showSelectTypePopupWindow(View v){
        if(null!=mTypes ){
            if(null == mSelectTypePopupWindow){
                mSelectTypePopupWindow = new SelectItemPopupWindow<String>(NiuNiuRecordActivity.this, "请选择账单类型", mTypes, new SelectItemPopupWindow.OnItemClickListener() {
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

    @Override
    public void getProfitRecorddRefreashSuccessfully(ProfitRecordBean.ResultBean result) {
        refreshLayout.finishRefresh ();
        if (result!=null ){
            recycleAdapter.setDatas ( result.getList () );
        }
        mTypes = result.getPaytype ();
    }

    @Override
    public void getProfitRecordLoadMoreSuccess(ProfitRecordBean.ResultBean result) {
        refreshLayout.finishLoadMore ();
        if (result!=null ){
            recycleAdapter.addData ( result.getList () );
            if (result.getList ()==null ||result.getList ().size ()<=0){
                ToastUtils.showShort ( "没有更多数据" );
            }
        }
    }

    @Override
    public void getProfitRecordFail() {

    }
}
