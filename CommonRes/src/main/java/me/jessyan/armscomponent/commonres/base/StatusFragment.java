package me.jessyan.armscomponent.commonres.base;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jess.arms.mvp.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.interfaces.IStatusView;

public abstract class StatusFragment<P extends IPresenter> extends BaseSupportFragment<P> implements IStatusView {
    private Unbinder unbinder;

    protected View mView;
    private int mLayoutResId;

    private boolean isShowLoading = true;
    private boolean isShowingContent = false;
    private boolean isShowingError = false;

    protected ViewGroup mSuperRealContent; //命名为了避免其子类中有相同
    protected View mCurrentShowView;

    protected View mLoadingPage,mEmptyPage,mResetErrorPage;

    private ObjectAnimator mShowAnimator;
    private ObjectAnimator mHideAnimator;

    /**
     * 绑定布局
     * @return 布局Id
     */
    public abstract int bindLayout();

    private void setBaseView(@LayoutRes int layoutId) {
        mLayoutResId = layoutId;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setBaseView(bindLayout());
        addStatusPage(inflater,container);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }
    public <V extends View> V findViewById(@IdRes int resId) {
        return (V) mView.findViewById(resId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /**
     * 添加各种状态页
     */
    private void addStatusPage(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.public_status_page, null);
        mView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mSuperRealContent = (FrameLayout) mView.findViewById(R.id.super_real_content);
        inflater.inflate(mLayoutResId, mSuperRealContent, true);

        mEmptyPage = findViewById(R.id.view_empty_page);
        mResetErrorPage = findViewById(R.id.view_reset_error_page);
        mLoadingPage = findViewById(R.id.view_loading_page);
        mCurrentShowView = mLoadingPage;

        mResetErrorPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickErrorLoadData(v);
            }
        });
    }

    public void onClickErrorLoadData(View v) {
        showLoadView();
    }

    public void showEmptyView() {
        showView(mEmptyPage);
        isShowingError = false;
        isShowingContent = false;
        isShowLoading = false;
    }

    public void showErrorView() {
        if (!isShowingError) {
            showView(mResetErrorPage);
            isShowingError = true;
            isShowingError = false;
            isShowLoading = false;
        }
    }

    public void showLoadView() {
        if (!isShowLoading) {
            showView(mLoadingPage);
            isShowingContent = false;
            isShowingError = false;
            isShowLoading = true;
        }
    }

    public void showContent() {
        if (!isShowingContent) {
            showView(mSuperRealContent);
            isShowingContent = true;
            isShowingError = false;
            isShowLoading = false;
        }
    }

    public void showView(View view) {
        hideViewWithAnimation(mCurrentShowView);
        mCurrentShowView = view;
        view.setVisibility(View.VISIBLE);
        showViewWithAnimation(view);
    }

    /**
     * 展示状态页添加动画
     *
     * @param view
     */
    public void showViewWithAnimation(View view) {
        if (mShowAnimator != null) {
            mShowAnimator.end();
            mShowAnimator.cancel();
            mShowAnimator = null;
        }
        mShowAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        mShowAnimator.setDuration(400);
        mShowAnimator.start();
    }

    /**
     * 隐藏状态页添加动画
     *
     * @param view
     */
    public void hideViewWithAnimation(View view) {
        if (mHideAnimator != null) {
            mHideAnimator.end();
            mHideAnimator.cancel();
            mHideAnimator = null;
        }
        mHideAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        mHideAnimator.setDuration(400);
        mHideAnimator.start();
        view.setVisibility(View.GONE);
    }
}
