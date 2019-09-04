package com.haisheng.easeim.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haisheng.easeim.di.module.RoomListModule;
import com.haisheng.easeim.mvp.contract.RoomListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.haisheng.easeim.mvp.ui.fragment.RoomListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 21:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = RoomListModule.class, dependencies = AppComponent.class)
public interface RoomListComponent {
    void inject(RoomListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RoomListComponent.Builder view(RoomListContract.View view);

        RoomListComponent.Builder appComponent(AppComponent appComponent);

        RoomListComponent build();
    }
}