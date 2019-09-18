package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.ContactFriendModule;
import com.ooo.main.mvp.contract.ContactFriendContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.ContactFriendActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 19:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ContactFriendModule.class, dependencies = AppComponent.class)
public interface ContactFriendComponent {
    void inject(ContactFriendActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContactFriendComponent.Builder view(ContactFriendContract.View view);

        ContactFriendComponent.Builder appComponent(AppComponent appComponent);

        ContactFriendComponent build();
    }
}