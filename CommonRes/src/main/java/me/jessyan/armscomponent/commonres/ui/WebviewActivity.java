package me.jessyan.armscomponent.commonres.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.di.component.AppComponent;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.view.ProgressWebView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

public class WebviewActivity extends BaseSupportActivity {

    ProgressWebView webView;
    View errorPage;
    Toolbar publicToolbar;

    private String mTitle;
    private boolean mIsTransparentToolbar;
    private String mUrl;

    public static void start(Context context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, boolean isTransparentToolbar, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("isTransparentToolbar", isTransparentToolbar);
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        Bundle bundle = getIntent().getExtras();
//        if(null!=bundle){
//            mIsTransparentToolbar = bundle.getBoolean("isTransparentToolbar", false);
//        }
//        if(mIsTransparentToolbar){
//            return R.layout.activity_webview1;
//        }else{
//            return R.layout.activity_webview;
//        }
        return R.layout.activity_webview;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus(this);
        StatusBarUtils.setStatusBarDarkTheme ( this,true );
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title");
        mIsTransparentToolbar = bundle.getBoolean("isTransparentToolbar", false);

        publicToolbar = findViewById(R.id.public_toolbar);
        webView = findViewById(R.id.webView);
        errorPage = findViewById(R.id.view_reset_error_page);
        publicToolbar.setVisibility(mIsTransparentToolbar ? View.GONE : View.VISIBLE);
        setTitle(mTitle);

//        webView.addJavascriptInterface(new AndroidtoJs(), "app");
        errorPage.setOnClickListener(v -> {
            webView.loadUrl(mUrl);
            errorPage.setVisibility(View.GONE);
        });

        webView.setLoadListener(new ProgressWebView.OnLoadListener() {
            @Override
            public void loadSuccess() {
                LogUtils.i(TAG + ":" + webView.getTitle() + "~" + getString(R.string.public_load_success));
            }
            @Override
            public void loadError() {
                errorPage.setVisibility(View.VISIBLE);
            }
        });

        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        if (!TextUtils.isEmpty ( token )) {
            cm.setCookie ( mUrl, "token=" + token );
        }
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(mUrl);
        webView.addJavascriptInterface(new AndroidtoJs(), "app");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (null != webView && webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressedSupport();
    }

    class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void goBack() {
            System.out.println("JS调用了Android的goBack方法");
            if (null != webView && webView.canGoBack()) {
                webView.goBack();
                return;
            }
            onBackPressedSupport();
        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void goLogin() {
            System.out.println("JS调用了Android的goLogin方法");
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.ACCOUT_TOKEN_OVERDUE,true);
            ARouter.getInstance()
                    .build(RouterHub.APP_MAINACTIVITY)
                    .with( bundle)  //参数：键：key 值：123
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    .navigation();
            finish();
        }
    }
}
