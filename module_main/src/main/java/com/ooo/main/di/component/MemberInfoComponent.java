package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.MemberInfoModule;
import com.ooo.main.mvp.contract.MemberInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.MemberInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MemberInfoModule.class, dependencies = AppComponent.class)
public interface MemberInfoComponent {
    void inject(MemberInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MemberInfoComponent.Builder view(MemberInfoContract.View view);

        MemberInfoComponent.Builder appComponent(AppComponent appComponent);

        MemberInfoComponent build();
    }
}