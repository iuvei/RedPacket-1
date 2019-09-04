package me.jessyan.armscomponent.commonsdk.http.interceptor;

import java.io.IOException;

import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/2/2.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain.request().url().toString().contains("login")
                || chain.request().url().toString().contains(" verifycode")) {
            return chain.proceed(chain.request());
        }
//        String token = (String) SpUtils.get(Utils.getApp(),"token", "");
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        Timber.i("App-token : %s", token);
        Request request = chain.request()
                .newBuilder()
                .addHeader("token",
                        "".equals(token) ?
                                "" : token)
                .build();

        return chain.proceed(request);
    }
}
