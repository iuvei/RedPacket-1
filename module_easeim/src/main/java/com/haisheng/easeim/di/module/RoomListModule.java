package com.haisheng.easeim.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;

import com.haisheng.easeim.mvp.contract.RoomListContract;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.haisheng.easeim.mvp.model.entity.RoomBean;
import com.haisheng.easeim.mvp.ui.adapter.RoomListAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;
//import com.haisheng.easeim.mvp.model.GroupModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 21:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class RoomListModule {

//    @Binds
//    abstract RoomListContract.Model bindGroupModel(GroupModel model);

    @FragmentScope
    @Provides
    public RedpacketModel provideRedpacketModel(IRepositoryManager iRepositoryManager) {
        return new RedpacketModel(iRepositoryManager);
    }

    @FragmentScope
    @Provides
    public ChatRoomModel provideChatRoomModel(IRepositoryManager iRepositoryManager) {
        return new ChatRoomModel(iRepositoryManager);
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(RoomListContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<RoomBean> provideRoomBeanList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static RoomListAdapter provideRoomListAdapter(List<RoomBean> list){
        return new RoomListAdapter(list);
    }

}