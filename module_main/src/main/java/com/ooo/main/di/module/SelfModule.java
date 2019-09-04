package com.ooo.main.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.jess.arms.integration.IRepositoryManager;
import com.ooo.main.mvp.contract.SelfContract;
import com.ooo.main.mvp.model.MemberModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class SelfModule {

//    @Binds
//    abstract SelfContract.Model bindSelfModel(SelfModel model);

    @FragmentScope
    @Provides
    public MemberModel provideLoginModel(IRepositoryManager iRepositoryManager) {
        return new MemberModel(iRepositoryManager);
    }
}