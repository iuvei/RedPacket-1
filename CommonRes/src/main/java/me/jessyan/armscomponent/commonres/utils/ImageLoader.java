package me.jessyan.armscomponent.commonres.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.armscomponent.commonres.R;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ImageLoader {

    public static void displayVideo(Context mContext, String url, ImageView imageView){
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext.getApplicationContext(), ImageConfigImpl
                        .builder()
                        .url(url)
                        .imageView(imageView)
                        .build());
    }

    public static void displayHeaderImage(Context mContext, String url, ImageView imageView){
//        ArmsUtils.obtainAppComponentFromContext(mContext)
//                .imageLoader()
//                .loadImage(mContext.getApplicationContext(), ImageConfigImpl
//                        .builder()
//                        .url(url)
//                        .placeholder(R.mipmap.icon_default_avatar)
//                        .errorPic(R.mipmap.icon_default_avatar)
//                        .imageRadius(10)
//                        .isCenterCrop(true)
//                        .isCrossFade(true)
//                        .imageView(imageView)
//                        .build());
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(10);
        RequestOptions options= RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(mContext).load(url).apply(options).into(imageView);
    }

    public static void displayImage(Context mContext, int resId, ImageView imageView){
        Glide.with(mContext).load(resId).into(imageView);
    }

    public static void displayImage(Context mContext, String url, ImageView imageView){
        displayImage(mContext,url,R.mipmap.holder,R.mipmap.receive_picturebroken,imageView);
    }

    public static void displayImage(Context mContext, String url, int placeholderIcnResId, ImageView imageView){
        displayImage(mContext,url,placeholderIcnResId,R.mipmap.receive_picturebroken,imageView);
    }

    public static void displayImage(Context mContext, String url, int placeholderIcnResId, int errIcnResId, ImageView imageView){
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext.getApplicationContext(), ImageConfigImpl
                        .builder()
                        .url(url)
                        .imageRadius(5)
                        .placeholder(placeholderIcnResId)
                        .errorPic(errIcnResId)
                        .imageView(imageView)
                        .build());
    }

}
