package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haisheng.easeim.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.bean.GetLayBean;
import com.hyphenate.easeui.bean.RedpacketRewardBean;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import me.jessyan.armscomponent.commonres.utils.CommonMethod;

public class ChatGetLayMessage extends EaseChatRow {

    private TextView tvGetRedpacketNickName;
    private TextView tvSendRedpacketNickName;
    private LinearLayout llItem;

    public ChatGetLayMessage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_received_get_lay_message , this);
    }

    @Override
    protected void onFindViewById() {
        tvGetRedpacketNickName = findViewById( R.id.tv_getRedPacketNickName);
        tvSendRedpacketNickName = findViewById( R.id.tv_sendRedPacketNickName);
        llItem = findViewById( R.id.ll_item);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        try {
            String  getContent = message.getStringAttribute ( IMConstants.MESSAGE_ATTR_CONENT,"");
            Log.e ( "TAg","ChatGetLayMessage="+ getContent);
            if(TextUtils.isEmpty(getContent)){
                return;
            }
            GetLayBean getLayBean = new Gson ().fromJson(getContent, GetLayBean.class);
            String myHxid = CommonMethod.getHxidForLocal ();

            //如果自己的id等于发包人id或者等于抢包人的id
            if (myHxid.equals ( getLayBean.getHxid () ) || myHxid.equals ( getLayBean.getHxid1 () )){
                llItem.setVisibility ( VISIBLE );
                tvGetRedpacketNickName.setText ( getLayBean.getNickname () );
                tvSendRedpacketNickName.setText ( getLayBean.getNickname_red () );
            }else{
                llItem.setVisibility ( GONE );
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
    }


}
