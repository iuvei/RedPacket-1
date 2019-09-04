package com.ooo.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

public class LoginAdActivity extends BaseSupportActivity {

    @BindView(R2.id.banner)
    BGABanner banner;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_ad;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        banner.setData(R.drawable.uoko_guide_foreground_1, R.drawable.uoko_guide_foreground_2, R.drawable.uoko_guide_foreground_3);
        banner.setAdapter(new BGABanner.Adapter<ImageView, BannerEntity>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerEntity itemModel, int position) {
                ImageLoader.displayImage(mContext, itemModel.getImageUrl(), itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate<ImageView, BannerEntity>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerEntity itemModel, int position) {
//                showMessage(itemModel.getTitle());
            }
        });
    }

    @OnClick({R2.id.tv_go_login, R2.id.tv_go_register})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_go_login) {
            openActivity(LoginActivity.class);

        } else if (i == R.id.tv_go_register) {
            openActivity(RegisterActivity.class);
        }
    }
}
