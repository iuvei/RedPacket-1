package com.haisheng.easeim.mvp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.hyphenate.util.PathUtil;

import org.openjdk.tools.javac.code.Attribute;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class ImageUtils {

    /**
     * 左下角添加水印（多行，图标 + 文字）
     * 参考资料：
     * Android 对Canvas的translate方法总结    https://blog.csdn.net/u013681739/article/details/49588549
     * @param photo
     */
    public static Bitmap addWaterMark(Context context, Bitmap photo, List<String> textList, List<Integer> iconIdList, boolean isShowIcon) {
        Bitmap newBitmap = photo;
        try {
            if (null == photo) {
                return null;
            }

            int srcWidth = photo.getWidth();
            int srcHeight = photo.getHeight();

            //Resources resources = context.getResources();
            //float scale = resources.getDisplayMetrics().density;
            int unitHeight = srcHeight > srcWidth ? srcWidth/30 : srcHeight / 25;
            int padding = unitHeight;
            int marginLeft = padding;
            int marginBottom = padding;
            int textSize = unitHeight;

            //创建一个bitmap
            if (!newBitmap.isMutable()) {
                newBitmap = copy(photo);
            }
            //将该图片作为画布
            Canvas canvas = new Canvas(newBitmap);

            // 设置画笔
            TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            textPaint.setTextSize(textSize);// 字体大小
            textPaint.setTypeface(Typeface.DEFAULT);// 采用默认的宽度
            textPaint.setColor(Color.WHITE);// 采用的颜色v

            Rect bounds = new Rect();
            String gText = "hello world!";
            textPaint.getTextBounds(gText, 0, gText.length(), bounds);

            int iconWidth = bounds.height();//图片宽度
            int maxTextWidth = srcWidth - padding*3 - iconWidth;//最大文字宽度

            for (int i = textList.size() - 1 ; i >=0 ; i--){
                String text = textList.get(i);
                int iconId = iconIdList.get(i);

                canvas.save();//锁画布(为了保存之前的画布状态)

                //文字处理
                StaticLayout layout = new StaticLayout(text, textPaint, maxTextWidth, Layout.Alignment.ALIGN_NORMAL,
                        1.0f, 0.0f, true); // 确定换行
                //在画布上绘制水印图片
                if (isShowIcon){
                    Bitmap watermark= BitmapFactory.decodeResource(context.getResources(),iconId);
                    int iconHeight = iconWidth * ((watermark.getHeight()*1000)/watermark.getWidth())/1000;//维持图片宽高比例，也可以简单粗暴 iconHeight = iconWidth;
                    //图片相对文字位置居中
                    RectF rectF=new RectF(marginLeft,srcHeight - marginBottom - layout.getHeight()/2 - iconHeight/2
                            ,marginLeft + iconWidth,srcHeight - marginBottom - layout.getHeight()/2 + iconHeight/2);
                    canvas.drawBitmap(watermark,null,rectF,null);//限定图片显示范围
                }

                //绘制文字
                canvas.translate(isShowIcon ? marginLeft + iconWidth + padding : marginLeft, srcHeight - layout.getHeight() - marginBottom); // 设定画布位置
                layout.draw(canvas); // 绘制水印

                //marginBottom 更新
                marginBottom = marginBottom + (padding + layout.getHeight());

                canvas.restore();//把当前画布返回（调整）到上一个save()状态之前
            }

            // 保存
//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            // 存储
            canvas.restore();

        } catch (Exception e) {
            e.printStackTrace();
            return newBitmap;
        }
        return newBitmap;
    }

    /**
     * 根据原位图生成一个新的位图，并将原位图所占空间释放
     *
     * @param srcBmp 原位图
     * @return 新位图
     */
    public static Bitmap copy(Bitmap srcBmp) {
        Bitmap destBmp = null;
        try {
            // 创建一个临时文件
            File file = new File(PathUtil.getInstance().getImagePath(), "temppic/tmp.txt");
            if (file.exists()) {// 临时文件 ， 用一次删一次
                file.delete();
            }
            file.getParentFile().mkdirs();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            int width = srcBmp.getWidth();
            int height = srcBmp.getHeight();
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, width * height * 4);
            // 将位图信息写进buffer
            srcBmp.copyPixelsToBuffer(map);
            // 释放原位图占用的空间
            srcBmp.recycle();
            // 创建一个新的位图
            destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            map.position(0);
            // 从临时缓冲中拷贝位图信息
            destBmp.copyPixelsFromBuffer(map);
            channel.close();
            randomAccessFile.close();
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
            destBmp = null;
            return srcBmp;
        }
        return destBmp;
    }


}
