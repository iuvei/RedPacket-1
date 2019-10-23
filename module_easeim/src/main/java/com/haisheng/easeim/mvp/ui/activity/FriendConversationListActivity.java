package com.haisheng.easeim.mvp.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.haisheng.easeim.mvp.ui.fragment.ConversationListFragment;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class FriendConversationListActivity extends BaseSupportActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_friend_conversation_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loadRootFragment(R.id.fl_content, ConversationListFragment.newInstance(IMConstants.CONVERSATION_FRIEND));
    }
}
