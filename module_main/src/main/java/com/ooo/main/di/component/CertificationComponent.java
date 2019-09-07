package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.CertificationModule;
import com.ooo.main.mvp.contract.CertificationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.CertificationActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CertificationModule.class, dependencies = AppComponent.class)
public interface CertificationComponent {
    void inject(CertificationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CertificationComponent.Builder view(CertificationContract.View view);

        CertificationComponent.Builder appComponent(AppComponent appComponent);

        CertificationComponent build();
    }
}