package com.ooo.main.mvp.ui.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.BlankCardBean;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/6
 * description
 */
public class BlankCradAdapter extends BaseListAdapter <BlankCardBean> {

    public BlankCradAdapter(List <BlankCardBean> list) {
        super ( list );
    }

    public void removerItemForPosition(long position){
        if (list!=null && list.size ()>position){
            list.remove ( list.get ( (int) position ) );
            notifyDataSetInvalidated ();
        }

    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder ();
            convertView = View.inflate ( parent.getContext (), R.layout.item_blank_card, null );
            holder.ivBlankLogo = convertView.findViewById ( R.id.iv_blank_logo );
            holder.tvBlankCardType = convertView.findViewById ( R.id.tv_blank_card_type );
            holder.tvBlankName = convertView.findViewById ( R.id.tv_blankName );
            holder.tvCardNum = convertView.findViewById ( R.id.tv_card_num );
            holder.rlBlank = convertView.findViewById ( R.id.rl_blank );
            convertView.setTag ( holder );
        }else {
            holder = (ViewHolder) convertView.getTag ();
        }
        holder.tvBlankCardType.setText ( "借记卡" );
        holder.tvCardNum.setText ( list.get ( position ).getBlankCardNum () );
        holder.tvBlankName.setText ( list.get ( position ).getBlankName () );
        switch (list.get ( position ).getBlankCardType ()){
            case 1:
                //支付宝
                holder.rlBlank.setBackgroundResource ( R.mipmap.alipay_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.alipay );
                holder.tvBlankName.setText ( "支付宝" );
                break;
            case 2:
                //工商
                holder.rlBlank.setBackgroundResource ( R.mipmap.gongshang_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.gongshang );
                holder.tvBlankName.setText ( "中国工商银行" );
                break;
            case 3:
                //邮政
                holder.rlBlank.setBackgroundResource ( R.mipmap.youzheng_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.youzheng );
                holder.tvBlankName.setText ( "中国邮政储蓄银行" );
                break;
            case 4:
                //农业
                holder.rlBlank.setBackgroundResource ( R.mipmap.nongye_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.nongye );
                holder.tvBlankName.setText ( "中国农业银行" );
                break;
            case 5:
                //建设
                holder.rlBlank.setBackgroundResource ( R.mipmap.jianshe_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.jianshe );
                holder.tvBlankName.setText ( "中国建设银行" );
                break;
            case 6:
                //中国银行
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhongguo_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhongguo );
                holder.tvBlankName.setText ( "中国银行" );
                break;
            case 7:
                //民生
                holder.rlBlank.setBackgroundResource ( R.mipmap.mengsheng_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.mengsheng );
                holder.tvBlankName.setText ( "中国民生银行" );
                break;
            case 8:
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhaoshang_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhaoshang );
                holder.tvBlankName.setText ( "招商银行" );
                break;
            case 9:
                holder.rlBlank.setBackgroundResource ( R.mipmap.xingye_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.xingye );
                holder.tvBlankName.setText ( "兴业银行" );
                break;
            case 10:
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhongxin_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhongxin );
                holder.tvBlankName.setText ( "中信银行" );
                break;
            case 11:
                holder.rlBlank.setBackgroundResource ( R.mipmap.pufa_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.pufa );
                holder.tvBlankName.setText ( "浦发银行" );
                break;
            case 12:
                holder.rlBlank.setBackgroundResource ( R.mipmap.guangda_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.guangda );
                holder.tvBlankName.setText ( "中国光大银行" );
                break;
            case 13:
                holder.rlBlank.setBackgroundResource ( R.mipmap.guangfa_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.guangfa );
                holder.tvBlankName.setText ( "广发银行" );
                break;
            case 14:
                holder.rlBlank.setBackgroundResource ( R.mipmap.jiaotong_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.jiaotong );
                holder.tvBlankName.setText ( "中国交通银行" );
                break;
            case 15:
                holder.rlBlank.setBackgroundResource ( R.mipmap.pingan_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.pingan );
                holder.tvBlankName.setText ( "平安银行" );
                break;
            case 16:
                holder.rlBlank.setBackgroundResource ( R.mipmap.huaxia_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.huaxia );
                holder.tvBlankName.setText ( "华夏银行" );
                break;
            default:
                holder.rlBlank.setBackgroundResource ( R.mipmap.yinlian_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.yinlian );
                holder.tvBlankName.setText ( "中国工商银行" );
                break;
        }
        return convertView;
    }

    class ViewHolder {
        ImageView ivBlankLogo;
        TextView tvBlankName;
        TextView tvBlankCardType;
        TextView tvCardNum;
        RelativeLayout rlBlank;
    }
}
