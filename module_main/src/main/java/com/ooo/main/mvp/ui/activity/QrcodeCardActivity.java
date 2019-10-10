package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.model.entity.MemberInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.utils.QRUtils;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

@Route(path = RouterHub.QR_ACTIVITY)
public class QrcodeCardActivity extends BaseSupportActivity {


    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.iv_sex)
    ImageView ivSex;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.tv_accountnum)
    TextView tvAccountnum;
    private Bitmap qrCode;


    public static void start(Context context, MemberInfo memberInfo) {
        Intent intent = new Intent ( context, QrcodeCardActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "memberInfo", memberInfo );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_qrcode_card;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "二维码" );
        ivRight.setVisibility ( View.VISIBLE );
        ivRight.setImageResource ( R.mipmap.message_icon_more );

        Glide.with ( this )
                .load ( AppLifecyclesImpl.getUserinfo ().getAvatarUrl () )
                .into ( ivAvatar );
        tvUsername.setText ( AppLifecyclesImpl.getUserinfo ().getNickname () );
        tvAccountnum.setText ( AppLifecyclesImpl.getUserinfo ().getAccount ()+"" );
        int sexStatusResId = AppLifecyclesImpl.getUserinfo ().getGender () == MemberInfo.FAMALE ? R.drawable.ic_female : R.drawable.ic_male;
        ivSex.setImageResource ( sexStatusResId );
        qrCode = QRUtils.getInstance().createQRCode( AppLifecyclesImpl.getUserinfo ().getAccount ()+"" );
        Glide.with ( this )
                .load ( qrCode )
                .into ( ivQrcode );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
        ARouter.getInstance ().inject ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.iv_right})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_right) {
            //保存图片
            View contentView = View.inflate ( this,R.layout.popupwindow_save_qr,null );
            PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( this,1,ivRight,contentView,true );
            popuWindowsUtils.showViewBottom_AlignRight(ivRight);
            contentView.findViewById ( R.id.ll_save_qr ).setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    popuWindowsUtils.dismiss ();
                    saveToSystemGallery(QrcodeCardActivity.this,qrCode);
                }
            } );
        }
    }


    public void saveToSystemGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File( Environment.getExternalStorageDirectory(), "vgmap");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showShort("保存失败");
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.showShort("保存失败");
        }
        ToastUtils.showShort ( "图片已保存到"+appDir+"目录下" );
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}
