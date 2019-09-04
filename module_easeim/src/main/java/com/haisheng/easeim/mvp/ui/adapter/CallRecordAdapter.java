package com.haisheng.easeim.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.hyphenate.util.DateUtils;

import java.util.Date;
import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

import static com.haisheng.easeim.mvp.model.entity.CallRecordEntity.CallTypeStatus.VOICE;

public class CallRecordAdapter extends BaseQuickAdapter<CallRecordEntity,BaseViewHolder> {

    public CallRecordAdapter(List<CallRecordEntity> infos) {
        super(R.layout.item_call_record, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, CallRecordEntity item) {

        helper.setText(R.id.tvUsername,item.getNickname());
        helper.setText(R.id.tvTime,DateUtils.getTimestampString(new Date(item.getTimestamp())));

        helper.setText(R.id.tvMessage,item.getMessage());

        helper.setImageResource(R.id.imgType, item.getType() == VOICE.ordinal()
                ? R.mipmap.news_icon_call : R.mipmap.news_icon_video);
        ImageView imgHead = helper.getView(R.id.imgHead);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),imgHead);
    }

}
