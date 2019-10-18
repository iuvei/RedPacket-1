package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.MyEarningsActivityModule;
import com.ooo.main.mvp.contract.MyEarningsActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.MyEarningsActivityActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/18/2019 20:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyEarningsActivityModule.class, dependencies = AppComponent.class)
public interface MyEarningsActivityComponent {
    void inject(MyEarningsActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyEarningsActivityComponent.Builder view(MyEarningsActivityContract.View view);

        MyEarningsActivityComponent.Builder appComponent(AppComponent appComponent);

        MyEarningsActivityComponent build();
    }
}