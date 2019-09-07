package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.WithdrawalRecordModule;
import com.ooo.main.mvp.contract.WithdrawalRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.WithdrawalRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/07/2019 14:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WithdrawalRecordModule.class, dependencies = AppComponent.class)
public interface WithdrawalRecordComponent {
    void inject(WithdrawalRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawalRecordComponent.Builder view(WithdrawalRecordContract.View view);

        WithdrawalRecordComponent.Builder appComponent(AppComponent appComponent);

        WithdrawalRecordComponent build();
    }
}