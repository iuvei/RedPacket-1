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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerLuckyWheelComponent;
import com.ooo.main.mvp.contract.LuckyWheelContract;
import com.ooo.main.mvp.presenter.LuckyWheelPresenter;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R2.id.zpan)
    PieView zpan;
    @BindView(R2.id.iv_go)
    ImageView ivGo;
    private boolean isRunning=false;

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
    }

    private void setListener() {
        zpan.setListener(new PieView.RotateListener() {
            @Override
            public void value(String s) {
                isRunning=false;
               /* new AlertDialog.Builder(MainActivity.this)
                        .setTitle("鹿死谁手呢？")
                        .setMessage(s)
                        .setIcon(R.drawable.f015)
                        .setNegativeButton("退出",null)
                        .show();*/
            }
        });

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
            if (!isRunning) {
                Random random = new Random ();
                //pieView.rotate(i[random.nextInt(4)]);
                zpan.rotate ( 3 );
            }
            isRunning = true;
        }
    }
}
