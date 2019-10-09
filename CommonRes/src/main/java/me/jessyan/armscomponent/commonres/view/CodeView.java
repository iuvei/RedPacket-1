package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * creat at 2019/10/9
 * description
 * 验证码
 */
public class CodeView extends View {
    int defaultWidth = DensityUtil.dp2px ( 80 );
    int defaultHeight = DensityUtil.dp2px ( 50 );
    Paint bgPaint;
    Paint textPaint1;
    Paint textPaint2;
    Paint textPaint3;
    Paint textPaint4;
    Paint linePaint1;
    Paint linePaint2;
    Paint linePaint3;
    Paint linePaint4;
    String code1;
    String code2;
    String code3;
    String code4;
    Point point1;
    Point point2;
    Point point3;
    Point point4;
    boolean firstLoad = true;
    private int widthSize;
    private int heightSize;
    private String[] code = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6",
            "7","8","9","0"};
    public CodeView(Context context) {
        this ( context,null );
    }

    public CodeView(Context context, AttributeSet attrs) {
        this ( context, attrs,0 );
    }

    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super ( context, attrs, defStyleAttr );
        init();
    }

    private void init() {
        bgPaint = new Paint (  );
        bgPaint.setAntiAlias ( true );
        textPaint1 = new Paint (  );
        textPaint1.setAntiAlias ( true );
        textPaint2 = new Paint (  );
        textPaint2.setAntiAlias ( true );
        textPaint3 = new Paint (  );
        textPaint3.setAntiAlias ( true );
        textPaint4 = new Paint (  );
        textPaint4.setAntiAlias ( true );
        linePaint1 = new Paint (  );
        linePaint1.setAntiAlias ( true );
        linePaint2 = new Paint (  );
        linePaint2.setAntiAlias ( true );
        linePaint3 = new Paint (  );
        linePaint3.setAntiAlias ( true );
        linePaint4 = new Paint (  );
        linePaint4.setAntiAlias ( true );
        point1 = new Point (  );
        point2 = new Point (  );
        point3 = new Point (  );
        point4 = new Point (  );
        settingPaint ();
    }

    public void settingPaint(){
        bgPaint.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        textPaint1.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        textPaint2.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        textPaint3.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        textPaint4.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        linePaint1.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        linePaint2.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        linePaint3.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        linePaint4.setARGB ( 255,((int)(Math.random ()*255)),((int)(Math.random ()*255)),((int)(Math.random ()*255)) );
        textPaint1.setTextSize ( DensityUtil.dp2px ( (float) (Math.random ()*5+20) ) );
        textPaint2.setTextSize ( DensityUtil.dp2px ( (float) (Math.random ()*5+20) ) );
        textPaint3.setTextSize ( DensityUtil.dp2px ( (float) (Math.random ()*5+20) ) );
        textPaint4.setTextSize ( DensityUtil.dp2px ( (float) (Math.random ()*5+20) ) );
        setRandomCode();
    }

    //设置随机数字及位置
    private void setRandomCode() {
        code1 = code[(int) (Math.random ()*code.length-1)];
        point1.x = (int)(Math.random ()*(widthSize/4 -textPaint1.getTextSize ()));
        point1.y = (int)(textPaint1.getTextSize ()+(float)(Math.random ()*( heightSize-textPaint1.getTextSize ())));
        code2 = code[(int) (Math.random ()*code.length-1)];
        point2.x = (int)(widthSize/4+Math.random ()*(widthSize/4 -textPaint2.getTextSize ()));
        point2.y = (int)(textPaint2.getTextSize ()+(float)(Math.random ()*( heightSize-textPaint2.getTextSize ())));
        code3 = code[(int) (Math.random ()*code.length-1)];
        point3.x = (int)(widthSize/2+Math.random ()*(widthSize/4 -textPaint3.getTextSize ()));
        point3.y = (int)(textPaint3.getTextSize ()+(float)(Math.random ()*( heightSize-textPaint3.getTextSize ())));
        code4 = code[(int) (Math.random ()*code.length-1)];
        point4.x = (int)(widthSize*3/4+Math.random ()*(widthSize/4 -textPaint4.getTextSize ()));
        point4.y = (int)(textPaint4.getTextSize ()+(float)(Math.random ()*( heightSize-textPaint4.getTextSize ())));
        invalidate ();
    }

    /**
     * 获取生成的验证码
     * @return
     */
    public String getCode(){
        return code1+code2+code3+code4;
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure ( widthMeasureSpec, heightMeasureSpec );
        int widthMode = MeasureSpec.getMode ( widthMeasureSpec );
        widthSize = MeasureSpec.getSize ( widthMeasureSpec );
        int heightMode = MeasureSpec.getMode ( heightMeasureSpec );
        heightSize = MeasureSpec.getSize ( heightMeasureSpec );
        switch (widthMode){
            case MeasureSpec.AT_MOST:
                widthSize = defaultWidth;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                widthSize = widthSize>defaultWidth?defaultWidth:widthSize;
                break;
        }
        switch (heightMode){
            case MeasureSpec.AT_MOST:
                heightSize = defaultHeight;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                heightSize = heightSize>defaultHeight?defaultHeight:heightSize;
                break;
        }
        setMeasuredDimension ( widthSize,heightSize );
        if (firstLoad){
            settingPaint ();
        }
        firstLoad = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect ( 0,0,widthSize,heightSize,DensityUtil.dp2px ( 5 ),DensityUtil.dp2px ( 2 ),bgPaint );
        //绘制文字
        canvas.drawText ( code1,point1.x,point1.y,textPaint1);
        canvas.drawText ( code2,point2.x,point2.y,textPaint2);
        canvas.drawText ( code3,point3.x,point3.y,textPaint3);
        canvas.drawText ( code4,point4.x,point4.y,textPaint4);
        //绘制线
        canvas.drawLine ( (float) (Math.random ()*widthSize),(float) (Math.random ()*heightSize),
                (float)(Math.random ()*widthSize),(float)(Math.random ()*heightSize),linePaint1 );
        canvas.drawLine ( (float) (Math.random ()*widthSize),(float) (Math.random ()*heightSize),
                (float)(Math.random ()*widthSize),(float)(Math.random ()*heightSize),linePaint2 );
        canvas.drawLine ( (float) (Math.random ()*widthSize),(float) (Math.random ()*heightSize),
                (float)(Math.random ()*widthSize),(float)(Math.random ()*heightSize),linePaint3 );
        canvas.drawLine ( (float) (Math.random ()*widthSize),(float) (Math.random ()*heightSize),
                (float)(Math.random ()*widthSize),(float)(Math.random ()*heightSize),linePaint4 );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction ()){
            case MotionEvent.ACTION_UP:
                settingPaint();
                break;
        }
        return true;
    }
}
