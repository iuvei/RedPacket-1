package com.haisheng.easeim.mvp.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.GarbRedpacketBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class GarbRepacketAdapter extends BaseQuickAdapter <GarbRedpacketBean, BaseViewHolder> {

    public GarbRepacketAdapter(List<GarbRedpacketBean> infos) {
        super( R.layout.item_garb_redpacket, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, GarbRedpacketBean item) {
        ImageView ivAvatar = helper.getView( R.id.iv_avatar);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),ivAvatar);
        helper.setText( R.id.tv_nickname,item.getNickname())
            .setText( R.id.tv_time,item.getTime())
            .setVisible( R.id.iv_banker,item.getBankerStatus()!=0)
            .setVisible( R.id.iv_bomb,item.getBombStatus() !=0)
            .setVisible( R.id.iv_best, item.getBestStatus()!=0);

        TextView tvMoney = helper.getView( R.id.tv_money);
//        String sMoney = String.format("%.2f",item.getMoney());
        String sMoney = item.getMoney();
        if(item.getBombStatus() !=0){
            SpannableString spannableString = new SpannableString(sMoney);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(foregroundColorSpan, sMoney.length()-1, sMoney.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText( R.id.tv_money,spannableString);
        }else{
            helper.setText( R.id.tv_money,sMoney);
        }

        Integer niuniuNumber = item.getNiuniuNumber();
        if(null != niuniuNumber){
            int iconRedId = R.drawable.ic_cow_0;
            switch (niuniuNumber){
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
                 case 0:
                    iconRedId = R.drawable.ic_cow_10;
                    break;
            }
            Drawable drawable = mContext.getResources().getDrawable(iconRedId);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvMoney.setCompoundDrawables(null,null,drawable,null);
        }

    }
}