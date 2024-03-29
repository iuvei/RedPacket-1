package me.jessyan.armscomponent.commonres.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import me.jessyan.armscomponent.commonres.R;

/**
 * ViewPage 指示器
 */
public class MyPageIndicator extends LinearLayout implements PageGridView.PageIndicator {
    public MyPageIndicator(Context context) {
        this(context, null);
    }

    public MyPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void InitIndicatorItems(int itemsNumber) {
        removeAllViews();
        for (int i = 0; i < itemsNumber; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.dot_unselected);
            imageView.setPadding(10, 0, 10, 0);
            addView(imageView);
        }
    }

    @Override
    public void onPageSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if(imageView!=null) {
            imageView.setImageResource(R.drawable.dot_selected);
        }
    }

    @Override
    public void onPageUnSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if(imageView!=null) {
            imageView.setImageResource(R.drawable.dot_unselected);
        }
    }
}
