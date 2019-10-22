package me.jessyan.armscomponent.commonres.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.CustomPopupWindow;

import java.util.HashMap;
import java.util.List;

import me.jessyan.armscomponent.commonres.R;


public abstract class SelectItemPopupWindow<T> {
    private Context mContext;
    private CustomPopupWindow mPopupWindow;

    private TextView tvTitle;
    private RecyclerView recyclerView;
    private Button btnCancel;

    private String mTitle;
    private List<T> mDatas;
    private int mItemLayoutResId;

    public SelectItemPopupWindow(Context context, List<T> datas,OnItemClickListener onItemClickListener){
        this(context,"提示",datas,onItemClickListener);
    }

    public SelectItemPopupWindow(Context context,String title, List<T> datas,OnItemClickListener onItemClickListener) {
        this(context,R.layout.item_text,title,datas,onItemClickListener);
    }

    public SelectItemPopupWindow(Context context,int itemLayoutResId,String title, List<T> datas,OnItemClickListener onItemClickListener){
        mContext = context;
        mItemLayoutResId = itemLayoutResId;
        mTitle = title;
        mDatas = datas;
        mOnItemClickListener = onItemClickListener;

        View contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow_select, null, false);
        mPopupWindow = CustomPopupWindow
                .builder()
                .contentView(contentView)
                .animationStyle(R.style.public_dialog_inout_anim)
                .isWrap(true)
                .customListener(mCustomPopupWindowListener)
                .build();
        mPopupWindow.setOnDismissListener(mOnDismissListener);
    }

    CustomPopupWindow.CustomPopupWindowListener mCustomPopupWindowListener = new CustomPopupWindow.CustomPopupWindowListener() {
        @Override
        public void initPopupView(View contentView) {
            tvTitle = contentView.findViewById(R.id.tv_title);
            tvTitle.setText(mTitle);
            recyclerView = contentView.findViewById(R.id.recyclerView);
            btnCancel = contentView.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(mOnClickListener);

            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

            StrinItemAdapter itemAdapter = new StrinItemAdapter(mDatas);
            recyclerView.setAdapter(itemAdapter);
            itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick ( adapter, view, position );
                    }
                    mPopupWindow.dismiss();
                }
            });
        }
    };

    public abstract void setItemInfo(BaseViewHolder helper, T item);

    public interface OnItemClickListener{
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    class StrinItemAdapter extends BaseQuickAdapter<T,BaseViewHolder>{

        public StrinItemAdapter(@Nullable List<T> data) {
            super(mItemLayoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            setItemInfo(helper,item);
        }
    }

    PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(1.0f);
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if(i == R.id.btn_cancel){
                mPopupWindow.dismiss();
            }
        }
    };

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * 按钮的监听
     * @param v
     */
    public void openPopWindow(View v) {
        setBackgroundAlpha(0.8f);
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //从底部显示
        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

}
