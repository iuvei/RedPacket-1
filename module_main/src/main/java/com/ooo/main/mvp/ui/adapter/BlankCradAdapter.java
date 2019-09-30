package com.ooo.main.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.BlankCardBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;

/**
 * 0
 * creat at 2019/9/6
 * description
 */
public class BlankCradAdapter extends BaseListAdapter <BlankCardBean.ResultBean> {

    public BlankCradAdapter(List <BlankCardBean.ResultBean> list) {
        super ( list );
    }

    public void removerItemForPosition(long position){
        if (list!=null && list.size ()>position){
            list.remove ( list.get ( (int) position ) );
            notifyDataSetInvalidated ();
        }

    }
    public void setData(List <BlankCardBean.ResultBean> list){
        this.list = list;
        notifyDataSetChanged ();
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
        if(list.get ( position ).getType ().equals ( "1" )) {
            holder.tvBlankCardType.setText ( "借记卡" );
        }else{
            holder.tvBlankCardType.setText ( "" );
        }
        holder.tvCardNum.setText ( list.get ( position ).getCardcodeSecret () );
        holder.tvBlankName.setText ( list.get ( position ).getCardopen () );
        switch (list.get ( position ).getCardopen ()){
            case "支付宝":
                //支付宝
                holder.rlBlank.setBackgroundResource ( R.mipmap.alipay_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.alipay );
                holder.tvBlankName.setText ( "支付宝" );
                break;
            case "中国工商银行":
                //工商
                holder.rlBlank.setBackgroundResource ( R.mipmap.gongshang_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.gongshang );
                holder.tvBlankName.setText ( "中国工商银行" );
                break;
            case "中国邮政储蓄银行":
                //邮政
                holder.rlBlank.setBackgroundResource ( R.mipmap.youzheng_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.youzheng );
                holder.tvBlankName.setText ( "中国邮政储蓄银行" );
                break;
            case "中国农业银行":
                //农业
                holder.rlBlank.setBackgroundResource ( R.mipmap.nongye_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.nongye );
                holder.tvBlankName.setText ( "中国农业银行" );
                break;
            case "中国建设银行":
                //建设
                holder.rlBlank.setBackgroundResource ( R.mipmap.jianshe_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.jianshe );
                holder.tvBlankName.setText ( "中国建设银行" );
                break;
            case "中国银行":
                //中国银行
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhongguo_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhongguo );
                holder.tvBlankName.setText ( "中国银行" );
                break;
            case "中国民生银行":
                //民生
                holder.rlBlank.setBackgroundResource ( R.mipmap.mengsheng_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.mengsheng );
                holder.tvBlankName.setText ( "中国民生银行" );
                break;
            case "招商银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhaoshang_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhaoshang );
                holder.tvBlankName.setText ( "招商银行" );
                break;
            case "兴业银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.xingye_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.xingye );
                holder.tvBlankName.setText ( "兴业银行" );
                break;
            case "中信银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.zhongxin_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.zhongxin );
                holder.tvBlankName.setText ( "中信银行" );
                break;
            case "浦发银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.pufa_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.pufa );
                holder.tvBlankName.setText ( "浦发银行" );
                break;
            case "中国光大银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.guangda_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.guangda );
                holder.tvBlankName.setText ( "中国光大银行" );
                break;
            case "广发银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.guangfa_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.guangfa );
                holder.tvBlankName.setText ( "广发银行" );
                break;
            case "中国交通银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.jiaotong_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.jiaotong );
                holder.tvBlankName.setText ( "中国交通银行" );
                break;
            case "平安银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.pingan_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.pingan );
                holder.tvBlankName.setText ( "平安银行" );
                break;
            case "华夏银行":
                holder.rlBlank.setBackgroundResource ( R.mipmap.huaxia_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.huaxia );
                holder.tvBlankName.setText ( "华夏银行" );
                break;
            default:
                holder.rlBlank.setBackgroundResource ( R.mipmap.yinlian_bg );
                holder.ivBlankLogo.setImageResource ( R.mipmap.yinlian );
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
