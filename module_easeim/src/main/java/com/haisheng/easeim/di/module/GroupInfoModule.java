package com.haisheng.easeim.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.contract.ChatContract;
import com.haisheng.easeim.mvp.contract.GroupInfoContract;
import com.haisheng.easeim.mvp.contract.RoomListContract;
import com.haisheng.easeim.mvp.model.ChatModel;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.GroupListBean;
import com.haisheng.easeim.mvp.ui.adapter.RoomListAdapter;
import com.haisheng.easeim.mvp.ui.adapter.UserGridAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class GroupInfoModule {

    //    @Binds
//    abstract GroupInfoContract.Model bindRoomInfoModel(RoomInfoModel model);
    @ActivityScope
    @Provides
    public ChatRoomModel provideChatRoomModel(IRepositoryManager iRepositoryManager) {
        return new ChatRoomModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(GroupInfoContract.View view) {
        return new GridLayoutManager(view.getActivity(),5);
    }

    @ActivityScope
    @Provides
    static List<UserInfo> provideUserList() {
        return new ArrayList<>();
    }

}
