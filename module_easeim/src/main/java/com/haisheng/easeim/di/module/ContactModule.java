package com.haisheng.easeim.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.ui.adapter.ContactAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

import com.haisheng.easeim.mvp.contract.ContactContract;
import com.haisheng.easeim.mvp.model.ContactModel;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class ContactModule {
//
//    @Binds
//    abstract ContactContract.Model bindContactModel(ContactModel model);

    @FragmentScope
    @Provides
    public ContactModel provideContactModel(IRepositoryManager iRepositoryManager) {
        return new ContactModel(iRepositoryManager);
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ContactContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<UserInfo> provideUserList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static ContactAdapter provideContactAdapter(List<UserInfo> list){
        return new ContactAdapter(list);
    }

}
