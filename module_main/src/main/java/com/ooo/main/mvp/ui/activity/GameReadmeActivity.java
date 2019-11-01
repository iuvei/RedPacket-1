package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerGameReadmeComponent;
import com.ooo.main.mvp.contract.GameReadmeContract;
import com.ooo.main.mvp.presenter.GameReadmePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.ui.WebviewActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/20/2019 18:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GameReadmeActivity extends BaseActivity <GameReadmePresenter> implements GameReadmeContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_poster)
    ImageView ivPoster;
    private int type;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGameReadmeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_game_readme; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        type = getIntent ().getIntExtra ( "type",0 );
        switch (type){
            case 0:
                tvTitle.setText ( "扫雷游戏奖励说明" );
                break;
            case 1:
                tvTitle.setText ( "佣金排行榜奖励说明" );
                break;
            case 2:
                tvTitle.setText ( "禁抢游戏说明" );
                break;
            case 3:
                tvTitle.setText ( "牛牛游戏说明" );
                break;
            default:
                break;
        }
        mPresenter.getGameRule ();
    }

    public static void start(Context context,int type){
        Intent intent = new Intent ( context,GameReadmeActivity.class );
        intent.putExtra ( "type",type );
        context.startActivity ( intent );
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {
        finish ();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish ();
    }

    @Override
    public void getGameRuleSuccess(List <String> result) {
        if (result!=null){
            switch (type){
                case 0:
                     //"扫雷游戏奖励说明"
                    if (result.size ()<1){
                        ToastUtils.showShort ( "没有详细资料" );
                        return;
                    }
                    Glide.with ( this ).load ( result.get ( 0 ) ).into ( ivPoster );
                    WebviewActivity.start ( this,"扫雷游戏奖励说明",result.get ( 0 ) );
                    break;
                case 1:
                    // "佣金排行榜奖励说明" );
                    if (result.size ()<4){
                        ToastUtils.showShort ( "没有详细资料" );
                        return;
                    }
                    Glide.with ( this ).load ( result.get ( 3 ) ).into ( ivPoster );
                    break;
                case 2:
                    //"禁抢游戏说明" );
                    if (result.size ()<2){
                        ToastUtils.showShort ( "没有详细资料" );
                        return;
                    }
                    Glide.with ( this ).load ( result.get ( 1 ) ).into ( ivPoster );
                    break;
                case 3:
                    //"牛牛游戏说明" );
                    if (result.size ()<3){
                        ToastUtils.showShort ( "没有详细资料" );
                        return;
                    }
                    Glide.with ( this ).load ( result.get ( 2 ) ).into ( ivPoster );
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void getGameRuleFail() {

    }
}
