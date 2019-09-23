package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.SearchContactModule;
import com.haisheng.easeim.mvp.contract.SearchContactContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.SearchContactActivity;


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
@ActivityScope
@Component(modules = SearchContactModule.class, dependencies = AppComponent.class)
public interface SearchContactComponent {
    void inject(SearchContactActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchContactComponent.Builder view(SearchContactContract.View view);

        SearchContactComponent.Builder appComponent(AppComponent appComponent);

        SearchContactComponent build();
    }
}
