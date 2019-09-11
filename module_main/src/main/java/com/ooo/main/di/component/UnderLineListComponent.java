package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.UnderLineListModule;
import com.ooo.main.mvp.contract.UnderLineListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.UnderLineListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UnderLineListModule.class, dependencies = AppComponent.class)
public interface UnderLineListComponent {
    void inject(UnderLineListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UnderLineListComponent.Builder view(UnderLineListContract.View view);

        UnderLineListComponent.Builder appComponent(AppComponent appComponent);

        UnderLineListComponent build();
    }
}