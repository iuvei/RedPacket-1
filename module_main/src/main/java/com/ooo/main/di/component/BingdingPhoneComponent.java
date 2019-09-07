package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.BingdingPhoneModule;
import com.ooo.main.mvp.contract.BingdingPhoneContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.BingdingPhoneActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 17:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BingdingPhoneModule.class, dependencies = AppComponent.class)
public interface BingdingPhoneComponent {
    void inject(BingdingPhoneActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BingdingPhoneComponent.Builder view(BingdingPhoneContract.View view);

        BingdingPhoneComponent.Builder appComponent(AppComponent appComponent);

        BingdingPhoneComponent build();
    }
}