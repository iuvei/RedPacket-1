package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.RedpacketDetailModule;
import com.haisheng.easeim.mvp.contract.RedpacketDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.RedpacketDetailActivity;


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
@ActivityScope
@Component(modules = RedpacketDetailModule.class, dependencies = AppComponent.class)
public interface RedpacketDetailComponent {
    void inject(RedpacketDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RedpacketDetailComponent.Builder view(RedpacketDetailContract.View view);

        RedpacketDetailComponent.Builder appComponent(AppComponent appComponent);

        RedpacketDetailComponent build();
    }
}
