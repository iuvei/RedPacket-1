package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.GunControlSettlementInfo;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class ChatGunControlSettlement extends EaseChatRow {

    TextView tvSendRedpacketUser;
    TextView tvSendRedpacketMoney;
    TextView tvSendRedpacketNumber;
    TextView tvRedpacketGameRules;
    TextView tvMineNumber;
    TextView tvBombNumber;
    TextView tvRedpacketOdds;
    TextView tvAwardMoney;

    public ChatGunControlSettlement(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate( R.layout.row_gun_control_settlement, this);
    }

    @Override
    protected void onFindViewById() {
        tvSendRedpacketUser = findViewById( R.id.tv_send_redpacket_user);
        tvSendRedpacketMoney = findViewById( R.id.tv_send_redpacket_money);
        tvSendRedpacketNumber = findViewById( R.id.tv_send_redpacket_number);
        tvRedpacketGameRules = findViewById( R.id.tv_redpacket_game_rules);
        tvMineNumber = findViewById( R.id.tv_mine_number);
        tvBombNumber = findViewById( R.id.tv_bomb_number);
        tvRedpacketOdds = findViewById( R.id.tv_redpacket_odds);
        tvAwardMoney = findViewById( R.id.tv_award_money);
    }

    @Override
    public void onSetUpView() {
        String sGunControlSettlementInfo =message.getStringAttribute( IMConstants.MESSAGE_ATTR_CONENT,"");
        if(!TextUtils.isEmpty(sGunControlSettlementInfo)){
            GunControlSettlementInfo settlementInfo = new Gson().fromJson(sGunControlSettlementInfo, GunControlSettlementInfo.class);
            if(null != usernickView)
                usernickView.setText(settlementInfo.getNickname());
            if(null != userAvatarView)
                ImageLoader.displayHeaderImage(context,settlementInfo.getAvatarUrl(),userAvatarView);

            tvSendRedpacketUser.setText(String.format(context.getString( R.string.send_redpacket_user),settlementInfo.getNickname()));
            tvSendRedpacketMoney.setText(String.format(context.getString( R.string.send_redpacket_money),settlementInfo.getMoney()));
            tvSendRedpacketNumber.setText(String.format(context.getString( R.string.send_redpacket_number),settlementInfo.getNumber()));
            tvRedpacketGameRules.setText(String.format(context.getString( R.string.redpacket_game_rules),settlementInfo.getGameRules()));
            tvMineNumber.setText(String.format(context.getString( R.string.mine_number),settlementInfo.getMineNumber()));
            tvBombNumber.setText(String.format(context.getString( R.string.bomb_number),settlementInfo.getBombNumber()));
            tvRedpacketOdds.setText(String.format(context.getString( R.string.redpacket_odds),settlementInfo.getOdds()));
            tvAwardMoney.setText(String.format(context.getString( R.string.award_money),settlementInfo.getAwardMoney()));
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString( R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            List<String> userList = EaseDingMessageHelper.get().getAckUsers(message);
            int count = userList == null ? 0 : userList.size();
            ackedView.setText(String.format(getContext().getString( R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
