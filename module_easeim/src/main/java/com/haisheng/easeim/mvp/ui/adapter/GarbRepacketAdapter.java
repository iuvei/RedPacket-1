package com.haisheng.easeim.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.hyphenate.easeui.bean.GarbRedpacketBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class GarbRepacketAdapter extends BaseQuickAdapter <GarbRedpacketBean, BaseViewHolder> {

    private boolean getAll;
    private String uid;

    public GarbRepacketAdapter(List<GarbRedpacketBean> infos) {
        super( R.layout.item_garb_redpacket, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, GarbRedpacketBean item) {
        Integer niuniuNumber = item.getNiuniuNumber();
        ImageView ivAvatar = helper.getView( R.id.iv_avatar);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),ivAvatar);
        helper.setText( R.id.tv_nickname,item.getNickname())
                .setText( R.id.tv_time,item.getTime())
                .setVisible( R.id.iv_banker,item.getBankerStatus()!=0)
                .setVisible( R.id.iv_best, item.getBestStatus()!=0);

        TextView tvMoney = helper.getView( R.id.tv_money);
//        String sMoney = String.format("%.2f",item.getMoney());
        String sMoney = item.getMoney();

        String money;
        if (isGetAll ()){
            money = sMoney;
            helper.setVisible( R.id.iv_bomb,item.getBombStatus() !=0);
        }else{
            if (isNiuNiu){
                //牛牛房
                if (TextUtils.isEmpty ( item.getId () ) && item.getNickname ().equals ( "免死" )){
                    //免死抢包
                    money = "*.**";
                }else if (uid.equals ( blankId )) {
                    //自己是庄家
                    if (uid.equals ( item.getId () )) {
                        //自己抢的包
                        money = sMoney.substring ( 0, sMoney.length () - 1 ) + "*";
                    }else{
                        //别人抢的包
                        money = sMoney;
                       // niuniuNumber = -1;
                    }
                }else{
                    //自己不是庄家
                    if (blankId.equals ( item.getId () )) {
                        //庄家抢的包
                        money = sMoney.substring ( 0, sMoney.length () - 1 ) + "*";
                    }else{
                        //闲家抢的包
                        money = sMoney;
                    }
                }
            }else{
                //不是牛牛房
                if (TextUtils.isEmpty ( item.getId () ) && item.getNickname ().equals ( "免死" )){
                    //免死抢包
                    money = "*.**";
                }else if (uid.equals ( item.getId () )){
                    //自己抢的包
                    money = sMoney;
                }else{
                    //别人抢包
                    money = "0.00";
                }
            }
        }
        helper.setText( R.id.tv_money,money);
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
                case 10:
                    iconRedId = R.drawable.ic_cow_10;
                    break;
            }
            Drawable drawable = mContext.getResources().getDrawable(iconRedId);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvMoney.setCompoundDrawables(null,null,drawable,null);
        }

    }

    public boolean isGetAll() {
        return getAll;
    }

    public void setGetAll(boolean getAll) {
        this.getAll = getAll;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isNiuNiu;

    public boolean isNiuNiu() {
        return isNiuNiu;
    }

    public void setNiuNiu(boolean niuNiu) {
        isNiuNiu = niuNiu;
    }

    private String blankId = "";
    //设置牛牛庄家id

    public String getBlankId() {
        return blankId;
    }

    public void setBlankId(String blankId) {
        this.blankId = blankId;
    }
}
