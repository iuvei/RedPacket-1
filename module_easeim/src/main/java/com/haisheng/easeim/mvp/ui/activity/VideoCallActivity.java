package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.app.IMModel;
import com.haisheng.easeim.di.component.DaggerVideoCallComponent;
import com.haisheng.easeim.di.module.VideoCallModule;
import com.haisheng.easeim.mvp.contract.VideoCallContract;
import com.haisheng.easeim.mvp.presenter.VideoCallPresenter;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMVideoCallHelper;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMCallSurfaceView;
import com.hyphenate.util.EMLog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.DiffuseView;
import me.jessyan.armscomponent.commonres.view.GiftSendSelectView;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class VideoCallActivity extends CallActivity<VideoCallPresenter> implements VideoCallContract.View {

    @BindView(R2.id.opposite_surface)
    EMCallSurfaceView oppositeSurface;
    @BindView(R2.id.local_surface)
    EMCallSurfaceView localSurface;
    @BindView(R2.id.imgHead)
    ImageView imgHead;
    @BindView(R2.id.tvUsername1)
    TextView tvUsername1;
    @BindView(R2.id.tvLikeNum)
    TextView tvLikeNum;
    @BindView(R2.id.imgState)
    ImageView imgState;
    @BindView(R2.id.viewUserInfo)
    RelativeLayout viewUserInfo;
    @BindView(R2.id.chronometer)
    Chronometer chronometer;
    @BindView(R2.id.viewControl)
    RelativeLayout viewControl;
    @BindView(R2.id.btn_show_giftselect)
    ImageButton btnShowGiftselect;
    @BindView(R2.id.tvNetBadHit)
    TextView tvNetBadHit;
    @BindView(R2.id.iv_gift_big)
    ImageView ivGiftBig;
    @BindView(R2.id.giftSendSelectView)
    GiftSendSelectView giftSendSelectView;
    @BindView(R2.id.floatAvatarView)
    DiffuseView floatAvatarView;
    @BindView(R2.id.tvUsername2)
    TextView tvUsername2;
    @BindView(R2.id.tv_call_state)
    TextView tvCallState;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tvHangUp)
    TextView tvHangUp;
    @BindView(R2.id.tvAnswer)
    TextView tvAnswer;
    @BindView(R2.id.imgHangUp)
    ImageView imgHangUp;
    @BindView(R2.id.btn_hands_free)
    ImageButton btnHandsFree;
    @BindView(R2.id.btn_nowheat)
    ImageButton btnNowheat;

    @BindView(R2.id.rl_call_wait_container)
    RelativeLayout rlCallWaitContainer;


    private boolean isMuteState;
    private boolean isHandsfreeState;
    private boolean isAnswered;
    private boolean endCallTriggerByMe = false;
    private boolean monitor = true;
    private int surfaceState = -1;

    private Handler uiHandler;

    private IMModel mIMModel;
    private boolean isInCalling;

    boolean isRecording = false;
    private EMVideoCallHelper callHelper;

    private boolean isPauseVideoTransfer = false;

    public static void start(Activity context, String toChatUsername, boolean isComingCall) {
        Intent intent = new Intent(context, VideoCallActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", toChatUsername);
        bundle.putBoolean("isComingCall", isComingCall);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoCallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .videoCallModule(new VideoCallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_call; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            finish();
//            return;
//        }
        super.initData(savedInstanceState);
        StatusBarUtils.setTranslucentStatus(this);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        isInComingCall = bundle.getBoolean("isComingCall");
        mIMModel = IMHelper.getInstance().getModel();
        mUserEntity = mIMModel.getUserEntityByUsername(username);

        msgid = UUID.randomUUID().toString();
        IMHelper.getInstance().isVideoCalling = true;
        callType = 1;

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        uiHandler = new Handler();

        localSurface.setZOrderMediaOverlay(true);
//        localSurface.setZOrderOnTop(true);

        addCallStateListener();
        if (!isInComingCall) {// outgoing call
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.em_outgoing, 1);

//            tvMoney.setText(String.format(getString(R.string.consume_coins_per_minute), mUserEntity.getVideoCoinPM()));
            tvAnswer.setVisibility(View.GONE);
            tvHangUp.setText(R.string.cancel);

            btnShowGiftselect.setImageResource(R.mipmap.videochat_btn_sendgift);

            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
            handler.sendEmptyMessage(MSG_CALL_MAKE_VIDEO);
            handler.postDelayed(new Runnable() {
                public void run() {
                    streamID = playMakeCallSounds();
                }
            }, 300);
        } else { // incoming call

            if (EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.IDLE
                    || EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.DISCONNECTED) {
                // the call has ended
                finish();
                return;
            }
            localSurface.setVisibility(View.INVISIBLE);

            btnShowGiftselect.setImageResource(R.mipmap.videochat_btn_wantgift);

            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }

        final int MAKE_CALL_TIMEOUT = 50 * 1000;
        handler.removeCallbacks(timeoutHangup);
        handler.postDelayed(timeoutHangup, MAKE_CALL_TIMEOUT);

        // get instance of call helper, should be called after setSurfaceView was called
        callHelper = EMClient.getInstance().callManager().getVideoCallHelper();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R2.id.opposite_surface, R2.id.imgHead, R2.id.imgState, R2.id.imgNoVideo, R2.id.imgTransCanmera, R2.id.imgHangUp,
            R2.id.btn_hands_free, R2.id.btn_nowheat, R2.id.imgBeauty, R2.id.btn_show_giftselect,R2.id.local_surface, R2.id.tvHangUp, R2.id.tvAnswer})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.opposite_surface) {
            toggleViewControl();

        }  else if (i == R.id.local_surface) {
            changeCallView();

        } else if (i == R.id.imgHead) {


        } else if (i == R.id.imgState) {


        } else if (i == R.id.imgNoVideo) {
            toggleVideoTransfe();

        } else if (i == R.id.imgTransCanmera) {
            EMClient.getInstance().callManager().switchCamera();

        } else if (i == R.id.imgHangUp) {
            hangUp();

        } else if (i == R.id.btn_hands_free) {
            toggleHandsfree();

        } else if (i == R.id.btn_nowheat) {
            toggleVoiceTransfer();

        } else if (i == R.id.imgBeauty) {


        } else if (i == R.id.btn_show_giftselect) {
            giftSendSelectView.setVisibility(View.VISIBLE);

        }else if (i == R.id.tvHangUp) {
            isRefused = true;
            handler.sendEmptyMessage(MSG_CALL_REJECT);

        } else if (i == R.id.tvAnswer) {
            answerCall();

        }
    }

    private void answerCall(){
        isAnswered = true;

        if (ringtone != null)
            ringtone.stop();

        openSpeakerOn();
        isHandsfreeState = true;
        btnHandsFree.setSelected(isHandsfreeState);

        localSurface.setVisibility(View.VISIBLE);
        rlCallWaitContainer.setVisibility(View.GONE);

        handler.sendEmptyMessage(MSG_CALL_ANSWER);
    }

    private void hangUp(){
        chronometer.stop();
        endCallTriggerByMe = true;
        tvCallState.setText(getResources().getString(R.string.hanging_up));
        if (isRecording) {
            callHelper.stopVideoRecord();
        }
        EMLog.d(TAG, "btn_hangup_call");
        handler.sendEmptyMessage(MSG_CALL_END);
    }

    private void toggleViewControl(){
        viewControl.setVisibility(viewControl.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void toggleHandsfree(){
        if (isHandsfreeState) {
            // turn off speaker
            closeSpeakerOn();
            isHandsfreeState = false;
        } else {
            openSpeakerOn();
            isHandsfreeState = true;
        }
        btnHandsFree.setSelected(isHandsfreeState);
    }

    private void toggleVideoTransfe() {
        try {
            if(!isPauseVideoTransfer){
                EMClient.getInstance().callManager().pauseVideoTransfer();
            }else{
                EMClient.getInstance().callManager().resumeVideoTransfer();
            }
            isPauseVideoTransfer = !isPauseVideoTransfer;
        }catch (HyphenateException e){
            e.printStackTrace();
        }
    }

    private void toggleVoiceTransfer(){
        if (isMuteState) {
            // resume voice transfer
            try {
                EMClient.getInstance().callManager().resumeVoiceTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            isMuteState = false;
        } else {
            // pause voice transfer
            try {
                EMClient.getInstance().callManager().pauseVoiceTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            isMuteState = true;
        }
        btnNowheat.setSelected(isMuteState);
    }


    /**
     * 切换通话界面，这里就是交换本地和远端画面控件设置，以达到通话大小画面的切换
     */
    private void changeCallView() {
        if (surfaceState == 0) {
            surfaceState = 1;
            EMClient.getInstance().callManager().setSurfaceView(oppositeSurface, localSurface);
        } else {
            surfaceState = 0;
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }
    }

    private void addCallStateListener() {
        callStateListener = new EMCallStateChangeListener() {

            @Override
            public void onCallStateChanged(final CallState callState, final CallError error) {
                switch (callState) {

                    case CONNECTING: // is connecting
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCallState.setText(R.string.Are_connected_to_each_other);
                            }
                        });
                        break;

                    case CONNECTED: // connected
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCallState.setText(R.string.have_connected_with);
                            }
                        });
                        break;

                    case ACCEPTED: // call is accepted
                        surfaceState = 0;
                        handler.removeCallbacks(timeoutHangup);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (soundPool != null)
                                        soundPool.stop(streamID);
                                    EMLog.d("EMCallManager", "soundPool stop ACCEPTED");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                rlCallWaitContainer.setVisibility(View.GONE);
                                localSurface.setVisibility(View.VISIBLE);

                                isHandsfreeState = true;
                                openSpeakerOn();
                                btnHandsFree.setSelected(isHandsfreeState);

                                isInCalling = true;
                                chronometer.setBase(SystemClock.elapsedRealtime());
                                chronometer.start();

                                tvCallState.setText(R.string.In_the_call);
                                callingState = CallingState.NORMAL;
                            }

                        });
                        break;

                    case NETWORK_DISCONNECTED:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                tvNetBadHit.setVisibility(View.VISIBLE);
                                tvNetBadHit.setText(R.string.network_unavailable);
                            }
                        });
                        break;

                    case NETWORK_UNSTABLE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                tvNetBadHit.setVisibility(View.VISIBLE);
                                if (error == CallError.ERROR_NO_DATA) {
                                    tvNetBadHit.setText(R.string.no_call_data);
                                } else {
                                    tvNetBadHit.setText(R.string.network_unstable);
                                }
                            }
                        });
                        break;

                    case NETWORK_NORMAL:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                tvNetBadHit.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;

                    case DISCONNECTED: // call is disconnected
                        handler.removeCallbacks(timeoutHangup);
                        @SuppressWarnings("UnnecessaryLocalVariable") final CallError fError = error;
                        runOnUiThread(new Runnable() {
                            private void postDelayedCloseMsg() {
                                uiHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        removeCallStateListener();
                                        saveCallRecord();
                                        finish();
                                    }

                                }, 200);
                            }

                            @Override
                            public void run() {
                                chronometer.stop();
                                callDruationText = chronometer.getText().toString();
                                String s1 = getResources().getString(R.string.The_other_party_refused_to_accept);
                                String s2 = getResources().getString(R.string.Connection_failure);
                                String s3 = getResources().getString(R.string.The_other_party_is_not_online);
                                String s4 = getResources().getString(R.string.The_other_is_on_the_phone_please);
                                String s5 = getResources().getString(R.string.The_other_party_did_not_answer);

                                String s6 = getResources().getString(R.string.hang_up);
                                String s7 = getResources().getString(R.string.The_other_is_hang_up);
                                String s8 = getResources().getString(R.string.did_not_answer);
                                String s9 = getResources().getString(R.string.Has_been_cancelled);
                                String s10 = getResources().getString(R.string.Refused);

                                if (fError == CallError.REJECTED) {
                                    callingState = CallingState.BEREFUSED;
                                    tvCallState.setText(s1);
                                } else if (fError == CallError.ERROR_TRANSPORT) {
                                    tvCallState.setText(s2);
                                } else if (fError == CallError.ERROR_UNAVAILABLE) {
                                    callingState = CallingState.OFFLINE;
                                    tvCallState.setText(s3);
                                } else if (fError == CallError.ERROR_BUSY) {
                                    callingState = CallingState.BUSY;
                                    tvCallState.setText(s4);
                                } else if (fError == CallError.ERROR_NORESPONSE) {
                                    callingState = CallingState.NO_RESPONSE;
                                    tvCallState.setText(s5);
                                } else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                                    callingState = CallingState.VERSION_NOT_SAME;
                                    tvCallState.setText(R.string.call_version_inconsistent);
                                } else {
                                    if (isRefused) {
                                        callingState = CallingState.REFUSED;
                                        tvCallState.setText(s10);
                                    } else if (isAnswered) {
                                        callingState = CallingState.NORMAL;
                                        if (endCallTriggerByMe) {
                                            tvCallState.setText(s6);
                                        } else {
                                            tvCallState.setText(s7);
                                        }
                                    } else {
                                        if (isInComingCall) {
                                            callingState = CallingState.UNANSWERED;
                                            tvCallState.setText(s8);
                                        } else {
                                            if (callingState != CallingState.NORMAL) {
                                                callingState = CallingState.CANCELLED;
                                                tvCallState.setText(s9);
                                            } else {
                                                tvCallState.setText(s6);
                                            }
                                        }
                                    }
                                }
                                showMessage(tvCallState.getText().toString());
                                postDelayedCloseMsg();
                            }

                        });

                        break;

                    default:
                        break;
                }

            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener);
    }

    void removeCallStateListener() {
        EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener);
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (isInCalling) {
            try {
                if(!isPauseVideoTransfer)
                    EMClient.getInstance().callManager().pauseVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInCalling) {
            try {
                if(!isPauseVideoTransfer)
                    EMClient.getInstance().callManager().resumeVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        if(giftSendSelectView.onBackPressed()){
            callDruationText = chronometer.getText().toString();
            hangUp();
            super.onBackPressedSupport();
        }
    }

    @Override
    protected void onDestroy() {
        IMHelper.getInstance().isVideoCalling = false;
        if (isRecording) {
            callHelper.stopVideoRecord();
            isRecording = false;
        }
        localSurface.getRenderer().dispose();
        localSurface = null;
        oppositeSurface.getRenderer().dispose();
        oppositeSurface = null;
        super.onDestroy();
    }


}
