package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import static android.view.View.getDefaultSize;

public class SquareView extends RelativeLayout {
    public SquareView(Context context) {
        super(context); }
    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();

        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec( childWidthSize, View.MeasureSpec.EXACTLY);
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec;
        //设定高是宽的比例
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
