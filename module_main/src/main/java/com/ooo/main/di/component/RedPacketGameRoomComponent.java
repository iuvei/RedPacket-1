package com.ooo.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ooo.main.di.module.RedPacketGameRoomModule;
import com.ooo.main.mvp.contract.RedPacketGameRoomContract;

import com.jess.arms.di.scope.ActivityScope;
import com.ooo.main.mvp.ui.activity.RedPacketGameRoomActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/17/2019 12:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RedPacketGameRoomModule.class, dependencies = AppComponent.class)
public interface RedPacketGameRoomComponent {
    void inject(RedPacketGameRoomActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RedPacketGameRoomComponent.Builder view(RedPacketGameRoomContract.View view);

        RedPacketGameRoomComponent.Builder appComponent(AppComponent appComponent);

        RedPacketGameRoomComponent build();
    }
}