package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.common.eventbus.Subscribe;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerUserInfoComponent;
import com.ooo.main.mvp.contract.UserInfoContract;
import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.ooo.main.mvp.presenter.UserInfoPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 10:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserInfoActivity extends BaseSupportActivity <UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.ll_headimg)
    LinearLayout llHeadimg;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.ll_account)
    LinearLayout llAccount;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.ll_username)
    LinearLayout llUsername;
    @BindView(R2.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R2.id.tv_sex)
    TextView tvSex;
    @BindView(R2.id.ll_sex)
    LinearLayout llSex;
    @BindView(R2.id.iv_head)
    ImageView ivHead;
    private BaseDialog dialog;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "个人信息" );
        if (AppLifecyclesImpl.getUserinfo ()!=null){
            tvNickname.setText ( AppLifecyclesImpl.getUserinfo ().getNickname () );
            tvSex.setText ( AppLifecyclesImpl.getUserinfo ().getGenderMean ());
            Glide.with ( this )
                    .load ( AppLifecyclesImpl.getUserinfo ().getAvatarUrl () )
                    .into ( ivHead );
            tvAccount.setText ( AppLifecyclesImpl.getUserinfo ().getAccount ()+"" );
            tvUsername.setText ( AppLifecyclesImpl.getUserinfo ().getUsername () );
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
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }

    /**
     * {@link ChooseHeadImgActivity#uploadImgSuccessfully(java.lang.String)}
     * @param info
     */
    @Subscriber(tag  = "uploadImgSuccessfully")
    public void onMessageEvent(LoginResultInfo info) {
        Glide.with ( this ).load ( info.getAvatarUrl () ).into ( ivHead );
        //更新信息
        mPresenter.updateMemberInfo ( info );
    }

    @OnClick({R2.id.iv_back, R2.id.ll_headimg, R2.id.ll_nickname, R2.id.ll_account,
            R2.id.ll_username, R2.id.ll_qrcode, R2.id.ll_sex})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.ll_headimg) {
            //头像
            openActivity ( ChooseHeadImgActivity.class );
        } else if (i == R.id.ll_nickname) {
            //昵称
            updateNickName();
        } else if (i == R.id.ll_account) {
            //账号

        } else if (i == R.id.ll_username) {
            //用户名
        } else if (i == R.id.ll_qrcode) {
            //二维码
            openActivity ( QrcodeCardActivity.class );
        } else if (i == R.id.ll_sex) {
            //性别
            chooseSex();
        }
    }

    //选择性别
    private void chooseSex() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_choose_sex, true, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMan = layout.findViewById ( R.id.tv_man);
                TextView tvWoman = layout.findViewById ( R.id.tv_woman );
                tvMan.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        AppLifecyclesImpl.getUserinfo ().setGender ( 1 );
                        mPresenter.updateMemberInfo ( AppLifecyclesImpl.getUserinfo () );
                        tvSex.setText ( tvMan.getText () );
                    }
                } );
                tvWoman.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        AppLifecyclesImpl.getUserinfo ().setGender ( 0 );
                        mPresenter.updateMemberInfo ( AppLifecyclesImpl.getUserinfo () );
                        tvSex.setText ( tvWoman.getText () );
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }

    //修改昵称
    private void updateNickName() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_update_nickname, true, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                EditText etNickName = layout.findViewById ( R.id.et_nickname );
                etNickName.setText ( AppLifecyclesImpl.getUserinfo ().getNickname () );
                TextView btnCancel = layout.findViewById ( R.id.btn_cancel );
                TextView btnSure = layout.findViewById ( R.id.btn_sure );
                btnCancel.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
                btnSure.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        AppLifecyclesImpl.getUserinfo ().setNickname ( etNickName.getText ().toString ().trim () );
                        mPresenter.updateMemberInfo ( AppLifecyclesImpl.getUserinfo () );
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }

    @Override
    public void saveSuccess() {
        tvNickname.setText ( AppLifecyclesImpl.getUserinfo ().getNickname () );
    }
}
