package me.jessyan.armscomponent.commonres.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.view.ProgressWebView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class WebviewActivity extends BaseSupportActivity {

    ProgressWebView webView;
    View errorPage;

    private String mTitle;
    private String mUrl;

    public static void start(Activity context, String title,String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_base_webview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title");

        webView = findViewById(R.id.webView);
        errorPage = findViewById(R.id.view_reset_error_page);

        setTitle(mTitle);

        webView.addJavascriptInterface(new AndroidtoJs(),"app");
        errorPage.setOnClickListener(v -> {
            webView.loadUrl(mUrl);
            errorPage.setVisibility(View.GONE);
        });

        webView.setLoadListener(new ProgressWebView.OnLoadListener() {
            @Override
            public void loadSuccess() {
                LogUtils.i(TAG+":"+webView.getTitle()+"~"+getString(R.string.public_load_success));
            }

            @Override
            public void loadError() {
                errorPage.setVisibility(View.VISIBLE);
            }
        });

        com.tencent.smtt.sdk.CookieManager cm=com.tencent.smtt.sdk.CookieManager.getInstance();
        cm.setAcceptCookie(true);
        cm.setCookie(mUrl,"__cookie_sz_yi_userid_1=dWFhMGU1NjNlMDkzMDVjNTNhYTRiYWQzMGI4MDhkOGY3");
        if (Build.VERSION.SDK_INT < 21) {
            com.tencent.smtt.sdk.CookieSyncManager.getInstance().sync();
        } else {
            com.tencent.smtt.sdk.CookieManager.getInstance().flush();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new AndroidtoJs(),"app");
        webView.loadUrl(mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public void onBackPressedSupport() {
        if(null != webView &&webView.canGoBack()){
            webView.goBack();
            return;
        }
        super.onBackPressedSupport();
    }

    class AndroidtoJs extends  Object{

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void goBack() {
            System.out.println("JS调用了Android的hello方法");
            if(null != webView && webView.canGoBack()){
                webView.goBack();
                return;
            }
            onBackPressedSupport();
        }
    }
}
