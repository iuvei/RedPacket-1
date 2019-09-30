package me.jessyan.armscomponent.commonres.utils;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 0
 * @email 819715035@qq.com
 * creat at $date$
 * description 相对控件各个方向弹出popuwindow
 * 用法  popupWindow = new PopuWindowsUtils(MainActivity.this,0.7f,view,true);
 *      popupWindow.showViewRight_AlignTop(button);
 *
 */
public class PopuWindowsUtils extends PopupWindow {

    private boolean isShowLeft;
    private boolean isShowRight;
    private boolean isShowUp;
    private boolean isShowDown;
    private boolean isAlignLeft;
    private boolean isAlignRight;
    private boolean isAlignTop;
    private boolean isAlignBottom;
    private boolean heightMiddle;

    /**
     *
     * @param activity 上下文
     * @param alpha  弹出框背景的阴影透明度
     * @param contentView 弹出框的布局
     * @param outsideTouable 点击外面是否消失弹出框
     */
    public PopuWindowsUtils(final Activity activity, float alpha, View anchorView,View contentView, boolean outsideTouable) {
        init(activity, alpha, anchorView,contentView, outsideTouable);
    }

    private void init(final Activity activity, float alpha,View anchorView, View contentView, boolean outsideTouable) {
        setAlpha(activity,alpha);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setContentView(contentView);
        setWidth(contentView.getMeasuredWidth());
        setHeight(contentView.getMeasuredHeight());
        setOutsideTouchable(outsideTouable);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(activity,1);
            }
        });
        //监听返回按钮
        setFocusable(true);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isShowing()) {
                        dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
        calculatePopWindowPos(anchorView, contentView);
    }

    //设置背景是否灰色
    public void setAlpha(Activity activity,float alpha){
        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
        lp.alpha=alpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 弹窗显示在view控件左上左外边
     * @param view
     * 显示效果
     *     {弹出view}
     *              参考view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_Top_ToLeft(View view){
        int width;
        if (isShowLeft){
            width = -getContentView().getMeasuredWidth();
        }else{
            width = view.getWidth();
        }

        int height;
        if (isShowUp){
            height= -view.getHeight()-getContentView().getMeasuredHeight();
        }else{
            height= 0;
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左上左内边对齐
     * @param view
     * 显示效果
     *          {弹出view}
     *          参考的view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_Top_AlignRight(View view){
        int width;
        if (isAlignLeft){
            width = 0;
        }else{
            width = -getContentView().getMeasuredWidth()+view.getWidth ();
        }

        int height;
        if (isShowUp){
            height= -view.getHeight()-getContentView().getMeasuredHeight();
        }else{
            height= 0;
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左上中间对齐
     * @param view
     * 显示效果
     *        {弹出的view}
     *          参考view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_Top_Middle(View view){
        int width = (view.getWidth()-getContentView().getMeasuredWidth())/2;
        int height;
        if (isShowUp){
            height= -view.getHeight()-getContentView().getMeasuredHeight();
        }else{
            height= 0;
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件右上右内边对齐
     * @param view
     * 显示效果
     *       {弹出的view}
     *          参考view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewRight_Top_AlignRight(View view){
        int width;
        if (isAlignRight){
            width = view.getWidth()-getContentView().getMeasuredWidth();
        }else{
            width = 0;
        }

        int height;
        if (isShowUp){
            height= -view.getHeight()-getContentView().getMeasuredHeight();
        }else{
            height= 0;
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件右上右外边对齐
     * @param view
     * 显示效果
     *                 {弹出的view}
     *          参考view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewRight_Top_ToRight(View view){
        int width;
        if (isShowRight){
            width = view.getWidth();
        }else{
            width = -getContentView().getMeasuredWidth();
        }

        int height;
        if (isShowUp){
            height= -view.getHeight()-getContentView().getMeasuredHeight();
        }else{
            height= 0;
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左边顶部对齐
     * @param view
     * 显示效果
     *          {弹出 参考的view
     *          view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_AlignTop(View view){
        int width;
        if (isShowLeft){
            width =-getContentView().getMeasuredWidth();
        }else{
            width = view.getWidth ();
        }

        int height;
        if (isAlignTop){
            height= -view.getHeight();
        }else{
            height= -getContentView ().getMeasuredHeight ();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左边中间对齐
     * @param view
     * 参考效果
     *          {弹出
     *          框的  参考view
     *          view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_Middle(View view){
        int width;
        if (isShowLeft){
            width =-getContentView().getMeasuredWidth();
        }else{
            width = view.getWidth ();
        }
        int height;
        if (heightMiddle) {
            height = -(view.getHeight () + getContentView ().getMeasuredHeight ()) / 2;
        }else{
            if (isShowUp){
                height = -getContentView ().getMeasuredHeight ();
            }else{
                height = -view.getHeight ();
            }
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左边底部对齐
     * @param view
     * 显示效果
     *          {弹出
     *          框的
     *          view} 参考的view
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_AlignBottom(View view){
        int width;
        if (isShowLeft){
            width =-getContentView().getMeasuredWidth();
        }else{
            width = view.getWidth ();
        }

        int height;
        if (isAlignBottom){
            height= -getContentView ().getMeasuredHeight ();
        }else{
            height= -view.getHeight();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件左边底部外边对齐
     * @param view
     * 显示效果
     *              参考的view
     *          {弹出
     *          框的
     *          view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewLeft_ToBottom(View view){
        int width;
        if (isShowLeft){
            width = -getContentView().getMeasuredWidth();
        }else{
            width = view.getWidth ();
        }

        int height;
        if (isShowDown){
            height= 0;
        }else{
            height= -getContentView ().getMeasuredHeight ()-view.getHeight();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件底部左内边对齐
     * @param view
     * 显示效果
     *          参考的view
     *          {弹出框的view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewBottom_AlignLeft(View view){
        int width;
        if (isAlignLeft){
            width = 0;
        }else{
            width = -getContentView ().getMeasuredWidth ()+view.getWidth ();
        }

        int height;
        if (isShowDown){
            height= 0;
        }else{
            height= -getContentView ().getMeasuredHeight ()-view.getHeight();
        }

        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件底部中间对齐
     * @param view
     * 显示效果
     *          参考view
     *       {弹出框的view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewBottom_Middle(View view){
        int height;
        if (isShowDown) {
            height = 0;
        }else{
            height = -(view.getHeight () + getContentView ().getMeasuredHeight ());
        }
        int width = (-getContentView().getMeasuredWidth()+view.getWidth())/2;
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件底部右边内对齐
     * @param view
     * 参考效果
     *          参考view
     *     {弹出框的view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewBottom_AlignRight(View view){
        int width;
        if (isAlignRight){
            width = view.getWidth()-getContentView().getMeasuredWidth();
        }else{
            width = 0;
        }
        int height;
        if (isShowDown){
            height = 0;
        }else{
            height = -view.getHeight ()-getContentView ().getMeasuredHeight ();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件底部右边外对齐
     * @param view
     * 参考效果
     *          参考view
     *                  {弹出框的view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewBottom_ToRight(View view){
        int width;
        if (isShowRight){
            width = view.getWidth();
        }else{
            width = -getContentView ().getMeasuredWidth ();
        }
        int height;
        if (isShowDown){
            height = 0;
        }else{
            height = -view.getHeight ()-getContentView ().getMeasuredHeight ();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 弹窗显示在view控件右边底部内对齐
     * @param view
     * 显示效果
     *                  {弹出
     *                  框的
     *        参考的view view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewRight_AlignBottom(View view){
        int width;
        if (isShowRight){
            width = view.getWidth();
        }else{
            width = -getContentView ().getMeasuredWidth ();
        }
        int height;
        if (isAlignBottom){
            height = -getContentView().getMeasuredHeight();
        }else{
            height = -view.getHeight ();
        }
        showAsDropDown(view,width,height);
    }


    /**
     * 弹窗显示在view控件右边中间对齐
     * @param view
     * 显示效果
     *                  {弹出
     *        参考的view 框的
     *                  view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewRight_Middle(View view){
        int width;
        if (isShowRight){
            width = view.getWidth();
        }else{
            width = -getContentView ().getMeasuredWidth ();
        }
        int height;
        if (heightMiddle){
            height = -(view.getHeight()+getContentView().getMeasuredHeight())/2;
        }else{
            if (isShowUp){
                height = -getContentView ().getMeasuredHeight ();
            }else{
                height = -view.getHeight ();
            }

        }
        showAsDropDown(view,width,height);
    }


    /**
     * 弹窗显示在view控件右边顶部内对齐
     * @param view
     * 显示效果
     *              参考view {弹出
     *                       框的
     *                       view}
     *
     * 有偏移;xoff表示x轴的偏移，负值表示向左，正值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
     */
    public void showViewRight_AlignTop(View view){
        int width;
        if (isShowRight){
            width = view.getWidth();
        }else{
            width = -getContentView ().getMeasuredWidth ();
        }

        int height;
        if (isAlignTop){
            height= -view.getHeight();
        }else{
            height= -getContentView ().getMeasuredHeight ();
        }
        showAsDropDown(view,width,height);
    }

    /**
     * 对齐activity的上下左右弹出对话框
     * @param parent 父布局
     * @param gravityOne 第一对齐方式 Gravity.LEFT,Gravity.TOP,Gravity.RIGHT,Gravity.BOTTOM
     * @param gravityTwo 第二对齐方式 Gravity.CENTER_VERTICAL,Gravity.CENTER_VERTICAL,Gravity.CENTER
     */
    public void showAtLocation(View parent,int gravityOne,int gravityTwo) {
        //super.showAtLocation(parent, gravity, x, y);
        showAtLocation(parent,gravityOne | gravityTwo, 0, 0);
    }

    //重新设置弹出框布局的宽
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    //重新设置弹出框布局的高
    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    private void calculatePopWindowPos(final View anchorView, final View contentView) {
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth ();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出
        final boolean isNeedShowUp = anchorLoc[1] > windowHeight;
        if (isNeedShowUp) {
            isShowUp = true;
        }else {
            isShowUp = false;
        }
        // 判断需要向下弹出显示
        final boolean isNeedShowDown = (screenHeight-anchorLoc[1]-anchorHeight) > windowHeight;
        if (isNeedShowDown) {
            isShowDown = true;
        }else{
            isShowDown = false;
        }

        // 判断需要向上顶部对齐弹出
        final boolean isNeedAlignUp = screenHeight-anchorLoc[1] > windowHeight;
        if (isNeedAlignUp) {
            isAlignTop = true;
        }else {
            isAlignTop = false;
        }
        // 判断需要向下顶部弹出显示
        final boolean isNeedAlignDown = (anchorLoc[1]+anchorHeight) > windowHeight;
        if (isNeedAlignDown) {
            isAlignBottom = true;
        }else{
            isAlignBottom = false;
        }

        // 判断需要向左弹出
        final boolean isNeedShowLeft = anchorLoc[0]> windowWidth;
        if (isNeedShowLeft) {
            isShowLeft = true;
        }else{
            isShowLeft = false;
        }

        // 判断需要向右弹出
        final boolean isNeedShowRight = (screenWidth - anchorLoc[0] - anchorWidth) > windowWidth;
        if (isNeedShowRight) {
            isShowRight = true;
        }else{
            isShowRight = false;
        }

        // 判断需要向左内边距弹出
        final boolean isNeedShowAlignLeft = (screenWidth - anchorLoc[0]) > windowWidth;
        if (isNeedShowAlignLeft) {
            isAlignLeft = true;
        }else{
            isAlignLeft = false;
        }

        // 判断需要向右内边距弹出
        final boolean isNeedShowAlignRight = (anchorLoc[0]+anchorWidth) > windowWidth;
        if (isNeedShowAlignRight) {
            isAlignRight = true;
        }else{
            isAlignRight = false;
        }

        // 判断是否高度居中弹出
        final boolean isNeedHeightMiddle = ((screenHeight-anchorLoc[1]) >= windowHeight/2)
                && anchorLoc[1]>=windowHeight/2;
        if (isNeedHeightMiddle) {
            heightMiddle = true;
        }else{
            heightMiddle = false;
        }
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
