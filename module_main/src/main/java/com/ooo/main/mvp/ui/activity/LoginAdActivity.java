package com.ooo.main.mvp.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

@Route( path = RouterHub.MAIN_LOGINADACTIVITY)
public class LoginAdActivity extends BaseSupportActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_ad;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //隐藏标题栏
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        super.onCreate ( savedInstanceState );
        //设置为true点击区域外消失，true为消失，false为不消失
        setFinishOnTouchOutside(false);

    }

    @OnClick({R2.id.tv_go_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_go_login) {
            Bundle bundle = new Bundle (  );
            bundle.putBoolean ( "logout",true );
            openActivity(LoginActivity.class,bundle);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return isCosumenBackKey();
        }
        return false;
    }

    private boolean isCosumenBackKey() {
        // 这儿做返回键的控制，如果自己处理返回键逻辑就返回true，如果返回false,代表继续向下传递back事件，由系统去控制
        return true;
    }
}
