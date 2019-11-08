package me.jessyan.armscomponent.commonres.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

public class LongImageActivity extends BaseSupportActivity {

    private SubsamplingScaleImageView imageView;
    private String mImageUrl;

    public static void start(Context context, String imageUrl) {
        Bundle bundle = new Bundle ();
        bundle.putString ( "imageUrl", imageUrl );
        Intent intent = new Intent ( context, LongImageActivity.class );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_long_image;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            mImageUrl = bundle.getString ( "imageUrl" );
        }

        imageView = findViewById ( R.id.imageView );
        imageView.setMinimumScaleType ( SubsamplingScaleImageView.SCALE_TYPE_CUSTOM );
        imageView.setMinScale ( 1.0F );//最小显示比例
        imageView.setMaxScale ( 1.0F );//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）


//下载图片保存到本地
        Glide.with ( this )
                .load ( mImageUrl ).downloadOnly ( new SimpleTarget <File> () {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition <? super File> transition) {
                imageView.setImage ( ImageSource.uri ( Uri.fromFile ( resource ) ), new ImageViewState ( 2.0F, new PointF ( 0, 0 ), 0 ) );
            }
        } );

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
    public float getInitImageScale(Bitmap bitmap) {
        WindowManager wm = this.getWindowManager ();
        int width = wm.getDefaultDisplay ().getWidth ();
        int width1 = ArmsUtils.getScreenWidth ( mContext );
        width1 = width1 + 1;
        int height = wm.getDefaultDisplay ().getHeight ();
        // 拿到图片的宽和高
        int dw = bitmap.getWidth ();
        int dh = bitmap.getHeight ();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

}
