package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.app.MainConstants;
import com.ooo.main.di.component.DaggerMemberInfoComponent;
import com.ooo.main.mvp.contract.MemberInfoContract;
import com.ooo.main.mvp.model.entity.MemberInfo;
import com.ooo.main.mvp.presenter.MemberInfoPresenter;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ActionUtils;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.popupwindow.SelectItemPopupWindow;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.utils.MyFileUtils;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.ooo.main.app.MainConstants.REQUEST_CODE_EDIT_NICKNAME;


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
public class MemberInfoActivity extends BaseSupportActivity<MemberInfoPresenter> implements MemberInfoContract.View {


    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.tv_sex)
    TextView tvSex;
    @BindView(R2.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.btn_save)
    Button btnSave;

    private MemberInfo mMemberInfo;

    private static final String mSexs[] = {"男","女"};
    private static final String mPictureFrom[] = {"相册","拍照"};
    private String mCameraSavePath,mCropSavePath;
    private ProgressDialogUtils progressDialogUtils;


    public static void start(Activity context, MemberInfo memberInfo) {
        Intent intent = new Intent(context, MemberInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("memberInfo", memberInfo);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,1);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMemberInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_member_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mMemberInfo = (MemberInfo) bundle.getSerializable("memberInfo");
            if(null != mMemberInfo)
                refreshMemberInfo(mMemberInfo);
        }
    }

    @OnClick({R2.id.ll_head_portrait, R2.id.ll_nickname, R2.id.ll_sex, R2.id.ll_qrcode,R2.id.btn_save})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ll_head_portrait) {
            showSelectPicturePopupWindow(view);

        } else if (i == R.id.ll_nickname) {
            Intent intent = new Intent(mContext, EditNicknameActivity.class);
            startActivityForResult(intent,REQUEST_CODE_EDIT_NICKNAME);
//            EditNicknameActivity.start(mContext);

        } else if (i == R.id.ll_sex) {
            showSelectSexPopupWindow(view);

        } else if (i == R.id.ll_qrcode) {
            QrcodeCardActivity.start(mContext,mMemberInfo);

        }else if(i == R.id.btn_save){
            mPresenter.updateMemberInfo(mMemberInfo);
        }
    }

    @Override
    public void refreshMemberInfo(MemberInfo memberInfo) {
        UserPreferenceManager.getInstance().setCurrentUserAvatarUrl(memberInfo.getAvatarUrl());
        UserPreferenceManager.getInstance().setCurrentUserNick(memberInfo.getNickname());
        ImageLoader.displayHeaderImage(mContext,memberInfo.getAvatarUrl(),ivAvatar);
        tvNickname.setText(memberInfo.getNickname());
        tvSex.setText(memberInfo.getSex()==MemberInfo.FAMALE  ? "女" : "男");
        tvPhoneNumber.setText(memberInfo.getPhoneNumber());
        ImageLoader.displayImage(mContext,memberInfo.getInviteCodeUrl(),ivQrcode);
    }

    @Override
    public void uploadImgSuccessfully(String imgUrl) {
        mMemberInfo.setAvatarUrl(imgUrl);
        ImageLoader.displayHeaderImage(mContext,imgUrl,ivAvatar);
    }

    @Override
    public void saveSuccessfully() {
        setResult(RESULT_OK);
        killMyself();
    }

    private SelectItemPopupWindow mSelectSexPopupWindow;
    private void showSelectSexPopupWindow(View view){
        if(null == mSelectSexPopupWindow){
            mSelectSexPopupWindow = new SelectItemPopupWindow<String>(mContext, Arrays.asList(mSexs), (adapter, v, position) -> {
                String sex = (String) adapter.getItem(position);
                tvSex.setText(sex);
                int sexStatus = position == 0 ? 1 : 0;
                mMemberInfo.setSex(sexStatus);

            }) {
                @Override
                public void setItemInfo(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_content,item);
                }
            };
        }
        mSelectSexPopupWindow.openPopWindow(view);
    }

    private SelectItemPopupWindow mSelectPicturePopupWindow;
    private void showSelectPicturePopupWindow(View view){
        if(null == mSelectPicturePopupWindow){
            mSelectPicturePopupWindow = new SelectItemPopupWindow<String>(mContext, Arrays.asList(mPictureFrom), (adapter, v, position) -> {
                if (position == 0) {
                    ActionUtils.openSystemAblum((Activity) mContext);
                } else {
                    mCameraSavePath = MyFileUtils.getNewCacheFilePath(mContext, Constants.IMAGE_CODE);
                    ActionUtils.openCamera(mContext, mCameraSavePath);
                }
            }) {
                @Override
                public void setItemInfo(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_content,item);
                }
            };
        }
        mSelectPicturePopupWindow.openPopWindow(view);
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage(getString(R.string.public_loading));
        }
        if (show) {
            progressDialogUtils.show();
        } else {
            progressDialogUtils.dismiss();
        }
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(null != message)
            ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) return;
        if (requestCode == Constants.REQUEST_CODE_SYSTEM_ALBUM) {
            mCropSavePath = MyFileUtils.getNewCacheFilePath(mContext,Constants.IMAGE_CODE);
            Uri desUri = Uri.fromFile(new File(mCropSavePath));
            ActionUtils.cropImageUri(mContext,data.getData(),desUri,1,1,300,300);

        } else if (requestCode == Constants.REQUEST_CODE_CAMERA) {
            Uri uri = MyFileUtils.getUriForFile(new File(mCameraSavePath));
            mCropSavePath = MyFileUtils.getNewCacheFilePath(mContext,Constants.IMAGE_CODE);
            Uri desUri = Uri.fromFile(new File(mCropSavePath));
            ActionUtils.cropImageUri(mContext,uri,desUri,1,1,300,300);

        }  else if (requestCode == Constants.REQUEST_CODE_CLIP) {
            mPresenter.updateAvatarUrl(mCropSavePath);

        } else if(requestCode == MainConstants.REQUEST_CODE_EDIT_NICKNAME){
            Bundle bundle = data.getExtras();
            if(null != bundle){
                String nickname = bundle.getString("nickname");
                mMemberInfo.setNickname(nickname);
                tvNickname.setText(nickname);
            }
        }
    }

}
