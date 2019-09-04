package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.MemberInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

import static com.ooo.main.app.MainConstants.REQUEST_CODE_EDIT_NICKNAME;

public class QrcodeCardActivity extends BaseSupportActivity {


    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.iv_sex)
    ImageView ivSex;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.tv_invite_code)
    TextView tvInviteCode;


    public static void start(Context context, MemberInfo memberInfo) {
        Intent intent = new Intent(context, QrcodeCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("memberInfo", memberInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
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
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            MemberInfo memberInfo = (MemberInfo) bundle.getSerializable("memberInfo");
            if(null != memberInfo){
                ImageLoader.displayHeaderImage(mContext,memberInfo.getAvatarUrl(),ivAvatar);
                tvNickname.setText(memberInfo.getNickname());
                int sexStatusResId = memberInfo.getSex()==MemberInfo.FAMALE ? R.drawable.ic_female : R.drawable.ic_male;
                ivSex.setImageResource(sexStatusResId);
                ImageLoader.displayImage(mContext,memberInfo.getInviteCodeUrl(),ivQrcode);
                tvInviteCode.setText(String.format("邀请码:%s",memberInfo.getInviteCode()));
            }
        }
    }
}
