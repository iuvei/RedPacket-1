package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.ContactModule;
import com.haisheng.easeim.mvp.contract.ContactContract;

import com.jess.arms.di.scope.FragmentScope;
import com.haisheng.easeim.mvp.ui.fragment.ContactFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ContactModule.class, dependencies = AppComponent.class)
public interface ContactComponent {
    void inject(ContactFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContactComponent.Builder view(ContactContract.View view);

        ContactComponent.Builder appComponent(AppComponent appComponent);

        ContactComponent build();
    }
}