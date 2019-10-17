package com.haisheng.easeim.mvp.utils;

import android.text.TextUtils;

import me.jessyan.armscomponent.commonres.utils.ConfigUtil;

/**
 * creat at 2019/10/17
 * description
 * 公共工具类
 */
public class CommontUtil {

    //是不是客服
    public static boolean isCustomer(String hxID){
        if (TextUtils.isEmpty ( hxID )){
            return false;
        }
        if (hxID.equals ( ConfigUtil.SERVICE_GAME_CONTROL_INSIDE )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_NIUNIU_ROME)
                || hxID.equals ( ConfigUtil.SERVICE_HOMEPAGE )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_NIUNIU_INSIDE )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_SAOLEI_ROOM )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_SAOLEI_INSIDE )
                || hxID.equals ( ConfigUtil.SERVICE_MYPAGE )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_CONTROL_ROOM )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_FULI_ROOM )
                || hxID.equals ( ConfigUtil.SERVICE_GAME_FULI_INSIDE )){
            return true;
        }
        return false;
    }
}
