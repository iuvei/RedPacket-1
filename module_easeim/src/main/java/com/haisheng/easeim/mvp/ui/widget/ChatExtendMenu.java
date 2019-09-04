package com.haisheng.easeim.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.ChatExtendItemEntity;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.view.AutoHeightViewPager;
import me.jessyan.armscomponent.commonres.view.recyclerview.AutoGridLayoutManager;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonres.view.recyclerview.PageIndicatorView;
import me.jessyan.armscomponent.commonsdk.adapter.ViewPagerAdapter;
import me.jessyan.armscomponent.commonsdk.entity.GiftEntity;

public class ChatExtendMenu extends RelativeLayout {

    private AutoHeightViewPager viewPager;
    private PageIndicatorView circleIndicator;

    private Context mContext;
    private LayoutInflater mInflater;


    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<ChatExtendItemEntity> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    private boolean mIsBoy = true;

//    private ChatExtendItemEntity mSelectedEntity;
    private RecyclerView.Adapter mTempAdapter;

    public ChatExtendMenu(Context context) {
        this(context, null);
    }

    public ChatExtendMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatExtendMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultInit(context);
    }

    private void defaultInit(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_chat_extend_menu, this);

        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
    }

    @SuppressLint("StringFormatInvalid")
    public void initData(List<ChatExtendItemEntity> chatExtendItemEntities,OnItemClickListener onItemClickListener){
        listDatas = chatExtendItemEntities;
        mOnItemClickListener = onItemClickListener;

        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            final RecyclerView recyclerView = new RecyclerView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(layoutParams);
            AutoGridLayoutManager autoGridLayoutManager = new AutoGridLayoutManager(mContext,4);
            recyclerView.setLayoutManager(autoGridLayoutManager);
//            recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));

            ChatExtendAdapter adapter = new ChatExtendAdapter(chatExtendItemEntities, i, mPageSize);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(null != mOnItemClickListener)
                        mOnItemClickListener.onItemClick(adapter,view,position);
                }
            });
            adapter.bindToRecyclerView(recyclerView);
            viewPagerList.add(recyclerView);
        }

        circleIndicator.initIndicator(totalPage);
        circleIndicator.setSelectedPage(0);

        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        viewPager.setAdapter(new ViewPagerAdapter(viewPagerList));
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener =new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
//            super.onPageSelected(position);
            circleIndicator.setSelectedPage(position);
        }
    };

//    /**
//     * hide extend menu
//     */
//    public void hideExtendMenuContainer() {
//        emojiconMenu.setVisibility(View.GONE);
//        chatExtendMenuContainer.setVisibility(View.GONE);
//        chatPrimaryMenu.onExtendMenuContainerHide();
//    }

    /**
     * when back key pressed
     *
     * @return false--extend menu is on, will hide it first
     *         true --extend menu is off
     */
    public boolean onBackPressed() {
        if (getVisibility() == View.VISIBLE) {
            setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }

    }


    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }

    public class ChatExtendAdapter extends BaseQuickAdapter<ChatExtendItemEntity, BaseViewHolder> {

        private int mIndex;
        private int mPagerSize;

        public ChatExtendAdapter(@Nullable List<ChatExtendItemEntity> data, int index, int pagerSize) {
            super(R.layout.item_chat_extend, data);
            mIndex = index;
            mPagerSize = pagerSize;
        }

        @Override
        protected void convert(BaseViewHolder helper, ChatExtendItemEntity item) {
            if(item != null){
                helper.setText(R.id.tvName,item.getName())
                .setImageResource(R.id.imgIcon,item.getImgResId());
            }
        }

        @Override
        public int getItemCount() {
            return mData.size() > (mIndex + 1) * mPagerSize ?
                    mPagerSize : (mData.size() - mIndex*mPagerSize);
        }

        @Nullable
        @Override
        public ChatExtendItemEntity getItem(int position) {
            return mData.get(position + mIndex * mPagerSize);
        }
    }
}
