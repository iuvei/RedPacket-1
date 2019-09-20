package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.GameReadmeModule;
import com.ooo.main.mvp.contract.GameReadmeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.GameReadmeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/20/2019 18:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GameReadmeModule.class, dependencies = AppComponent.class)
public interface GameReadmeComponent {
    void inject(GameReadmeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GameReadmeComponent.Builder view(GameReadmeContract.View view);

        GameReadmeComponent.Builder appComponent(AppComponent appComponent);

        GameReadmeComponent build();
    }
}