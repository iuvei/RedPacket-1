package com.haisheng.easeim.di.module;

import com.haisheng.easeim.mvp.model.RedpacketModel;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;

import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/25/2019 12:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class RedPacketRecordModule {
    @ActivityScope
    @Provides
    public RedpacketModel provideRedpacketModel(IRepositoryManager iRepositoryManager) {
        return new RedpacketModel(iRepositoryManager);
    }
}