package me.jessyan.armscomponent.commonres.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.adapter.ImagePagerAdapter;
import me.jessyan.armscomponent.commonres.view.HackyViewPager;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class ShowImagesActivity extends BaseSupportActivity {

    TextView pagertitle;
    HackyViewPager viewpager;

    private String mTitle;
    private List<String> mImageUrls;

    public static void start(Context mContext, String title, String imageUrl) {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(imageUrl);
        start(mContext,title,imageUrls);
    }

    public static void start(Context mContext, String title, List<String> imageUrls) {
        start(mContext,title,0,imageUrls);
    }

    public static void start(Context mContext, String title,int position, List<String> imageUrls) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("position", position);
        bundle.putStringArrayList("imageUrls", (ArrayList<String>) imageUrls);
        Intent intent = new Intent(mContext, ShowImagesActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_show_images;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        mTitle = bundle.getString("title");
        int position = bundle.getInt("position");
        mImageUrls = bundle.getStringArrayList("imageUrls");


        pagertitle = findViewById(R.id.pagertitle);
        viewpager = findViewById(R.id.viewpager);

        setTitle(mTitle);
        setPagertitle(position);
        ImagePagerAdapter adapter = new ImagePagerAdapter(mContext, mImageUrls);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(position);
        viewpager.addOnPageChangeListener(mOnPageChangeListener);

        pagertitle.setVisibility(mImageUrls.size()>1 ? View.VISIBLE : View.INVISIBLE);
    }

    private void setPagertitle(int position){
        StringBuilder stringBuilder = new StringBuilder()
                .append(position+1).append("/").append(mImageUrls.size());
        pagertitle.setText(stringBuilder.toString());
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            setPagertitle(position);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
