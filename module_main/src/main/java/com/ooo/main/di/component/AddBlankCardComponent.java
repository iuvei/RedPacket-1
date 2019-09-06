package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.AddBlankCardModule;
import com.ooo.main.mvp.contract.AddBlankCardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.AddBlankCardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 18:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddBlankCardModule.class, dependencies = AppComponent.class)
public interface AddBlankCardComponent {
    void inject(AddBlankCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddBlankCardComponent.Builder view(AddBlankCardContract.View view);

        AddBlankCardComponent.Builder appComponent(AppComponent appComponent);

        AddBlankCardComponent build();
    }
}