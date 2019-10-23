package me.jessyan.armscomponent.commonres.utils;

import android.content.Context;

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


    //从本地获取昵称
    public static String getNickNameForLocal(Context context){
        return SpUtils.getValue ( context,"nickname", "" );
    }
}
