package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerChooseHeadImgComponent;
import com.ooo.main.mvp.contract.ChooseHeadImgContract;
import com.ooo.main.mvp.presenter.ChooseHeadImgPresenter;
import com.ooo.main.mvp.ui.adapter.ChooseHeadImgAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChooseHeadImgActivity extends BaseSupportActivity <ChooseHeadImgPresenter> implements ChooseHeadImgContract.View {

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recyclerView_icon)
    RecyclerView recyclerViewIcon;
    @BindView(R2.id.tv_takephoto)
    TextView tvTakephoto;
    @BindView(R2.id.tv_choosephoto)
    TextView tvChoosephoto;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChooseHeadImgComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose_head_img; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "选择头像" );
        int[] imgs = {R.mipmap.icon_select_avatar_first,R.mipmap.avatar_default_01,R.mipmap.avatar_default_02,
                R.mipmap.avatar_default_03,R.mipmap.avatar_default_04,R.mipmap.avatar_default_05,
                R.mipmap.avatar_default_06,R.mipmap.avatar_default_07,R.mipmap.avatar_default_08,
                R.mipmap.avatar_default_09,R.mipmap.avatar_default_10,R.mipmap.avatar_default_11,
                R.mipmap.avatar_default_12,R.mipmap.avatar_default_13,R.mipmap.avatar_default_14,
                R.mipmap.avatar_default_15,R.mipmap.avatar_default_16,R.mipmap.avatar_default_17,
                R.mipmap.avatar_default_18,R.mipmap.avatar_default_19,R.mipmap.avatar_default_20,
                R.mipmap.avatar_default_21,R.mipmap.avatar_default_22,R.mipmap.avatar_default_23,
                R.mipmap.avatar_default_24,R.mipmap.avatar_default_25,R.mipmap.avatar_default_26,
                R.mipmap.avatar_default_27,R.mipmap.avatar_default_28,R.mipmap.avatar_default_29,
                R.mipmap.avatar_default_30,R.mipmap.avatar_default_31,R.mipmap.avatar_default_32,
                R.mipmap.avatar_default_33,R.mipmap.avatar_default_34,R.mipmap.avatar_default_35,
                R.mipmap.avatar_default_36,R.mipmap.avatar_default_37,R.mipmap.avatar_default_38,
                R.mipmap.avatar_default_39,R.mipmap.avatar_default_40,R.mipmap.avatar_default_41,
                R.mipmap.avatar_default_42,R.mipmap.avatar_default_43};
        ChooseHeadImgAdapter recycleAdapter = new ChooseHeadImgAdapter ( this, imgs);
        GridLayoutManager gridManager = new GridLayoutManager (this,4 );
        //设置布局管理器
        recyclerViewIcon.setLayoutManager(gridManager);
        //设置为垂直布局，这也是默认的
        gridManager.setOrientation( OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerViewIcon.setAdapter(recycleAdapter);
        recycleAdapter.setItemClickListener ( new ChooseHeadImgAdapter.ItemClickListener () {
            @Override
            public void onItemClick(int[] data, int position) {
                if (position==0){
                    //拍照
                }else {
                    //选中头像
                    int imgRes = data[position];
                }
            }
        } );
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
    }

    @OnClick({R2.id.iv_back, R2.id.tv_takephoto, R2.id.tv_choosephoto, R2.id.tv_cancel})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        }  else if (i == R.id.tv_takephoto) {
            //拍照
        } else if (i == R.id.tv_choosephoto) {
            //从相册选择
        } else if (i == R.id.tv_cancel) {
            //取消
            finish ();
        }
    }
}
