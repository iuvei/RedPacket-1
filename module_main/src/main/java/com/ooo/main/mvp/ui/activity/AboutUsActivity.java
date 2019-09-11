package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerAboutUsComponent;
import com.ooo.main.mvp.contract.AboutUsContract;
import com.ooo.main.mvp.presenter.AboutUsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.ui.WebviewActivity;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AboutUsActivity extends BaseSupportActivity <AboutUsPresenter> implements AboutUsContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_version)
    TextView tvVersion;
    @BindView(R2.id.ll_privacy)
    LinearLayout llPrivacy;
    @BindView(R2.id.ll_user)
    LinearLayout llUser;
    @BindView(R2.id.ll_withdraw)
    LinearLayout llWithdraw;
    @BindView(R2.id.ll_version)
    LinearLayout llVersion;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "关于我们" );
        tvVersion.setText ( AppUtils.getAppVersionName () );
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

    @OnClick({R2.id.iv_back, R2.id.ll_privacy, R2.id.ll_user, R2.id.ll_withdraw, R2.id.ll_version})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.ll_privacy) {
           WebviewActivity.start ( this,"隐私协议","http://www.baidu.com" );
        } else if (i == R.id.ll_user) {
            WebviewActivity.start ( this,"用户协议","http://www.baidu.com" );
        } else if (i == R.id.ll_withdraw) {
            WebviewActivity.start ( this,"提现协议","http://www.baidu.com" );
        } else if (i == R.id.ll_version) {
            //WebviewActivity.start ( this,"版本更新","http://www.baidu.com" );
        }
    }
}
