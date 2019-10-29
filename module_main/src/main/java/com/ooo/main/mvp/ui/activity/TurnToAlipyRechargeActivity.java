package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseConstant;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.di.component.DaggerTurnToAlipyRechargeComponent;
import com.ooo.main.mvp.contract.TurnToAlipyRechargeContract;
import com.ooo.main.mvp.model.entity.GetRechargeInfoBean;
import com.ooo.main.mvp.presenter.TurnToAlipyRechargePresenter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ConfigUtil;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.CopyUtil;
import me.jessyan.armscomponent.commonres.utils.FileUtil;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TurnToAlipyRechargeActivity extends BaseActivity <TurnToAlipyRechargePresenter> implements TurnToAlipyRechargeContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.iv_uploadPic)
    ImageView ivUploadPic;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.btn_submit)
    Button btnSubmit;
    private String picUrl;
    private List <GetRechargeInfoBean.ResultBean> recharge;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTurnToAlipyRechargeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_turn_to_alipy_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "充值中心" );
        tvRight.setText ( "客服" );
        tvRight.setVisibility ( View.VISIBLE );
        String money = getIntent ().getStringExtra ( "money" );
        tvMoney.setText ( (ConvertNumUtils.stringToDouble ( money ) + (int) (Math.random () * 100) / 100.0) + "" );
        mPresenter.onlinePayInfo ( "2" );
    }

    public static void start(Context context, String money) {
        Intent intent = new Intent ( context, TurnToAlipyRechargeActivity.class );
        intent.putExtra ( "money", money );
        context.startActivity ( intent );
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {
        finish ();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.btn_copy_money, R2.id.btn_cancel, R2.id.btn_submit, R2.id.iv_uploadPic})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
            //客服
            Bundle bundle = new Bundle ();
            bundle.putString ( "userId", ConfigUtil.SERVICE_HOMEPAGE );
            bundle.putInt ( "chatType", EaseConstant.CHATTYPE_SINGLE );
            bundle.putSerializable ( "isService", true );
            ARouter.getInstance ().build ( RouterHub.IM_CHATACTIVITY ).with ( bundle ).navigation ();
        } else if (i == R.id.btn_copy_money) {
            CopyUtil.getInstance ().copyString ( this, tvMoney.getText ().toString ().trim () );
        } else if (i == R.id.btn_cancel) {
            finish ();
        } else if (i == R.id.btn_submit) {
            String payMoney = tvMoney.getText ().toString ().trim ();
//            if (TextUtils.isEmpty ( picUrl )) {
//                ToastUtils.showShort ( "请先上传充值凭证" );
//                return;
//            }
            if (recharge == null) {
                ToastUtils.showShort ( "暂无充值信息" );
                return;
            }
            mPresenter.submitRechargeInfo ( AppLifecyclesImpl.getUserinfo ().getAccount ()+"",
                    recharge.get ( 0 ).getPaycode () ,"2", payMoney, recharge.get ( 0 ).getPayname (), picUrl );
            btnSubmit.setEnabled ( false );
        } else if (i == R.id.iv_uploadPic) {
            //上传图片
            showChoosePic ( view );
        }
    }

    public void showChoosePic(View view) {
        //保存图片
        View contentView = View.inflate ( this, com.haisheng.easeim.R.layout.popuwindow_choose_pictype, null );
        PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( this, 0.7f, ivUploadPic, contentView, true );
        popuWindowsUtils.showAtLocation ( view.getRootView (), Gravity.CENTER, Gravity.BOTTOM );
        contentView.findViewById ( com.haisheng.easeim.R.id.tv_takePhoto ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //拍照
                Uri uri;
                File file = new File ( Environment
                        .getExternalStorageDirectory (),
                        "xiaoma.jpg" );
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(TurnToAlipyRechargeActivity.this,"com.ooo.redpacketlan.provider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Intent intentCamera = new Intent (
                        MediaStore.ACTION_IMAGE_CAPTURE );
                intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //将拍照结果保存至photo_file的Uri中，不保留在相册中

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult ( intentCamera, 2 );
            }
        } );
        contentView.findViewById ( com.haisheng.easeim.R.id.tv_take_image ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //相册
                Intent intent = new Intent ( Intent.ACTION_PICK, null );
                intent.setDataAndType (
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*" );
                startActivityForResult ( intent, 1 );
            }
        } );
        contentView.findViewById ( com.haisheng.easeim.R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //取消
            }
        } );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case 1:
                    startPhotoZoom ( data.getData () );
                    break;
                // 如果是调用相机拍照时
                case 2:
                    File temp = new File ( Environment.getExternalStorageDirectory ()
                            + "/xiaoma.jpg" );
                    startPhotoZoom ( Uri.fromFile ( temp ) );
                    break;
                // 取得裁剪后的图片
                case 3:
                    if (data != null) {
                        setPicToView ( data );
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult ( requestCode, resultCode, data );
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent ( "com.android.camera.action.CROP" );
        intent.setDataAndType ( uri, "image/*" );
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra ( "crop", "true" );
        // aspectX aspectY 是宽高的比例
        intent.putExtra ( "aspectX", 1 );
        intent.putExtra ( "aspectY", 1 );
        // outputX outputY 是裁剪图片宽高
        intent.putExtra ( "outputX", 150 );
        intent.putExtra ( "outputY", 150 );
        intent.putExtra ( "return-data", true );
        startActivityForResult ( intent, 3 );
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras ();
        if (extras != null) {
            Bitmap photo = extras.getParcelable ( "data" );
            //图片路径
            String urlpath = FileUtil.saveFile ( this, "temphead.jpg", photo );
            mPresenter.upLoadPic ( urlpath );
            System.out.println ( "----------路径----------" + urlpath );
        }
    }

    @Override
    public void uploadImgSuccessfully(String picUrl) {
        this.picUrl = picUrl;
        Glide.with ( this ).load ( picUrl ).into ( ivUploadPic );
    }

    @Override
    public void submitRechargeInfoFail() {
        btnSubmit.setEnabled ( true );
    }

    @Override
    public void submitRechargeInfoSuccess(String result) {
        ToastUtils.showShort ( result );
        finish ();
    }

    @Override
    public void getRechargeInfoFail() {

    }

    @Override
    public void getRechargeInfoSuccess(List <GetRechargeInfoBean.ResultBean> result) {
        if (result != null && result.size () > 0) {
            recharge = result;
            Glide.with ( this ).load ( result.get ( 0 ).getPayurl () ).into ( ivQrcode );
        } else {
            ToastUtils.showShort ( "暂无充值信息" );
        }
    }
}
