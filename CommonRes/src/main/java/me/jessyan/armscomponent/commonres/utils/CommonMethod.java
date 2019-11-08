package me.jessyan.armscomponent.commonres.utils;

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
    //游戏客服
    public final static String GAME_SERVICE = "http://p.qiao.baidu.com/cps/chat?siteId=14103450&userId=29211734&cp=225g.cn&cr=225g.cn&cw=225g.cn";
    //充值客服
    public final static String RECHARGE_SERVICE = "http://p.qiao.baidu.com/cps/chat?siteId=14103461&userId=29218530&cp=225g.cn&cr=225g.cn&cw=225g.cn";
    //平台客服
    public final static String PLATFORM_SERVICE = "http://p.qiao.baidu.com/cps/chat?siteId=14041512&userId=29199082&cp=225g.cn&cr=225g.cn&cw=225g.cn";

    //从本地获取hxid
    public static String getHxidForLocal(){
        MMKV kv = MMKV.defaultMMKV();
        return kv.getString ( "hxid","" );
    }
    //保存环信id到本地
    public static void setHxidForLocal(String hxid){
        MMKV kv = MMKV.defaultMMKV();
        kv.encode ( "hxid", hxid );
    }
    //从本地获取昵称
    public static String getNickNameForLocal(){
        MMKV kv = MMKV.defaultMMKV();
        return kv.getString ( "nickname", "" );
    }
    //保存昵称到本地
    public static void setNickNameForLocal(String nickname){
        MMKV kv = MMKV.defaultMMKV();
        kv.encode ( "nickname", nickname);
    }

    //从本地获取uid
    public static String getUidForLocal(){
        MMKV kv = MMKV.defaultMMKV();
        return kv.getString ( "uid", "" );
    }
    //保存uid到本地
    public static void setUidForLocal(String uid){
        MMKV kv = MMKV.defaultMMKV();
        kv.encode ( "uid", uid);
    }

    //判断hx用户消息是否免打扰
    public static boolean isNotTroubleFromHxid(String hxid){
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeBool ( hxid+"isNotify" );
    }

    //设置hx用户消息是否免打扰
    public static boolean setNotTroubleFromHxid(String hxid, boolean isNotify){
        MMKV kv = MMKV.defaultMMKV();
        return kv.encode ( hxid+"isNotify",isNotify );
    }

    //新消息大佬是否打开震动
    public static boolean isVibrateFromLocal(){
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeBool ( "vibrate" );
    }

    //新消息是否打开震动保存到本地
    public static void setVibrateToLocal(boolean isVibrate){
        MMKV kv = MMKV.defaultMMKV();
        kv.encode ( "vibrate",isVibrate );
    }

    //新消息大佬是否打开声音
    public static boolean isVoiceFromLocal(){
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeBool ( "voice",true );
    }

    //新消息是否打开声音保存到本地
    public static void setVoiceToLocal(boolean isVibrate){
        MMKV kv = MMKV.defaultMMKV();
        kv.encode ( "voice",isVibrate );
    }
}
