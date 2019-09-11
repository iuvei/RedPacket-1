package com.ooo.main.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.CommissionBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/11
 * description
 */
public class CommissionAdapter extends BaseListAdapter<CommissionBean> {

    public CommissionAdapter(List <CommissionBean> list) {
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
        holder.tvNum.setText ( (position+1)+"" );
        holder.tvNickName.setText ( list.get ( position ).getNickname () );
        holder.tvCommission.setText ( list.get ( position ).getCommission () );
        return convertView;
    }

    class ViewHolder{
        TextView tvCommission;
        TextView tvNickName;
        TextView tvNum;
        LinearLayout llItem;
    }

}
