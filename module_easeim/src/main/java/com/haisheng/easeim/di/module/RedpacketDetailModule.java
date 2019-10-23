package com.haisheng.easeim.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haisheng.easeim.mvp.contract.RedpacketDetailContract;
import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.hyphenate.easeui.bean.GarbRedpacketBean;
import com.haisheng.easeim.mvp.ui.adapter.GarbRepacketAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 21:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class RedpacketDetailModule {

    //    @Binds
//    abstract RedpacketDetailContract.Model bindRedpacketDetailModel(RedpacketDetailModel model);
    @ActivityScope
    @Provides
    public RedpacketModel provideRedpacketModel(IRepositoryManager iRepositoryManager) {
        return new RedpacketModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(RedpacketDetailContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<GarbRedpacketBean> provideGarbRedpacketList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static GarbRepacketAdapter provideGarbRepacketAdapter(List<GarbRedpacketBean> list){
        return new GarbRepacketAdapter(list);
    }

}
