package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.PostersModule;
import com.ooo.main.mvp.contract.PostersContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.PostersActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/20/2019 16:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PostersModule.class, dependencies = AppComponent.class)
public interface PostersComponent {
    void inject(PostersActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PostersComponent.Builder view(PostersContract.View view);

        PostersComponent.Builder appComponent(AppComponent appComponent);

        PostersComponent build();
    }
}