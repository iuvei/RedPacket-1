package com.ooo.main.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.jess.arms.integration.IRepositoryManager;
import com.ooo.main.mvp.model.OtherModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 10:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class AdNoticeModule {
//
//    @Binds
//    abstract AdNoticeContract.Model bindGameModel(GameModel model);

    @FragmentScope
    @Provides
    public OtherModel provideOtherModel(IRepositoryManager iRepositoryManager) {
        return new OtherModel(iRepositoryManager);
    }
}