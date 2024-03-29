package com.ooo.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.hyphenate.easeui.EaseConstant;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerRedPacketGameRoomComponent;
import com.ooo.main.mvp.contract.RedPacketGameRoomContract;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.presenter.RedPacketGameRoomPresenter;
import com.ooo.main.mvp.ui.adapter.RedPacketGameRoomListAdapter;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.ui.WebviewActivity;
import me.jessyan.armscomponent.commonres.utils.CommonMethod;
import me.jessyan.armscomponent.commonres.utils.ConfigUtil;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class RedPacketGameRoomActivity extends BaseSupportActivity <RedPacketGameRoomPresenter> implements RedPacketGameRoomContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R2.id.lv_room)
    ListView lvRoom;
    private BannerEntity bannerEntity;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRedPacketGameRoomComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_red_packet_game_room; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        bannerEntity = (BannerEntity) getIntent ().getSerializableExtra ( "banner" );
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        if (bannerEntity!=null){
            tvTitle.setText ( bannerEntity.getTitle ());
            getRoomList();
        }
        setListener();
    }

    private void setListener() {
        lvRoom.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int i, long l) {
                RedPacketGameRomeBean.ResultBean itemBean = (RedPacketGameRomeBean.ResultBean) lvRoom.getItemAtPosition ( i );
                mPresenter.roomDetail ( itemBean.getId ()+"","" );
                SpUtils.put ( RedPacketGameRoomActivity.this,itemBean.getHxgroupid ()+"head",itemBean.getSurl () );
            }
        } );
    }

    private void getRoomList() {
        mPresenter.getRoomList ( bannerEntity.getUrl () );
    }

    @Override
    public void showLoading() {

    }

    public static void start(Context context, BannerEntity bannerEntity){
        Intent intent = new Intent ( context,RedPacketGameRoomActivity.class );
        intent.putExtra ( "banner",bannerEntity );
        context.startActivity ( intent );
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

    @OnClick({R2.id.iv_back, R2.id.ll_recharge})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.ll_recharge) {
            //充值
          if(bannerEntity!=null) {
              if (bannerEntity.getUrl ().equals ( "0" )) {
                 //扫雷区";
                 // ChatActivity.start ( this, ConfigUtil.SERVICE_GAME_SAOLEI_ROOM,true );
                  WebviewActivity.start ( this,"在线客服", CommonMethod.PLATFORM_SERVICE );
              } else if (bannerEntity.getUrl ().equals ( "1" )) {
                 // return "禁抢区";
                 // ChatActivity.start ( this, ConfigUtil.SERVICE_GAME_CONTROL_ROOM,true );
                  WebviewActivity.start ( this,"在线客服", CommonMethod.PLATFORM_SERVICE );
              } else if (bannerEntity.getUrl ().equals ( "2" )) {
                  //return "牛牛不翻倍";
                  //ChatActivity.start ( this, ConfigUtil.SERVICE_GAME_NIUNIU_ROME,true );
                  WebviewActivity.start ( this,"在线客服", CommonMethod.PLATFORM_SERVICE );
              } else if (bannerEntity.getUrl ().equals ( "3" )) {
                  //return "牛牛翻倍";
                 // ChatActivity.start ( this, ConfigUtil.SERVICE_GAME_NIUNIU_ROME,true );
                  WebviewActivity.start ( this,"在线客服", CommonMethod.PLATFORM_SERVICE );
              } else if (bannerEntity.getUrl ().equals ( "4" )) {
                  //return "福利区";
                  //ChatActivity.start ( this, ConfigUtil.SERVICE_GAME_FULI_ROOM,true );
                  WebviewActivity.start ( this,"在线客服", CommonMethod.PLATFORM_SERVICE );
              }
          }
        }
    }

    @Override
    public void getRoomListSuccess(RedPacketGameRomeBean redPacketGameRomeBean) {
        RedPacketGameRoomListAdapter adapter = new RedPacketGameRoomListAdapter ( redPacketGameRomeBean.getResult () );
        lvRoom.setAdapter ( adapter );
    }

    @Override
    public void getRoomListFail() {

    }

    @Override
    public void joinRoomSuccessfully(ChatRoomBean result) {
        Bundle bundle = new Bundle (  );
        bundle.putString("userId", result.getHxId ()+"");
        bundle.putInt("chatType", EaseConstant.CHATTYPE_CHATROOM);
        bundle.putSerializable("chatRoomInfo", result);
        ARouter.getInstance ().build ( RouterHub.IM_CHATACTIVITY ).with ( bundle ).navigation ();
    }
}
