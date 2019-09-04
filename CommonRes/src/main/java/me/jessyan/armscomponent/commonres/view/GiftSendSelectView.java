package me.jessyan.armscomponent.commonres.view;

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

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.view.recyclerview.AutoGridLayoutManager;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonres.view.recyclerview.PageIndicatorView;
import me.jessyan.armscomponent.commonsdk.adapter.ViewPagerAdapter;
import me.jessyan.armscomponent.commonsdk.entity.GiftEntity;

public class GiftSendSelectView extends RelativeLayout {

    private TextView tvRest,tvRecharge,tvSend;
    private AutoHeightViewPager viewPager;
    private PageIndicatorView circleIndicator;

    private Context mContext;
    private LayoutInflater mInflater;


    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<GiftEntity> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    private boolean mIsBoy = true;

    private GiftEntity mSelectedEntity;
    private RecyclerView.Adapter mTempAdapter;

    public GiftSendSelectView(Context context) {
        this(context, null);
    }

    public GiftSendSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftSendSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultInit(context);
    }

    private void defaultInit(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_gift_send_select, this);

        tvRest = findViewById(R.id.tvRest);
        tvRecharge = findViewById(R.id.tvRecharge);
        tvSend = findViewById(R.id.tvSend);

        if(!mIsBoy){
            tvRest.setVisibility(INVISIBLE);
            tvRecharge.setVisibility(INVISIBLE);
            tvSend.setText(R.string.public_please_enjoy);
        }
        tvRest.setOnClickListener(mOnClickListener);
        tvRecharge.setOnClickListener(mOnClickListener);
        tvSend.setOnClickListener(mOnClickListener);

        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
    }

    @SuppressLint("StringFormatInvalid")
    public void initData(int restCoin, List<GiftEntity> giftEntities){
        listDatas = giftEntities;
        tvRest.setText(String.format(mContext.getString(R.string.public_rest_coin),restCoin));

        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            final RecyclerView recyclerView = new RecyclerView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(layoutParams);
            AutoGridLayoutManager autoGridLayoutManager = new AutoGridLayoutManager(mContext,4);
            recyclerView.setLayoutManager(autoGridLayoutManager);
//            recyclerView.addItemDecoration(new DividerGridItemDecoration(ContextCompat.getDrawable(mContext,R.drawable.public_divider_bg)));
            recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));

            GiftAdapter adapter = new GiftAdapter(giftEntities, i, mPageSize);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mSelectedEntity = (GiftEntity) adapter.getItem(position);
                    if(mTempAdapter == null){
                        mTempAdapter = adapter;
                    }else{
                        if(mTempAdapter != adapter){
                            mTempAdapter.notifyDataSetChanged();
                            mTempAdapter = adapter;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    ToastUtils.showShort(mSelectedEntity.getName());
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

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.tvRecharge){
                if(null != mOnGiftSelectClickListener)
                    mOnGiftSelectClickListener.onRecharge();
            }else if(id == R.id.tvSend){
                if(null == mSelectedEntity){
                    ToastUtils.showShort(mContext.getString(R.string.public_please_select_gift));
                    return;
                }
                if(null != mOnGiftSelectClickListener)
                    mOnGiftSelectClickListener.onSend(mSelectedEntity);
            }
        }
    };

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


    private OnGiftSelectClickListener mOnGiftSelectClickListener;
    public void setOnGiftSelectClickListener(OnGiftSelectClickListener onGiftSelectClickListener){
        this.mOnGiftSelectClickListener = onGiftSelectClickListener;
    }
    public interface OnGiftSelectClickListener {
        void onSend(GiftEntity entity);
        void onRecharge();
    }

    public class GiftAdapter extends BaseQuickAdapter<GiftEntity,BaseViewHolder>{

        private int mIndex;
        private int mPagerSize;

        public GiftAdapter( @Nullable List<GiftEntity> data, int index, int pagerSize) {
            super(R.layout.item_gift_select, data);
            mIndex = index;
            mPagerSize = pagerSize;
        }

        @Override
        protected void convert(BaseViewHolder helper, GiftEntity item) {
            if(item != null){
                helper.setText(R.id.tvName,item.getName());
                helper.setText(R.id.tvNeedCoin,String.valueOf(item.getNeedCoin()));

                View view = helper.getConvertView();
                if(mSelectedEntity != null && item.getId() == mSelectedEntity.getId()){
                    view.setSelected(true);
                }else{
                    view.setSelected(false);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mData.size() > (mIndex + 1) * mPagerSize ?
                    mPagerSize : (mData.size() - mIndex*mPagerSize);
        }

        @Nullable
        @Override
        public GiftEntity getItem(int position) {
            return mData.get(position + mIndex * mPagerSize);
        }
    }
}
