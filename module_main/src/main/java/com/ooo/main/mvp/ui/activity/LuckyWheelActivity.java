package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerLuckyWheelComponent;
import com.ooo.main.mvp.contract.LuckyWheelContract;
import com.ooo.main.mvp.model.entity.LuckyDrawSettingBean;
import com.ooo.main.mvp.model.entity.StartLuckyDrawBean;
import com.ooo.main.mvp.presenter.LuckyWheelPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.view.PieView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 17:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

@Route ( path = RouterHub.MAIN_LUCKYWHEELACTIVITY)
public class LuckyWheelActivity extends BaseSupportActivity <LuckyWheelPresenter> implements LuckyWheelContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.tv_luckynum)
    TextView tvLuckyNum;
    @BindView(R2.id.zpan)
    PieView zpan;
    @BindView(R2.id.iv_go)
    ImageView ivGo;
    private String[] mStrings;
    private int luckyNum;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLuckyWheelComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lucky_wheel; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "幸运大转盘" );
        tvRight.setVisibility ( View.VISIBLE );
        tvRight.setText ( "抽奖记录" );
        setListener ();
        ivGo.setEnabled ( false );
        mPresenter.getTurnTableSettingInfo ();
    }

    private void setListener() {

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.iv_go})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
            openActivity ( LuckyDrawActivity.class );
        } else if (i == R.id.iv_go) {
            //启动转盘
            zpan.startRatate ();
            if (luckyNum<=0){
                ToastUtils.showShort ( "抽奖次数为0" );
                return;
            }
            luckyNum--;
            tvLuckyNum.setText ( "剩余抽奖次数："+luckyNum+"次" );
            mPresenter.startLuckyDraw ();
        }
    }

    @Override
    public void getLuckyDrawSettingSuccess(LuckyDrawSettingBean.ResultBean result) {
        List<String> prizeItem = result.getList ();
        if (result==null){
            ToastUtils.showShort ( "暂无奖项" );
            return;
        }
        mStrings = new String[prizeItem.size ()];
        for (int i = 0;i<prizeItem.size ();i++){
            mStrings[i] = prizeItem.get ( i );
        }
        zpan.setmStrings ( mStrings );
        if (ConvertNumUtils.stringToInt ( result.getWelfarenums () )>0){
            ivGo.setEnabled ( true );
            ivGo.setImageResource ( R.drawable.go );
        }
        luckyNum = ConvertNumUtils.stringToInt ( result.getWelfarenums () );
        tvLuckyNum.setText ( "剩余抽奖次数："+result.getWelfarenums ()+"次" );
    }

    @Override
    public void getLuckyDrawSettingFail() {

    }

    @Override
    public void getLuckyDrawResultSuccess(StartLuckyDrawBean.ResultBean result) {
        zpan.rotate ( result.getPricekk () );
        zpan.setListener ( new PieView.RotateListener () {
            @Override
            public void value(String s) {
                ToastUtils.showShort ( s );
            }
        } );
    }

    @Override
    public void getLuckyDrawResultFail() {

    }

    @Override
    protected void onDestroy() {
        zpan.endRatate();
        super.onDestroy ();
    }
}
