package me.jessyan.armscomponent.commonres.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 0
 * creat at 2019/9/22
 * description
 * 复制版工具类
 */
public class CopyUtil {

    private CopyUtil() {

    }

    public static CopyUtil getInstance(){
        return SingleCopyUtil.copyUtil1;
    }

    private static class SingleCopyUtil{
        private static CopyUtil copyUtil1 = new CopyUtil ();
    }

    public void copyString(Context context,String copyString){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService( Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyString);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
