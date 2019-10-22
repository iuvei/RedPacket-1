package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.GetRedPacketMessageBean;
import com.haisheng.easeim.mvp.model.entity.RedpacketRewardBean;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

public class ChatRewardMessage extends EaseChatRow {

    private TextView tvNickname;
    private TextView tvNum;
    private TextView tvMoney;

    public ChatRewardMessage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_received_reward_message , this);
    }

    @Override
    protected void onFindViewById() {
        tvNickname = findViewById( R.id.tv_nickname);
        tvNum = findViewById( R.id.tv_num);
        tvMoney = findViewById( R.id.tv_money);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        try {
            String  getContent = message.getStringAttribute ( IMConstants.MESSAGE_ATTR_CONENT,"");
            LogUtils.e ( "getContent = "+getContent );
            if(!TextUtils.isEmpty(getContent)){
                RedpacketRewardBean rewardBean = new Gson ().fromJson(getContent, RedpacketRewardBean.class);
                tvNickname.setText ( rewardBean.getNickname () );
                tvNum.setText ( rewardBean.getGold ()+"-"+rewardBean.getBoom () );
                tvMoney.setText ( "("+rewardBean.getGetnums ()+")"+"+"+rewardBean.getRewardgold ()+"元" );
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


}
