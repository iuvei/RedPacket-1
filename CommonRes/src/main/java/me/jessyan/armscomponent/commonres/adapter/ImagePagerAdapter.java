package me.jessyan.armscomponent.commonres.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import uk.co.senab.photoview.PhotoView;


public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mImageViews;
    private List<String> mMediaUrls;

    public ImagePagerAdapter(Context context, List<String> imageUrls){
        super();
        this.mContext = context;
        this.mMediaUrls = imageUrls;
        if(mMediaUrls != null && mMediaUrls.size()>0){
            List<View> views = new ArrayList<>();
            for (String mediaUrl : mMediaUrls) {
                    PhotoView imageView = new PhotoView(mContext);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    ImageLoader.displayImage(mContext,mediaUrl,imageView);
                    views.add(imageView);
            }
            mImageViews = views;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(null == mMediaUrls)
            super.getPageTitle(position);

        return String.valueOf(position+1);
    }

    @Override
    public int getCount() {
        return mImageViews==null ? 0 : mImageViews.size();
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可。这里判断当前的View是否和对应的key关联。这里的key和instantiateItem方法的返回值一致。
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    // PagerAdapter只缓存三张要显示的图片，如果此时滑动到第三页时，第一页就会调用该方法去销毁相应的View。
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mImageViews.get(position));
    }

    // 该方法是按需调用，默认情况先调用三次，将三个即将使用View页面添加到ViewGroup中，当你滑动到第二页View时，ViewPager会调用一次该方法将第四个View页面添加到ViewGroup中。该方法返回值作为key和对应位置的View关联起来。用于isViewFromObject方法判断当前View和key是否关联的。
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(mImageViews.get(position));
        return mImageViews.get(position);
    }


}