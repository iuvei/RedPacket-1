package com.haisheng.easeim.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

public class UserListAdapter extends BaseQuickAdapter <UserInfo, BaseViewHolder> {

    public UserListAdapter(@Nullable List<UserInfo> data) {
        super( R.layout.item_user_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        ImageView ivAvatar = helper.getView( R.id.iv_avatar);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),ivAvatar);
        helper.setText( R.id.tv_nickname,item.getNickname());
    }
}