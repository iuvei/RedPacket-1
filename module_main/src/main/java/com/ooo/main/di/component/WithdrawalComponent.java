package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.WithdrawalModule;
import com.ooo.main.mvp.contract.WithdrawalContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.WithdrawalActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 11:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WithdrawalModule.class, dependencies = AppComponent.class)
public interface WithdrawalComponent {
    void inject(WithdrawalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawalComponent.Builder view(WithdrawalContract.View view);

        WithdrawalComponent.Builder appComponent(AppComponent appComponent);

        WithdrawalComponent build();
    }
}