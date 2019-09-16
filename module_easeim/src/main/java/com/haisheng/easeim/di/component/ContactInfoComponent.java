package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.ContactInfoModule;
import com.haisheng.easeim.mvp.contract.ContactInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haisheng.easeim.mvp.ui.activity.ContactInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 15:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ContactInfoModule.class, dependencies = AppComponent.class)
public interface ContactInfoComponent {
    void inject(ContactInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContactInfoComponent.Builder view(ContactInfoContract.View view);

        ContactInfoComponent.Builder appComponent(AppComponent appComponent);

        ContactInfoComponent build();
    }
}