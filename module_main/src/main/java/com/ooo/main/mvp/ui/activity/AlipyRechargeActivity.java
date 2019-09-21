package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerAlipyRechargeComponent;
import com.ooo.main.mvp.contract.AlipyRechargeContract;
import com.ooo.main.mvp.model.entity.RechargeMoneyBean;
import com.ooo.main.mvp.presenter.AlipyRechargePresenter;
import com.ooo.main.mvp.ui.adapter.RechargeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 19:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlipyRechargeActivity extends BaseActivity <AlipyRechargePresenter> implements AlipyRechargeContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.et_input_money)
    EditText etInputMoney;
    @BindView(R2.id.iv_clear)
    ImageView ivClear;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlipyRechargeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alipy_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "支付宝充值" );
        tvMoney.setText ( AppLifecyclesImpl.getUserinfo ().getBalance ()+"" );
        mPresenter.getRechargeMoneyList ();
        setListener();
    }

    private void setListener() {
        etInputMoney.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etInputMoney.getText ().toString ().trim ().length ()>0){
                    ivClear.setVisibility ( View.VISIBLE );
                }else{
                    ivClear.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
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
    public void getRechargeMoneyListSuccess(List <RechargeMoneyBean.ResultBean.AlipayBean> alipay) {
        RechargeAdapter recycleAdapter = new RechargeAdapter ( this, alipay.get ( 0 ).getPaylist () );
        GridLayoutManager gridManager = new GridLayoutManager ( this, 4 );
        //设置布局管理器
        recyclerView.setLayoutManager ( gridManager );
        //设置为垂直布局，这也是默认的
        gridManager.setOrientation ( OrientationHelper.VERTICAL );
        //设置Adapter
        recyclerView.setAdapter ( recycleAdapter );
        recycleAdapter.setItemClickListener ( new RechargeAdapter.ItemClickListener () {
            @Override
            public void onItemClick(List <Integer> data, int position) {
                etInputMoney.setText ( data.get ( position ) + "" );
                etInputMoney.setSelection ( etInputMoney.getText ().toString ().trim ().length () );
            }
        } );
    }

    @Override
    public void getRechargeMoneyListFail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.iv_clear, R2.id.btn_recharge})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_clear) {
            etInputMoney.setText ( "" );
        } else if (i == R.id.btn_recharge) {

        }
    }
}
