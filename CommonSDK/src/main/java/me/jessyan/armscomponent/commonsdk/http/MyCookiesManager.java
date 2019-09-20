package me.jessyan.armscomponent.commonsdk.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Iterator;

public class MyCookiesManager {

    private static final String KEY_COOKIE = "cookie";
    public static final String FILE_NAME = "cookie_data";

    private static MyCookiesManager sMyCookiesManager;

    private MyCookiesManager() {
    }

    public static MyCookiesManager getInstance() {
        if (null == sMyCookiesManager) {

            synchronized(MyCookiesManager.class) {
                if (null == sMyCookiesManager) {
                    sMyCookiesManager = new MyCookiesManager();
                }
            }
        }
        return sMyCookiesManager;
    }

    public void saveCookies(Context context,  HashSet<String> cookies){
        SharedPreferences.Editor config = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        config.putStringSet(KEY_COOKIE,cookies);
        config.commit();
    }

    public void injectCookies(Context context,String url){
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        HashSet<String> cookies = (HashSet<String>) preferences.getStringSet(KEY_COOKIE, null);
        if(null != cookies){
            com.tencent.smtt.sdk.CookieManager cm=com.tencent.smtt.sdk.CookieManager.getInstance();
            cm.removeAllCookie();

            cm.setAcceptCookie(true);

            Iterator iterator = cookies.iterator();
            while (iterator.hasNext()){
                String cookie = (String) iterator.next();
                cm.setCookie(url,cookie);
            }
            if (Build.VERSION.SDK_INT < 21) {
                com.tencent.smtt.sdk.CookieSyncManager.getInstance().sync();
            } else {
                com.tencent.smtt.sdk. CookieManager.getInstance().flush();
            }
        }
    }
}
