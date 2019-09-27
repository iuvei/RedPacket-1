package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.NiuNiuRecordModule;
import com.haisheng.easeim.mvp.contract.NiuNiuRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.NiuNiuRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 19:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NiuNiuRecordModule.class, dependencies = AppComponent.class)
public interface NiuNiuRecordComponent {
    void inject(NiuNiuRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NiuNiuRecordComponent.Builder view(NiuNiuRecordContract.View view);

        NiuNiuRecordComponent.Builder appComponent(AppComponent appComponent);

        NiuNiuRecordComponent build();
    }
}