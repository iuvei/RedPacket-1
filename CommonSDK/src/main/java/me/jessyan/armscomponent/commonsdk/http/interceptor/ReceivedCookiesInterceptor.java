package me.jessyan.armscomponent.commonsdk.http.interceptor;

import com.blankj.utilcode.util.Utils;

import java.io.IOException;
import java.util.HashSet;

import me.jessyan.armscomponent.commonsdk.http.MyCookiesManager;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()){
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            MyCookiesManager.getInstance().saveCookies(Utils.getApp(),cookies);
        }
        return originalResponse;
    }
}
