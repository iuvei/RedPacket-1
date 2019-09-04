package com.ooo.main.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;

import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class LongImageActivity extends BaseSupportActivity {

    SubsamplingScaleImageView imageView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_long_image;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        imageView = findViewById(R.id.imageView);

//        Bitmap bitmap = ImageUtils.getBitmap(R.drawable.ic_grab_redpacket_reward_big);
//        Bitmap bitmap = Glide.with(mContext)
//                .load(R.drawable.ic_grab_redpacket_reward_big)
//                .asBitmap() //必须
//                .centerCrop()
//                .get();
//        float initImageScale = getInitImageScale(bitmap);
//        imageView.setZoomEnabled(false);
//        imageView.setImage(ImageSource.resource(R.drawable.ic_grab_redpacket_reward_big),new ImageViewState(initImageScale, new PointF(0, 0),0));
    }

    /**
     * 计算出图片初次显示需要放大倍数
     */
    public float getInitImageScale( Bitmap bitmap){
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int width1 = ArmsUtils.getScreenWidth(mContext);
        width1 = width1+1;
        int height = wm.getDefaultDisplay().getHeight();
        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }
}
