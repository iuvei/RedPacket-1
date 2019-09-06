package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.ChooseHeadImgModule;
import com.ooo.main.mvp.contract.ChooseHeadImgContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.ChooseHeadImgActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseHeadImgModule.class, dependencies = AppComponent.class)
public interface ChooseHeadImgComponent {
    void inject(ChooseHeadImgActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseHeadImgComponent.Builder view(ChooseHeadImgContract.View view);

        ChooseHeadImgComponent.Builder appComponent(AppComponent appComponent);

        ChooseHeadImgComponent build();
    }
}