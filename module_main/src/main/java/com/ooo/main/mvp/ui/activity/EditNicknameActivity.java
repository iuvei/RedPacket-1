package com.ooo.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.MemberInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

import static com.ooo.main.app.MainConstants.REQUEST_CODE_EDIT_NICKNAME;

public class EditNicknameActivity extends BaseSupportActivity implements IView {

    @BindView(R2.id.et_nickname)
    EditText etNickname;

    public static void start(Activity context) {
        Intent intent = new Intent(context, EditNicknameActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivityForResult(intent,REQUEST_CODE_EDIT_NICKNAME);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edit_nickname;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。 TODO Auto-generated
         * method stub
         */
        int i = item.getItemId();
        if (i == R.id.item_save) {
            saveNickname();

        } else if (i == android.R.id.home) {
            onBackPressedSupport();
        }

        return true;
    }

    private void saveNickname(){
        String nickname = etNickname.getText().toString();
        if(TextUtils.isEmpty(nickname)){
            showMessage("昵称不能为空！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("nickname",nickname);
        setResult(RESULT_OK,new Intent().putExtras(bundle));
        finish();
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }
}
