package me.jessyan.armscomponent.commonres.utils;

import android.app.Application;
import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * creat at 2019/10/23
 * description
 * 公共方法
 */
public class CommonMethod {

//     SpUtils.put ( this,"username",etPhone.getText ().toString ().trim () );
//            SpUtils.put ( this,"password",etPassword.getText ().toString ().trim () );
//            SpUtils.put ( this,"nickname", AppLifecyclesImpl.getUserinfo ().getNickname () );
//            SpUtils.put ( this,"uid", uid+"" );
//            SpUtils.put ( this,"hxid", id );

    //首页客服
    public final static String HOME_PAGE_SERVICE = "http://p.qiao.baidu.com/cps/chat?siteId=14041512&userId=29199082";
    //从本地获取昵称
    public static String getNickNameForLocal(Context context){
        return SpUtils.getValue ( context,"nickname", "" );
    }

    //判断hx用户消息是否免打扰
    public static boolean isNotifyFromHxid(String hxid){
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeBool ( hxid+"isNotify" );
    }

    //设置hx用户消息是否免打扰
    public static boolean setNotifyFromHxid(String hxid,boolean isNotify){
        MMKV kv = MMKV.defaultMMKV();
        return kv.encode ( hxid+"isNotify",isNotify );
    }
}
