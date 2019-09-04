package com.ooo.main.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.jess.arms.integration.IRepositoryManager;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.ToolModel;


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
@Module
public class MemberInfoModule {

//    @Binds
//    abstract MemberInfoContract.Model bindMemberInfoModel(MemberInfoModel model);
    @ActivityScope
    @Provides
    public MemberModel provideLoginModel(IRepositoryManager iRepositoryManager) {
        return new MemberModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public ToolModel provideToolModel(IRepositoryManager iRepositoryManager) {
        return new ToolModel(iRepositoryManager);
    }

}