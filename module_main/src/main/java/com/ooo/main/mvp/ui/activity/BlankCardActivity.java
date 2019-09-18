package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerBlankCardComponent;
import com.ooo.main.mvp.contract.BlankCardContract;
import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.presenter.BlankCardPresenter;
import com.ooo.main.mvp.ui.adapter.BlankCradAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BlankCardActivity extends BaseSupportActivity <BlankCardPresenter> implements BlankCardContract.View {

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.lv_card)
    ListView lvCard;
    private BlankCradAdapter adapter;
    private BaseDialog dialog;
    private ArrayList <BlankCardBean.ResultBean> lists;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBlankCardComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_blank_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "我的银行卡" );
        ivRight.setVisibility ( View.VISIBLE );
        ivRight.setImageResource ( R.mipmap.icon_vip );
        lists = new ArrayList <> (  );
        getBlankCard ();
        adapter = new BlankCradAdapter ( lists );
        lvCard.setAdapter ( adapter );
        /**
         * 选择银行卡
         * {@link WithdrawalActivity#chooseBlankCard(com.ooo.main.mvp.model.entity.BlankCardBean.ResultBean)}
         */
        lvCard.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int position, long l) {
                BlankCardBean.ResultBean resultBean = (BlankCardBean.ResultBean) lvCard.getItemAtPosition ( position );
                if (!resultBean.getType ().equals ( "1" )){
                    ToastUtils.showShort ( "不能提现到信用卡" );
                    return;
                }
                EventBus.getDefault ().post ( resultBean,"chooseBlankCard" );
                BlankCardActivity.this.finish ();
            }
        } );
        lvCard.setOnItemLongClickListener ( new AdapterView.OnItemLongClickListener () {
            @Override
            public boolean onItemLongClick(AdapterView <?> adapterView, View view, int i, long l) {
                showDelectCardDialog ( l);
                return true;
            }
        } );
    }

    //删除银行卡
    private void showDelectCardDialog(long position) {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_delect_blankcard, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvBlankType = layout.findViewById ( R.id.tv_blankType );
                TextView tvCancel = layout.findViewById ( R.id.tv_cancel );
                TextView tvSure = layout.findViewById ( R.id.tv_sure );
                tvCancel.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
                tvSure.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        getBlankCard();
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }


    public void getBlankCard(){
        mPresenter.getBlankCardList ();
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
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }

    /**
     * 添加银行卡成功后刷新银行卡列表
     * {@link AddBlankCardActivity#getAddBlankCardSuccess(com.ooo.main.mvp.model.entity.AddBlankCardBean)}
     * @param addBlankCardBean
     */
    @Subscriber(tag = "getAddBlankCardSuccess")
    public void addBlankCard(AddBlankCardBean addBlankCardBean){
        getBlankCard();
    }

    @OnClick({R2.id.iv_back, R2.id.iv_right})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_right) {
            //添加
            openActivity ( AddBlankCardActivity.class );
        }
    }

    @Override
    public void getBlankCardSuccess(List <BlankCardBean.ResultBean> result) {
        adapter.setData ( result );
    }

    @Override
    public void getBlankCardFail() {

    }
}
