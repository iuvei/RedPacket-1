package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.MemberInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

public class QrcodeCardActivity extends BaseSupportActivity {


    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.iv_sex)
    ImageView ivSex;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.tv_accountnum)
    TextView tvAccountnum;


    public static void start(Context context, MemberInfo memberInfo) {
        Intent intent = new Intent ( context, QrcodeCardActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "memberInfo", memberInfo );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_qrcode_card;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "二维码" );
        ivRight.setVisibility ( View.VISIBLE );
        ivRight.setImageResource ( R.drawable.icon_go );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            MemberInfo memberInfo = (MemberInfo) bundle.getSerializable ( "memberInfo" );
            if (null != memberInfo) {
                ImageLoader.displayHeaderImage ( mContext, memberInfo.getAvatarUrl (), ivAvatar );
                tvUsername.setText ( memberInfo.getNickname () );
                int sexStatusResId = memberInfo.getSex () == MemberInfo.FAMALE ? R.drawable.ic_female : R.drawable.ic_male;
                ivSex.setImageResource ( sexStatusResId );
                ImageLoader.displayImage ( mContext, memberInfo.getInviteCodeUrl (), ivQrcode );
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.iv_right})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_right) {
            //保存图片
            View contentView = View.inflate ( this,R.layout.popupwindow_save_qr,null );
            PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( this,1,ivRight,contentView,true );
            popuWindowsUtils.showViewBottom_AlignRight(ivRight);
            contentView.findViewById ( R.id.ll_save_qr ).setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    popuWindowsUtils.dismiss ();
                }
            } );
        }
    }
}
