package me.jessyan.armscomponent.commonres.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.File;
import java.util.List;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.ui.RecorderVideoActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.utils.MyFileUtils;

/**
 * Created by Administrator on 2017/11/4.
 */

public class ActionUtils {

    public static void copyToClipboard(Context context,String text){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtils.showShort("复制成功~");
    }

    public static void openBrowser(Context context,String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
    /**
     * 打开相机
     * @param mContext
     * @param imagePath
     * @return
     */
    public static void openCamera(FragmentActivity mContext, String imagePath){
        new RxPermissions(mContext)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 隐式调用
                        if ( null == photoIntent.resolveActivity(mContext.getPackageManager()) || null == imagePath )
                            return;
                        Uri uri = MyFileUtils.getUriForFile(new File(imagePath));
                        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                        mContext.startActivityForResult(photoIntent, Constants.REQUEST_CODE_CAMERA);
                    } else {
                        // Oups permission denied
                        ToastUtils.showShort("打开摄像头失败~~");
                    }
                });
    }

    /**
     * 打开录制
     */
    public static void openVideoRecorder(Activity mContext){
        new RxPermissions((FragmentActivity) mContext)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        mContext.startActivityForResult(new Intent(mContext, RecorderVideoActivity.class),Constants.REQUEST_CODE_VIDEO);
                    } else {
                        ToastUtils.showShort(mContext.getString(R.string.public_lack_of_permissions));
                    }
                });
    }

    /**
     * 打开位置纠正
     * @param mContext
     * @return
     */
    public static void openLocationCorrection(Activity mContext){
//        LocationCorrectionActivity.start(mContext);
    }

    /**
     * 相册 单选
     * @param mContext
     */
    public static void openSystemAblum(Activity mContext){
        new RxPermissions((FragmentActivity) mContext)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("image/*");//从所有图片中进行选择
                        mContext.startActivityForResult(intent, Constants.REQUEST_CODE_SYSTEM_ALBUM);
                    } else {
                        ToastUtils.showShort(mContext.getString(R.string.public_lack_of_permissions));
                    }
                });
    }

    /**
     * 打开相册 单选
     * @param mContext
     * @return
     */
    public static void openRadioAlbums(FragmentActivity mContext){
        new RxPermissions(mContext)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
//                        Matisse.from(mContext)
//                                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
//                                .showSingleMediaType(true)
//                                .capture(true)
//                                .captureStrategy(new CaptureStrategy(true , AppUtils.getAppPackageName()+".my.fileProvider"))
//                                .maxSelectable(1) // 图片选择的最多数量
//                                .gridExpectedSize(mContext.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f) // 缩略图的比例
//                                .theme(R.style.public_matisse)
//                                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
//                                .forResult(Constants.REQUEST_CODE_RADIO_ALBUM); // 设置作为标记的请求码
                    } else {
                        ToastUtils.showShort(mContext.getString(R.string.public_lack_of_permissions));
                    }
                });
    }

    /**
     * 打开相册 多选
     * @param mContext
     * @return
     */
    public static void openMultipleAlbums(FragmentActivity mContext){
        openMultipleAlbums(mContext,9);
    }

    public static void openMultipleAlbums(FragmentActivity mContext,int maxSelectable){
        new RxPermissions(mContext)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
//                        Matisse.from(mContext)
//                                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
//                                .showSingleMediaType(true)
//                                .capture(true)
//                                .captureStrategy(new CaptureStrategy(true , AppUtils.getAppPackageName()+".my.fileProvider"))
//                                .maxSelectable(maxSelectable) // 图片选择的最多数量
//                                .gridExpectedSize(mContext.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f) // 缩略图的比例
//                                .theme(R.style.public_matisse)
//                                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
//                                .forResult(Constants.REQUEST_CODE_MULTI_ALBUM); // 设置作为标记的请求码
                    } else {
                        ToastUtils.showShort(mContext.getString(R.string.public_lack_of_permissions));
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public static void call(Activity mContext, String modePhone){
        new RxPermissions((FragmentActivity) mContext)
                .request(Manifest.permission_group.PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + modePhone)));
                    } else {
                        ToastUtils.showShort(mContext.getString(R.string.public_lack_of_permissions));
                    }
                });
    }

    /**
     * @param activity
     *         当前activity
     * @param orgUri
     *         剪裁原图的Uri
     * @param desUri
     *         剪裁后的图片的Uri
     * @param aspectX
     *         X方向的比例
     * @param aspectY
     *         Y方向的比例
     * @param width
     *         剪裁图片的宽度
     * @param height
     *         剪裁图片高度
     */
    public static void cropImageUri(Activity activity, Uri orgUri,
                                    Uri desUri, int aspectX, int aspectY,
                                    int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        //大于7.0的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String scheme = orgUri.getScheme();
            if (scheme.equals("content")) {
                Uri contentUri = orgUri;

                intent.setDataAndType(contentUri, "image/*");
            } else {
                Uri contentUri = getImageContentUri(activity,orgUri);
                intent.setDataAndType(contentUri, "image/*");
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_CLIP);
    }

    /**
     * 转换 content:// uri
     */
    public static Uri getImageContentUri(Activity activity,Uri uri) {
        String filePath = uri.getPath();
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            return activity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    /**
     * 裁剪图片
     * @param mContext
     */
    public static void clipPhoto(Activity mContext, Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent,Constants.REQUEST_CODE_CLIP);
    }

    public static void openFile(Context context, String filePath){
        File file = new File(filePath);
        if(!file.exists()) return;
        Intent intent;
        /* 取得扩展名 */
        String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
                end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
            intent= getAudioFileIntent(file);
        }else if(end.equals("3gp")||end.equals("mp4")){
            intent= getVideoFileIntent(file);
        }else{
            String mimeType = null;
            switch (end){
                case "jpg":
                case "gif":
                case "png":
                case "jpeg":
                case "bmp":
                    mimeType = "image/*";
                    break;
                case "apk":
                    mimeType = "application/vnd.android.package-archive";
                    break;
                case "pdf":
                    mimeType = "application/pdf";
                    break;
                case "doc":
                    mimeType = "application/msword";
                    break;
                case "ppt":
                    mimeType = "application/vnd.ms-powerpoint";
                    break;
                case "xls":
                    mimeType = "application/vnd.ms-excel";
                    break;
                case "chm":
                    mimeType = "application/x-chm";
                    break;
                case "txt":
                    mimeType = "text/plain";
                    break;
                default:
                    mimeType = "*/*";
                    break;
            }
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = MyFileUtils.getUriForFile(file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri,mimeType);
        }
        context.startActivity(intent);
//        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(File file) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = MyFileUtils.getUriForFile(file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "video/mp4");
        return intent;
    }

    public static Intent getVideoUrlIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "video/mp4");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(File file){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri =MyFileUtils.getUriForFile(file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }


}
