package com.haisheng.easeim.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;

import me.jessyan.armscomponent.commonsdk.entity.ILetter;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

public class ContactAdapter extends BaseQuickAdapter<UserInfo,BaseViewHolder>  implements SectionIndexer {

    List<String> list;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;

    public ContactAdapter(@Nullable List<UserInfo> data) {
        super(R.layout.item_contact, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        int type = item.getType();
        String letter = item.getInitialLetter();
        int position = helper.getAdapterPosition();

        TextView tvHeaderType = helper.getView(R.id.tv_header_type);
        TextView tvHeaderLetter = helper.getView(R.id.tv_header_letter);
        if(type == UserInfo.TYPE_CUSTOMER_SERVICE){
            tvHeaderType.setText(R.string.official_customer_service);
            tvHeaderLetter.setVisibility(View.GONE);


        }else if(type == UserInfo.TYPE_INVITE_MY_FRIEND){
            tvHeaderType.setText(R.string.invite_my_friend);
            tvHeaderLetter.setVisibility(View.GONE);

        }else{
            tvHeaderType.setText(R.string.invite_my_friend);
            if (position == 0 || letter != null && !letter.equals(getItem(position - 1).getLetter())) {
                if (TextUtils.isEmpty(letter)) {
                    tvHeaderLetter.setVisibility(View.GONE);
                } else {
                    tvHeaderLetter.setVisibility(View.VISIBLE);
                    tvHeaderLetter.setText(letter);
                }
            } else {
                tvHeaderLetter.setVisibility(View.GONE);
            }
        }
        if(position == 0 || type!= getItem(position-1).getType()){
            tvHeaderType.setVisibility(View.VISIBLE);
        }else{
            tvHeaderType.setVisibility(View.GONE);
        }

        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),ivAvatar);
        helper.setText(R.id.tv_nickname,item.getNickname());

    }

    @Override
    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getItemCount();
        list = new ArrayList<String>();
        list.add("搜");
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = getItem(i);
            int type = userInfo.getType();
            if(type == UserInfo.TYPE_MY_INVITE_FRIEND){
                String letter = userInfo.getLetter();
                int section = list.size() - 1;
                if (list.get(section) != null && !list.get(section).equals(letter)) {
                    list.add(letter);
                    section++;
                    positionOfSection.put(section, i);
                }
                sectionOfPosition.put(i, section);
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
