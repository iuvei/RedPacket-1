package com.haisheng.easeim.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.contract.ContactContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.haisheng.easeim.mvp.ui.adapter.ContactAdapter;
import com.haisheng.easeim.mvp.ui.adapter.UserListAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import com.haisheng.easeim.mvp.contract.CustomerServiceListContract;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;
//import com.haisheng.easeim.mvp.model.CustomerServiceListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class CustomerServiceListModule {

    //    @Binds
//    abstract CustomerServiceListContract.Model bindCustomerServiceListModel(CustomerServiceListModel model);
    @ActivityScope
    @Provides
    public ContactModel provideContactModel(IRepositoryManager iRepositoryManager) {
        return new ContactModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CustomerServiceListContract.View view) {
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
