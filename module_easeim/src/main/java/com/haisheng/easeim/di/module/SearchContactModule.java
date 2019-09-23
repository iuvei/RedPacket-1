package com.haisheng.easeim.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.contract.CustomerServiceListContract;
import com.haisheng.easeim.mvp.contract.SearchContactContract;
import com.haisheng.easeim.mvp.model.ChatRoomModel;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.ui.adapter.UserListAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 11:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class SearchContactModule {
//
//    @Binds
//    abstract SearchContactContract.Model bindSearchContactModel(SearchContactModel model);

    @ActivityScope
    @Provides
    public ContactModel provideContactModel(IRepositoryManager iRepositoryManager) {
        return new ContactModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SearchContactContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<UserInfo> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static UserListAdapter provideUserListAdapter(List<UserInfo> list){
        return new UserListAdapter(list);
    }
}
