package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BillRecordModule;
import com.ooo.main.mvp.contract.BillRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BillRecordActivity;


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
@ActivityScope
@Component(modules = BillRecordModule.class, dependencies = AppComponent.class)
public interface BillRecordComponent {
    void inject(BillRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BillRecordComponent.Builder view(BillRecordContract.View view);

        BillRecordComponent.Builder appComponent(AppComponent appComponent);

        BillRecordComponent build();
    }
}