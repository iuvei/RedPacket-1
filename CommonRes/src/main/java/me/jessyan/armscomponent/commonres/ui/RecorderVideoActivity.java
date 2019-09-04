package me.jessyan.armscomponent.commonres.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.utils.video.Utils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class RecorderVideoActivity extends BaseSupportActivity implements IView {

    private VideoView videoView;
    private ImageButton btnSwitchCamera;
    private Chronometer chronometer;
    private ImageButton btnRecorder;

    private PowerManager.WakeLock mWakeLock;
    private MediaRecorder mediaRecorder;
    String localPath = "";// path to save recorded video
    private Camera mCamera;
    private int previewWidth = 480;
    private int previewHeight = 480;

    private int frontCamera = 0; // 0 is back camera，1 is front camera
    Camera.Parameters cameraParameters = null;
    private SurfaceHolder mSurfaceHolder;
    int defaultVideoFrameRate = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recorder_video;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        initViews();

        mSurfaceHolder = videoView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initViews(){
        setTitle(R.string.public_video_recorder);
        videoView = findViewById(R.id.videoView);
        chronometer = findViewById(R.id.chronometer);
        btnSwitchCamera = findViewById(R.id.btn_switch_camera);
        btnRecorder = findViewById(R.id.btn_recorder);

        btnSwitchCamera.setOnClickListener(mOnClickListener);
        btnRecorder.setOnClickListener(mOnClickListener);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void killMyself() {

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if(viewId == R.id.btn_switch_camera){
                switchCamera();

            }else if(viewId == R.id.btn_recorder){
                if(!btnRecorder.isSelected()){
                    if (!startRecording())
                        return;
                    showMessage(getString(R.string.public_start_video));
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    btnRecorder.setSelected(true);

                }else{
                    stopRecording();
                    chronometer.stop();
                    btnRecorder.setSelected(false);

                    new AlertDialog.Builder(mContext)
                            .setMessage(R.string.public_whether_to_make)
                            .setPositiveButton(R.string.public_confirm,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                            sendVideo(null);

                                        }
                                    })
                            .setNegativeButton(R.string.public_cancel,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            if (localPath != null) {
                                                File file = new File(localPath);
                                                if (file.exists())
                                                    file.delete();
                                            }
                                            finish();
                                        }
                                    }).setCancelable(false).show();

                }
            }
        }
    };

    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mCamera == null) {
                if (!initCamera()) {
                    showFailDialog();
                    return;
                }
            }
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
                handleSurfaceChanged();
            } catch (Exception e1) {
                LogUtils.e("video", "start preview fail " + e1.getMessage());
                showFailDialog();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            LogUtils.v("video", "surfaceDestroyed");
        }
    };

    private MediaRecorder.OnInfoListener mOnInfoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                LogUtils.v("video", "max duration reached");
                stopRecording();
                btnSwitchCamera.setVisibility(View.VISIBLE);
                chronometer.stop();
                btnRecorder.setSelected(false);
                chronometer.stop();
                if (localPath == null) {
                    return;
                }
                String st3 = getResources().getString(R.string.public_whether_to_make);
                new AlertDialog.Builder(mContext)
                        .setMessage(st3)
                        .setPositiveButton(R.string.public_confirm, (arg0, arg1) -> {

                            arg0.dismiss();
                            sendVideo(null);

                        }).setNegativeButton(R.string.public_cancel, null)
                        .setCancelable(false).show();
            }
        }
    };

    private MediaRecorder.OnErrorListener mOnErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            LogUtils.e("video", "recording onError:");
            stopRecording();
            showMessage(getString(R.string.public_recording_error));
        }
    };

    public boolean startRecording() {
        if (mediaRecorder == null) {
            if (!initRecorder())
                return false;
        }
        mediaRecorder.setOnInfoListener(mOnInfoListener);
        mediaRecorder.setOnErrorListener(mOnErrorListener);
        mediaRecorder.start();
        return true;
    }

    @SuppressLint("NewApi")
    private boolean initRecorder() {
        if (!SDCardUtils.isSDCardEnable()) {
            showNoSDCardDialog();
            return false;
        }

        if (mCamera == null) {
            if (!initCamera()) {
                showFailDialog();
                return false;
            }
        }
        videoView.setVisibility(View.VISIBLE);
        mCamera.stopPreview();
        mediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        if (frontCamera == 1) {
            mediaRecorder.setOrientationHint(270);
        } else {
            mediaRecorder.setOrientationHint(90);
        }

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // set resolution, should be set after the format and encoder was set
        mediaRecorder.setVideoSize(previewWidth, previewHeight);
        mediaRecorder.setVideoEncodingBitRate(384 * 1024);
        // set frame rate, should be set after the format and encoder was set
        if (defaultVideoFrameRate != -1) {
            mediaRecorder.setVideoFrameRate(defaultVideoFrameRate);
        }
        // set the path for video file
        String dirPath = Environment.getExternalStorageDirectory() + "/" + mContext.getApplication().getApplicationInfo().packageName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = "VID" + System.currentTimeMillis() + ".mp4";

        localPath = dirPath+"/"+fileName;
        mediaRecorder.setOutputFile(localPath);
        mediaRecorder.setMaxDuration(30000);
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setOnInfoListener(null);
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                LogUtils.e("video", "stopRecording error:" + e.getMessage());
            }
        }
        releaseRecorder();

        if (mCamera != null) {
            mCamera.stopPreview();
            releaseCamera();
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    protected void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
        }
    }

    @SuppressLint("NewApi")
    public void switchCamera() {

        if (mCamera == null) {
            return;
        }
        if (Camera.getNumberOfCameras() >= 2) {
            btnSwitchCamera.setEnabled(false);
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            switch (frontCamera) {
                case 0:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    frontCamera = 1;
                    break;
                case 1:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    frontCamera = 0;
                    break;
            }
            try {
                mCamera.lock();
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(videoView.getHolder());
                mCamera.startPreview();
            } catch (IOException e) {
                mCamera.release();
                mCamera = null;
            }
            btnSwitchCamera.setEnabled(true);

        }

    }

    @SuppressLint("NewApi")
    private boolean initCamera() {
        try {
            if (frontCamera == 0) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }
            Camera.Parameters camParams = mCamera.getParameters();
            mCamera.lock();
            mSurfaceHolder = videoView.getHolder();
            mSurfaceHolder.addCallback(mSurfaceCallback);
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mCamera.setDisplayOrientation(90);

        } catch (RuntimeException ex) {
            LogUtils.e("video", "init Camera fail " + ex.getMessage());
            return false;
        }
        return true;
    }

    private void handleSurfaceChanged() {
        if (mCamera == null) {
            finish();
            return;
        }
        boolean hasSupportRate = false;
        List<Integer> supportedPreviewFrameRates = mCamera.getParameters()
                .getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates != null
                && supportedPreviewFrameRates.size() > 0) {
            Collections.sort(supportedPreviewFrameRates);
            for (int i = 0; i < supportedPreviewFrameRates.size(); i++) {
                int supportRate = supportedPreviewFrameRates.get(i);

                if (supportRate == 15) {
                    hasSupportRate = true;
                }

            }
            if (hasSupportRate) {
                defaultVideoFrameRate = 15;
            } else {
                defaultVideoFrameRate = supportedPreviewFrameRates.get(0);
            }

        }

        // get all resolutions which camera provide
        List<Camera.Size> resolutionList =  Utils.getResolutionList(mCamera);
        if (resolutionList != null && resolutionList.size() > 0) {
            Collections.sort(resolutionList, new Utils.ResolutionComparator());
            Camera.Size previewSize = null;
            boolean hasSize = false;

            // use 60*480 if camera support
            for (int i = 0; i < resolutionList.size(); i++) {
                Camera.Size size = resolutionList.get(i);
                if (size != null && size.width == 640 && size.height == 480) {
                    previewSize = size;
                    previewWidth = previewSize.width;
                    previewHeight = previewSize.height;
                    hasSize = true;
                    break;
                }
            }
            // use medium resolution if camera don't support the above resolution
            if (!hasSize) {
                int mediumResolution = resolutionList.size() / 2;
                if (mediumResolution >= resolutionList.size())
                    mediumResolution = resolutionList.size() - 1;
                previewSize = resolutionList.get(mediumResolution);
                previewWidth = previewSize.width;
                previewHeight = previewSize.height;

            }
        }
    }


    MediaScannerConnection msc = null;
    ProgressDialog progressDialog = null;

    public void sendVideo(View view) {
        if (TextUtils.isEmpty(localPath)) {
            LogUtils.e("Recorder", "recorder fail please try again!");
            return;
        }
        if (msc == null)
            msc = new MediaScannerConnection(this,
                    new MediaScannerConnection.MediaScannerConnectionClient() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            LogUtils.d(TAG, "scanner completed");
                            msc.disconnect();
                            progressDialog.dismiss();

//							setResult(RESULT_OK, getIntent().putExtra("uri", uri));

                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("uri", uri);
                            bundle.putString("path", path);
                            intent.putExtras(bundle);

                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onMediaScannerConnected() {
                            msc.scanFile(localPath, "video/*");
                        }
                    });


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("processing...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        msc.connect();

    }

    private void showFailDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.public_hint)
                .setMessage(R.string.public_open_equipment_failure)
                .setPositiveButton(R.string.public_confirm,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        }).setCancelable(false).show();

    }

    private void showNoSDCardDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.public_hint)
                .setMessage("No sd card!")
                .setPositiveButton(R.string.public_confirm, (dialog, which) -> finish())
                .setCancelable(false).show();
    }

}
