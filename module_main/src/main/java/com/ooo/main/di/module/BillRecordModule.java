package com.ooo.main.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.jess.arms.integration.IRepositoryManager;
import com.ooo.main.mvp.contract.BillRecordContract;
import com.ooo.main.mvp.model.BillModel;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.ui.adapter.BillRecordAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 16:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class BillRecordModule {

//    @Binds
//    abstract BillRecordContract.Model bindBillRecordModel(BillRecordModel model);

    @ActivityScope
    @Provides
    public BillModel provideBillModel(IRepositoryManager iRepositoryManager) {
        return new BillModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(BillRecordContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<BillBean> provideBillBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static BillRecordAdapter provideBillRecordAdapter(List<BillBean> list){
        return new BillRecordAdapter(list);
    }
}