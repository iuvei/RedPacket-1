package com.ooo.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.MainConstants;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonres.utils.SpUtils;

public class GuideActivity extends BaseSupportActivity {


    @BindView(R2.id.banner)
    BGABanner banner;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guide;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        banner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.bg_guide_1,
                R.drawable.bg_guide_2,
                R.drawable.bg_guide_3);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(i == 2){
                    enterLoginAdActivity();
//                    new Handler().postDelayed(() -> enterLoginAdActivity(),1000);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void enterLoginAdActivity() {
        SpUtils.put(mContext, MainConstants.FIRST_START, true);
        openActivity(LoginActivity.class);
        finish();
    }

}
