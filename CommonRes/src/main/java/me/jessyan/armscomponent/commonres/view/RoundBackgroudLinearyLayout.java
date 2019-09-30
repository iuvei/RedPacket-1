package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import me.jessyan.armscomponent.commonres.R;

/**
 * creat at 2019/9/5
 * description 圆形背景的linearlayout
 */
public class RoundBackgroudLinearyLayout extends View {

    int diameter;
    private int height;
    private int width;
    private Paint paint;
    private int roundColor;

    public RoundBackgroudLinearyLayout(Context context) {
        this( context,null);
    }

    public RoundBackgroudLinearyLayout(Context context, AttributeSet attrs) {
        this ( context, attrs,0 );
    }

    public RoundBackgroudLinearyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super ( context, attrs, defStyleAttr );
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.mRoundBackgroud);
        roundColor = a.getColor ( R.styleable.mRoundBackgroud_roundColor,Color.parseColor ( "#ffffff" ) );
        a.recycle();// 使用完后记得回收
        paint = new Paint (  );
        paint.setAntiAlias(true);
        paint.setColor ( roundColor );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure ( widthMeasureSpec, heightMeasureSpec );
        int heightMode = MeasureSpec.getMode ( heightMeasureSpec );
        height = MeasureSpec.getSize ( heightMeasureSpec );
        int widthMode = MeasureSpec.getMode ( widthMeasureSpec );
        width = MeasureSpec.getSize ( widthMeasureSpec );
        switch (heightMode){
            case MeasureSpec.AT_MOST:
                height = 100;
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        switch (widthMode){
            case MeasureSpec.AT_MOST:
                width = 100;
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        diameter = height>width?width:height;
        setMeasuredDimension ( width,height );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw ( canvas );
        Log.e ( "tag","height="+height+";width="+width );
        canvas.drawCircle ( width/2,height/2,diameter/2,paint );
    }
}
