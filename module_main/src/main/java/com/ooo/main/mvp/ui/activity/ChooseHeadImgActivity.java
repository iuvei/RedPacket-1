package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerChooseHeadImgComponent;
import com.ooo.main.mvp.contract.ChooseHeadImgContract;
import com.ooo.main.mvp.presenter.ChooseHeadImgPresenter;
import com.ooo.main.mvp.ui.adapter.ChooseHeadImgAdapter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.FileUtil;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChooseHeadImgActivity extends BaseSupportActivity <ChooseHeadImgPresenter>
        implements ChooseHeadImgContract.View{

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recyclerView_icon)
    RecyclerView recyclerViewIcon;
    @BindView(R2.id.tv_takephoto)
    TextView tvTakephoto;
    @BindView(R2.id.tv_choosephoto)
    TextView tvChoosephoto;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChooseHeadImgComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose_head_img; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "选择头像" );
        int[] imgs = {R.mipmap.icon_select_avatar_first,R.mipmap.avatar_default_01,R.mipmap.avatar_default_02,
                R.mipmap.avatar_default_03,R.mipmap.avatar_default_04,R.mipmap.avatar_default_05,
                R.mipmap.avatar_default_06,R.mipmap.avatar_default_07,R.mipmap.avatar_default_08,
                R.mipmap.avatar_default_09,R.mipmap.avatar_default_10,R.mipmap.avatar_default_11,
                R.mipmap.avatar_default_12,R.mipmap.avatar_default_13,R.mipmap.avatar_default_14,
                R.mipmap.avatar_default_15,R.mipmap.avatar_default_16,R.mipmap.avatar_default_17,
                R.mipmap.avatar_default_18,R.mipmap.avatar_default_19,R.mipmap.avatar_default_20,
                R.mipmap.avatar_default_21,R.mipmap.avatar_default_22,R.mipmap.avatar_default_23,
                R.mipmap.avatar_default_24,R.mipmap.avatar_default_25,R.mipmap.avatar_default_26,
                R.mipmap.avatar_default_27,R.mipmap.avatar_default_28,R.mipmap.avatar_default_29,
                R.mipmap.avatar_default_30,R.mipmap.avatar_default_31,R.mipmap.avatar_default_32,
                R.mipmap.avatar_default_33,R.mipmap.avatar_default_34,R.mipmap.avatar_default_35,
                R.mipmap.avatar_default_36,R.mipmap.avatar_default_37,R.mipmap.avatar_default_38,
                R.mipmap.avatar_default_39,R.mipmap.avatar_default_40,R.mipmap.avatar_default_41,
                R.mipmap.avatar_default_42,R.mipmap.avatar_default_43};
        ChooseHeadImgAdapter recycleAdapter = new ChooseHeadImgAdapter ( this, imgs);
        GridLayoutManager gridManager = new GridLayoutManager (this,4 );
        //设置布局管理器
        recyclerViewIcon.setLayoutManager(gridManager);
        //设置为垂直布局，这也是默认的
        gridManager.setOrientation( OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerViewIcon.setAdapter(recycleAdapter);
        recycleAdapter.setItemClickListener ( new ChooseHeadImgAdapter.ItemClickListener () {
            @Override
            public void onItemClick(int[] data, int position) {
                if (position==0){
                    //拍照
                    Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    "xiaoma.jpg")));
                    startActivityForResult(intent, 2);
                }else {
                    //选中头像
                    int imgRes = data[position];

                }
            }
        } );
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @OnClick({R2.id.iv_back, R2.id.tv_takephoto, R2.id.tv_choosephoto, R2.id.tv_cancel})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        }  else if (i == R.id.tv_takephoto) {
            //拍照
            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(),
                            "xiaoma.jpg")));
            startActivityForResult(intent, 2);
        } else if (i == R.id.tv_choosephoto) {
            //从相册选择
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(intent, 1);
        } else if (i == R.id.tv_cancel) {
            //取消
            finish ();
        }
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
            System.out.println("----------路径----------" + urlpath);
        }
    }

}
