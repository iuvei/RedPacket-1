package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebView;


/**
 * Created by Administrator on 2017/1/11.
 */

public class ProgressWebView extends WebView {

    private final static String TAG = ProgressWebView.class.getSimpleName();

    private ProgressBar progressbar;

    private boolean loadError=false;

    OnLoadListener mOnLoadListener;

    public interface OnLoadListener{
        void loadSuccess();
        void loadError();
    }

    public void setLoadListener(OnLoadListener mOnLoadListener){
        this.mOnLoadListener=mOnLoadListener;
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10, Gravity.TOP));
        ClipDrawable d = new ClipDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_orange_light)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressbar.setProgressDrawable(d);
        addView(progressbar);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        this.setWebViewClient(new WebViewClient());
        this.setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
            if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                loadError = true;
            }else{
                loadError = false;
            }
        }

    }

    public class WebViewClient extends com.tencent.smtt.sdk.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            Log.d(TAG,s);
            return super.shouldOverrideUrlLoading(webView, s);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            if(loadError){
                if(mOnLoadListener!=null)
                    mOnLoadListener.loadError();
            }else{
                if(mOnLoadListener!=null)
                    mOnLoadListener.loadSuccess();
            }

        }
        /**
         * 页面加载错误时执行的方法，但是在6.0以下，有时候会不执行这个方法
         * @param view
         * @param errorCode
         * @param description
         * @param failingUrl
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            loadError = true;
        }

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//        lp.x = l;
//        lp.y = t;
//        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }



}
