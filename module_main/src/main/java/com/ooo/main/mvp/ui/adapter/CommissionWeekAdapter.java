package com.ooo.main.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.RankingBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;

/**
 * @author lanjian
 * creat at 2019/9/11
 * description
 */
public class CommissionWeekAdapter extends BaseListAdapter <RankingBean.ResultBean.WeekBean> {

    public CommissionWeekAdapter(List <RankingBean.ResultBean.WeekBean> list) {
        super ( list );
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate ( parent.getContext (), R.layout.item_commission,null );
            holder = new ViewHolder ();
            holder.tvCommission = convertView.findViewById ( R.id.tv_commission );
            holder.tvNickName = convertView.findViewById ( R.id.tv_nickname );
            holder.tvNum = convertView.findViewById ( R.id.tv_num );
            holder.llItem = convertView.findViewById ( R.id.ll_item );
            convertView.setTag ( holder );
        }else{
            holder = (ViewHolder) convertView.getTag ();
        }
        if (position%2!=0){
            holder.llItem.setBackgroundColor ( Color.parseColor ( "#ececec" ) );
        }else{
            holder.llItem.setBackgroundColor ( Color.parseColor ( "#ffffff" ) );
        }
        holder.tvNum.setText ( list.get ( position ).getRank () +"");
        holder.tvNickName.setText ( list.get ( position ).getUid () );
        holder.tvCommission.setText ( list.get ( position ).getAllmoney () );
        return convertView;
    }

    class ViewHolder{
        TextView tvCommission;
        TextView tvNickName;
        TextView tvNum;
        LinearLayout llItem;
    }

}
