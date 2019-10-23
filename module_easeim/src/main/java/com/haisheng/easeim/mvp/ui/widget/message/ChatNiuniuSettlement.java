package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.easeui.bean.NiuniuSettlementInfo;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class ChatNiuniuSettlement extends EaseChatRow {

    TextView tvBankNumber;
    TextView tvPlayerNumber;
    ImageView ivAvatar;
    TextView tvNickname;
    ImageView ivBanker;
    ImageView ivNiuniu;
    ImageView ivStatus;

    public ChatNiuniuSettlement(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate( R.layout.row_niuniu_settlement, this);
    }

    @Override
    protected void onFindViewById() {
        ivStatus = findViewById( R.id.iv_status);
        tvBankNumber = findViewById( R.id.tv_bank_number);
        tvPlayerNumber = findViewById( R.id.tv_player_number);
        ivAvatar = findViewById( R.id.iv_avatar);
        tvNickname = findViewById( R.id.tv_nickname);
        ivBanker = findViewById( R.id.iv_banker);
        ivNiuniu = findViewById( R.id.iv_niuniu);
    }

    @Override
    public void onSetUpView() {
        String sNiuniuSettlementInfo = message.getStringAttribute( IMConstants.MESSAGE_ATTR_CONENT, "");
        if (!TextUtils.isEmpty(sNiuniuSettlementInfo)) {
            NiuniuSettlementInfo settlementInfo = new Gson().fromJson(sNiuniuSettlementInfo, NiuniuSettlementInfo.class);
            int status = settlementInfo.getStatus();
            ivStatus.setVisibility(status!=0? VISIBLE : INVISIBLE);
            ivStatus.setImageResource(status==1? R.drawable.ic_bank_win : R.drawable.ic_bank_fail);
            tvBankNumber.setText(String.valueOf(settlementInfo.getBankNumber()));
            tvPlayerNumber.setText(String.valueOf(settlementInfo.getPlayerNumber()));
            ImageLoader.displayHeaderImage(context,settlementInfo.getAvatarUrl(),ivAvatar);
            tvNickname.setText(settlementInfo.getNickname());
            int niuniuNumber = settlementInfo.getNiuniuNumber();
            int iconRedId;
            switch (niuniuNumber) {
                case 1:
                    iconRedId = R.drawable.ic_cow_1;
                    break;
                case 2:
                    iconRedId = R.drawable.ic_cow_2;
                    break;
                case 3:
                    iconRedId = R.drawable.ic_cow_3;
                    break;
                case 4:
                    iconRedId = R.drawable.ic_cow_4;
                    break;
                case 5:
                    iconRedId = R.drawable.ic_cow_5;
                    break;
                case 6:
                    iconRedId = R.drawable.ic_cow_6;
                    break;
                case 7:
                    iconRedId = R.drawable.ic_cow_7;
                    break;
                case 8:
                    iconRedId = R.drawable.ic_cow_8;
                    break;
                case 9:
                    iconRedId = R.drawable.ic_cow_9;
                    break;
                default:
                    iconRedId = R.drawable.ic_cow_0;
                    break;
            }
            ivNiuniu.setImageResource(iconRedId);
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
