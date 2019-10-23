package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.CallingState;
import com.hyphenate.easeui.utils.IMConstants;
import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.app.IMModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.util.EMLog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.DiffuseView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.IM_CALLWAITACTIVITY)
public class CallWaitActivity extends BaseSupportActivity implements IView {

    @BindView(R2.id.floatAvatarView)
    DiffuseView floatAvatarView;
    @BindView(R2.id.tvUsername)
    TextView tvUsername;
//    @BindView(R2.id.tvType)
//    TextView tvType;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tvHangUp)
    TextView tvHangUp;
    @BindView(R2.id.tvAnswer)
    TextView tvAnswer;

    private final int MSG_CALL_MAKE_VIDEO = 0;
    private final int MSG_CALL_MAKE_VOICE = 1;
    private final int MSG_CALL_ANSWER = 2;
    private final int MSG_CALL_REJECT = 3;
    private final int MSG_CALL_END = 4;
    private final int MSG_CALL_RELEASE_HANDLER = 5;

    //拒接
    private boolean isRefused;
    //接受
    private boolean isAnswered;

    private AudioManager audioManager;
    private SoundPool soundPool;
    private Ringtone ringtone;
    private int outgoing;
    private int streamID = -1;
    private CallingState callingState = CallingState.CANCELLED;
    private EMCallManager.EMCallPushProvider pushProvider;

    private IMModel mIMModel;
    private String mUsername;
    private UserInfo mUserEntity;
    private int mCallType;
    private boolean mIsComingCall;

    public static void start(Activity context, String toChatUsername,int callType,boolean isComingCall ){
        Intent intent = new Intent(context, CallWaitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", toChatUsername);
        bundle.putInt("callType", callType);
        bundle.putBoolean("isComingCall", isComingCall);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_call_wait; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        mUsername = bundle.getString("username");
        mCallType = bundle.getInt("callType");
        mIsComingCall = bundle.getBoolean("isComingCall");

        mIMModel = IMHelper.getInstance().getModel();
        mUserEntity = mIMModel.getUserEntityByUsername(mUsername);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if(!mIsComingCall){
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.em_outgoing, 1);
            //播放呼叫铃声
//            streamID = playMakeCallSounds();
            handler.sendEmptyMessage(MSG_CALL_MAKE_VIDEO);
            handler.postDelayed(new Runnable() {
                public void run() {
                    streamID = playMakeCallSounds();
                }
            }, 300);

            final int MAKE_CALL_TIMEOUT = 50 * 1000;
            handler.removeCallbacks(timeoutHangup);
            handler.postDelayed(timeoutHangup, MAKE_CALL_TIMEOUT);

        }else{
            if (EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.IDLE
                    || EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.DISCONNECTED) {
                finish();
                return;
            }
            //播放接听铃声
            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();
        }

        pushProvider = new EMCallManager.EMCallPushProvider() {

            void updateMessageText(final EMMessage oldMsg, final String to) {
                // update local message text
                EMConversation conv = EMClient.getInstance().chatManager().getConversation(oldMsg.getTo());
                conv.removeMessage(oldMsg.getMsgId());
            }

            @Override
            public void onRemoteOffline(final String to) {
                //this function should exposed & move to Demo
                EMLog.d(TAG, "onRemoteOffline, to:" + to);
                final EMMessage message = EMMessage.createTxtSendMessage("You have an incoming call", to);
                // set the user-defined extension field
                message.setAttribute("em_apns_ext", true);
                message.setAttribute("is_voice_call", mCallType == IMConstants.CALL_TYPE_VOICE ? true : false);
                message.setMessageStatusCallback(new EMCallBack(){

                    @Override
                    public void onSuccess() {
                        EMLog.d(TAG, "onRemoteOffline success");
                        updateMessageText(message, to);
                    }

                    @Override
                    public void onError(int code, String error) {
                        EMLog.d(TAG, "onRemoteOffline Error");
                        updateMessageText(message, to);
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }
                });
                // send messages
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        };
        EMClient.getInstance().callManager().setPushProvider(pushProvider);

        initViews();
        addCallStateListener();
    }

    private void addCallStateListener() {
        EMClient.getInstance().callManager().addCallStateChangeListener(mCallStateChangeListener);
    }

    private void removeCallStateListener() {
        if(null != mCallStateChangeListener)
            EMClient.getInstance().callManager().removeCallStateChangeListener(mCallStateChangeListener);
    }

    private void initViews(){
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        tvUsername.setText(mUserEntity.getNickname());

        if(!mIsComingCall){
//            tvMoney.setText(String.format(getString(R.string.consume_coins_per_minute),
//                    mCallType == IMConstants.CALL_TYPE_VOICE ? mUserEntity.getVoiceCoinPM() : mUserEntity.getVideoCoinPM()));
            tvAnswer.setVisibility(View.GONE);
            tvHangUp.setText(R.string.cancel);
        }else{
//            tvMoney.setText(String.format(getString(R.string.income_coins_per_minute),
//                    mCallType == IMConstants.CALL_TYPE_VOICE ? mUserEntity.getVoiceCoinPM() : mUserEntity.getVideoCoinPM()));
        }
    }

    Runnable timeoutHangup = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(MSG_CALL_END);
        }
    };

    HandlerThread callHandlerThread = new HandlerThread("callHandlerThread");
    { callHandlerThread.start(); }

    protected Handler handler = new Handler(callHandlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CALL_MAKE_VIDEO:
                case MSG_CALL_MAKE_VOICE:
                    try {
                        if (msg.what == MSG_CALL_MAKE_VIDEO) {
                            EMClient.getInstance().callManager().makeVideoCall(mUsername);
                        } else {
                            EMClient.getInstance().callManager().makeVoiceCall(mUsername);
                        }
                    } catch (final EMServiceNotReadyException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            String hint = e.getMessage();
                            if (e.getErrorCode() == EMError.CALL_REMOTE_OFFLINE) {
                                hint = getResources().getString(R.string.The_other_is_not_online);
                            } else if (e.getErrorCode() == EMError.USER_NOT_LOGIN) {
                                hint = getResources().getString(R.string.Is_not_yet_connected_to_the_server);
                            } else if (e.getErrorCode() == EMError.INVALID_USER_NAME) {
                                hint = getResources().getString(R.string.illegal_user_name);
                            } else if (e.getErrorCode() == EMError.CALL_BUSY) {
                                hint = getResources().getString(R.string.The_other_is_on_the_phone);
                            } else if (e.getErrorCode() == EMError.NETWORK_ERROR) {
                                hint = getResources().getString(R.string.can_not_connect_chat_server_connection);
                            }
                            showMessage(hint);
                            killMyself();
                        });
                    }
                    break;

                case MSG_CALL_END:
                    if (soundPool != null)
                        soundPool.stop(streamID);
                    try {
                        EMClient.getInstance().callManager().endCall();
                    } catch (Exception e) {
                        saveCallRecord();
                        finish();
                    }
                    break;

                case MSG_CALL_RELEASE_HANDLER:
                    try {
                        EMClient.getInstance().callManager().endCall();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.removeCallbacks(timeoutHangup);
                    handler.removeMessages(MSG_CALL_MAKE_VIDEO);
                    handler.removeMessages(MSG_CALL_MAKE_VOICE);
                    handler.removeMessages(MSG_CALL_END);
                    callHandlerThread.quit();
                    break;

                default:
                    break;
            }
        }
    };

    void releaseHandler() {
        handler.sendEmptyMessage(MSG_CALL_RELEASE_HANDLER);
    }


    private EMCallStateChangeListener mCallStateChangeListener = new EMCallStateChangeListener() {
        @Override
        public void onCallStateChanged(CallState callState, CallError callError) {
            switch (callState){
                case CONNECTING: // is connecting
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            tvType.setText(R.string.Are_connected_to_each_other);
                        }
                    });
                    break;

                case CONNECTED: // connected
                    break;

                case ACCEPTED: // call is accepted
                    handler.removeCallbacks(timeoutHangup);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (soundPool != null)
                                    soundPool.stop(streamID);
//                                VideoCallActivity1.start(mContext,mUsername,mIsComingCall);
                                killMyself();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;

                case DISCONNECTED: // call is disconnected
                    handler.removeCallbacks(timeoutHangup);
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    final CallError fError = callError;
                    if (fError == CallError.REJECTED) {
                        callingState = CallingState.BEREFUSED;
                    } else if (fError == CallError.ERROR_TRANSPORT) {
                    } else if (fError == CallError.ERROR_UNAVAILABLE) {
                        callingState = CallingState.OFFLINE;
                    } else if (fError == CallError.ERROR_BUSY) {
                        callingState = CallingState.BUSY;
                    } else if (fError == CallError.ERROR_NORESPONSE) {
                        callingState = CallingState.NO_RESPONSE;
                    }else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED){
                        callingState = CallingState.VERSION_NOT_SAME;
                    } else {
                        if (isRefused) {
                            callingState = CallingState.REFUSED;
                        }else if (isAnswered) {
                            callingState = CallingState.NORMAL;
                        } else {
                            if (mIsComingCall) {
                                callingState = CallingState.UNANSWERED;
                            } else {
                                if (callingState != CallingState.NORMAL) {
                                    callingState = CallingState.CANCELLED;
                                }
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            saveCallRecord();
                            killMyself();
                        }
                    });
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        floatAvatarView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        floatAvatarView.stop();
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
        ArmsUtils.snackbarText(message);
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

    @OnClick({R2.id.tvHangUp, R2.id.tvAnswer})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tvHangUp) {
            msgCallReject();

        } else if (i == R.id.tvAnswer) {
            answerCall();
        }
    }

    private void answerCall(){
        isAnswered = true;
        if (ringtone != null)
            ringtone.stop();
        if (mIsComingCall) {
            try {
                EMClient.getInstance().callManager().answerCall();
            } catch (EMNoActiveCallException e) {
                e.printStackTrace();
                saveCallRecord();
                killMyself();
            }
        }
    }

    private void  msgCallReject(){
        isRefused = true;
        if (ringtone != null)
            ringtone.stop();
        callingState = CallingState.REFUSED;
        try {
            EMClient.getInstance().callManager().rejectCall();
        } catch (Exception e) {
            e.printStackTrace();
            saveCallRecord();
            killMyself();
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        if (soundPool != null)
            soundPool.release();

        if (ringtone != null && ringtone.isPlaying())
            ringtone.stop();

        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setMicrophoneMute(false);

        removeCallStateListener();

        if (pushProvider != null) {
            EMClient.getInstance().callManager().setPushProvider(null);
            pushProvider = null;
        }

        releaseHandler();
        super.onDestroy();
    }

    // play the incoming call ringtone
    protected int playMakeCallSounds() {
        try {
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);

            // play
            int id = soundPool.play(outgoing, // sound resource
                    0.3f, // left volume
                    0.3f, // right volume
                    1,    // priority
                    -1,   // loop，0 is no loop，-1 is loop forever
                    1);   // playback rate (1.0 = normal playback, range 0.5 to 2.0)
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    private void saveCallRecord() {
        @SuppressWarnings("UnusedAssignment") EMMessage message = null;
        @SuppressWarnings("UnusedAssignment") EMTextMessageBody txtBody = null;
        if (!mIsComingCall) { // outgoing call
            message = EMMessage.createSendMessage(EMMessage.Type.TXT);
            message.setTo(mUsername);
        } else {
            message = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            message.setFrom(mUsername);
        }
        String st1 = getResources().getString(R.string.call_duration);
        String st2 = getResources().getString(R.string.Refused);
        String st3 = getResources().getString(R.string.The_other_party_has_refused_to);
        String st4 = getResources().getString(R.string.The_other_is_not_online);
        String st5 = getResources().getString(R.string.The_other_is_on_the_phone);
        String st6 = getResources().getString(R.string.The_other_party_did_not_answer);
        String st7 = getResources().getString(R.string.did_not_answer);
        String st8 = getResources().getString(R.string.Has_been_cancelled);
        switch (callingState) {
            case NORMAL:
                txtBody = new EMTextMessageBody(st1);
                break;
            case REFUSED:
                txtBody = new EMTextMessageBody(st2);
                break;
            case BEREFUSED:
                txtBody = new EMTextMessageBody(st3);
                break;
            case OFFLINE:
                txtBody = new EMTextMessageBody(st4);
                break;
            case BUSY:
                txtBody = new EMTextMessageBody(st5);
                break;
            case NO_RESPONSE:
                txtBody = new EMTextMessageBody(st6);
                break;
            case UNANSWERED:
                txtBody = new EMTextMessageBody(st7);
                break;
            default:
                txtBody = new EMTextMessageBody(st8);
                break;
        }
        // set message extension
        if(mCallType == 0)
            message.setAttribute(IMConstants.MESSAGE_ATTR_IS_VOICE_CALL, true);
        else
            message.setAttribute(IMConstants.MESSAGE_ATTR_IS_VIDEO_CALL, true);

        // set message body
        message.addBody(txtBody);
        message.setMsgId(UUID.randomUUID().toString());
        message.setStatus(EMMessage.Status.SUCCESS);
        // save
        EMClient.getInstance().chatManager().saveMessage(message);
    }
}
