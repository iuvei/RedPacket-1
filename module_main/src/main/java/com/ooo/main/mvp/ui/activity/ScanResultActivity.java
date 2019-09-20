package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerScanResultComponent;
import com.ooo.main.mvp.contract.ScanResultContract;
import com.ooo.main.mvp.model.entity.UserInfoFromIdBean;
import com.ooo.main.mvp.presenter.ScanResultPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 17:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ScanResultActivity extends BaseActivity <ScanResultPresenter> implements ScanResultContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_userhead)
    ImageView ivUserhead;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.iv_sex)
    ImageView ivSex;
    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.rl_user)
    RelativeLayout rlUser;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScanResultComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scan_result; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void start(Context context, String userAccount) {
        Intent intent = new Intent ( context, ScanResultActivity.class );
        intent.putExtra ( "account", userAccount );
        context.startActivity ( intent );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "详细资料" );
        String account = getIntent ().getStringExtra ( "account" );
        mPresenter.getUserInfoFromId ( account );
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
    public void getUserInfoSuccess(UserInfoFromIdBean.ResultBean bean) {
        rlUser.setVisibility ( View.VISIBLE );
        if (bean!=null) {
            tvAccount.setText ( bean.getId () );
            tvNickname.setText ( bean.getNickname () );
            if (bean.isMan ()){
                ivSex.setImageResource ( R.drawable.ic_male );
            }else{
                ivSex.setImageResource ( R.drawable.ic_female );
            }
            Glide.with ( this ).load ( bean.getAvatar () ).into ( ivUserhead );
        }
    }

    @Override
    public void getUserInfoFail() {

    }
}
