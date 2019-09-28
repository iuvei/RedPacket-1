package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerTurnToYinLianRechargeComponent;
import com.ooo.main.mvp.contract.TurnToYinLianRechargeContract;
import com.ooo.main.mvp.presenter.TurnToYinLianRechargePresenter;
import com.ooo.main.mvp.ui.adapter.ChooseBlankAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.CopyUtil;
import me.jessyan.armscomponent.commonres.utils.FileUtil;
import me.jessyan.armscomponent.commonres.utils.PopuWindowsUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TurnToYinLianRechargeActivity extends BaseActivity <TurnToYinLianRechargePresenter> implements TurnToYinLianRechargeContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.sp_blank)
    Spinner spBlank;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_card_num)
    TextView tvCardNum;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_marke)
    TextView tvMarke;
    @BindView(R2.id.tv_payName)
    TextView tvPayName;
    @BindView(R2.id.iv_uploadPic)
    ImageView ivUploadPic;
    private String picUrl;
    private ChooseBlankAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTurnToYinLianRechargeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_turn_to_yin_lian_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "充值中心" );
        tvRight.setText ( "客服" );
        tvRight.setVisibility ( View.VISIBLE );
        String money = getIntent ().getStringExtra ( "money" );
        String name = getIntent ().getStringExtra ( "name" );
        tvMoney.setText ( (ConvertNumUtils.stringToDouble ( money )+(int)(Math.random ()*100)/100.0)+"" );
        tvPayName.setText ( name );
        mPresenter.getRechargeInfo ( money, "" );
        List<String> spinnerItems = new ArrayList <> (  );
        spinnerItems.add ( "中国银行" );
        spinnerItems.add ( "中国银行" );
        spinnerItems.add ( "中国银行" );
        spinnerItems.add ( "中国银行" );
        adapter = new ChooseBlankAdapter ( spinnerItems );
        spBlank.setAdapter ( adapter );
        setListener();
    }

    private void setListener() {
       spBlank.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
           @Override
           public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {

           }

           @Override
           public void onNothingSelected(AdapterView <?> adapterView) {

           }
       } );
    }

    public static void start(Context context, String money, String name) {
        Intent intent = new Intent ( context, TurnToYinLianRechargeActivity.class );
        intent.putExtra ( "money", money );
        intent.putExtra ( "name", name );
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

    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.btn_copy_name, R2.id.btn_copy_card_num,
            R2.id.btn_copy_money, R2.id.btn_copy_marke, R2.id.btn_cancel, R2.id.btn_submit,R2.id.iv_uploadPic})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
        } else if (i == R.id.btn_copy_name) {
            CopyUtil.getInstance ().copyString ( this, tvName.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_card_num) {
            CopyUtil.getInstance ().copyString ( this, tvCardNum.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_money) {
            CopyUtil.getInstance ().copyString ( this, tvMoney.getText ().toString ().trim () );
        } else if (i == R.id.btn_copy_marke) {
            CopyUtil.getInstance ().copyString ( this, tvMarke.getText ().toString ().trim () );
        } else if (i == R.id.btn_cancel) {
            finish ();
        } else if (i == R.id.btn_submit) {
            String payCodeId = "";
            String payMoney = tvMoney.getText ().toString ().trim ();
            String payName = "";
            String payImg = "";
            mPresenter.submitRechargeInfo ( payCodeId, payMoney, payName, payImg );
        }else if (i == R.id.iv_uploadPic) {
            //上传凭证
            showChoosePic(view);
        }
    }
    public void showChoosePic(View view){
        //保存图片
        View contentView = View.inflate ( this, com.haisheng.easeim.R.layout.popuwindow_choose_pictype,null );
        PopuWindowsUtils popuWindowsUtils = new PopuWindowsUtils ( this,0.7f,ivUploadPic,contentView,true );
        popuWindowsUtils.showAtLocation(view.getRootView (), Gravity.CENTER,Gravity.BOTTOM );
        contentView.findViewById ( com.haisheng.easeim.R.id.tv_takePhoto ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //拍照
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File ( Environment
                                .getExternalStorageDirectory(),
                                "xiaoma.jpg")));
                startActivityForResult(intent, 2);
            }
        } );
        contentView.findViewById ( com.haisheng.easeim.R.id.tv_take_image ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                popuWindowsUtils.dismiss ();
                //相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 1);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case 1:
                    startPhotoZoom(data.getData());
                    break;
                // 如果是调用相机拍照时
                case 2:
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/xiaoma.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                // 取得裁剪后的图片
                case 3:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //图片路径
            String urlpath = FileUtil.saveFile ( this, "temphead.jpg", photo );
            mPresenter.upLoadPic ( urlpath );
            System.out.println("----------路径----------" + urlpath);
        }
    }

    @Override
    public void uploadImgSuccessfully(String picUrl) {
        this.picUrl = picUrl;
        Glide.with ( this ).load ( picUrl ).into ( ivUploadPic );
    }


}
