package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerAddBlankCardComponent;
import com.ooo.main.mvp.contract.AddBlankCardContract;
import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.presenter.AddBlankCardPresenter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.utils.RegExpValidateUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 18:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddBlankCardActivity extends BaseSupportActivity <AddBlankCardPresenter> implements AddBlankCardContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_chooseBlank)
    TextView tvChooseBlank;
    @BindView(R2.id.et_card_num)
    EditText etCardNum;
    @BindView(R2.id.et_lower_bank)
    EditText etLowerBank;
    @BindView(R2.id.rb_yes)
    RadioButton rbYes;
    @BindView(R2.id.rb_no)
    RadioButton rbNo;
    @BindView(R2.id.ll_blank)
    LinearLayout llBlank;
    @BindView(R2.id.et_alipy)
    EditText etAlipy;
    @BindView(R2.id.ll_alipy)
    LinearLayout llAlipy;
    @BindView(R2.id.btn_next)
    Button btnNext;
    @BindView(R2.id.rg_cardtype)
    RadioGroup rgCardtype;
    @BindView(R2.id.iv_delect_blankName)
    ImageView ivDelectBlankName;
    @BindView(R2.id.iv_delect_cardNum)
    ImageView ivDelectCardNum;
    @BindView(R2.id.iv_delect_alipyNum)
    ImageView ivDelectAlipyNum;
    private OptionsPickerView pvCustomOptions;
    private ArrayList <String> cardItem = new ArrayList <> ();
    private BaseDialog dialog;

    /**
     * 添加成功后刷新银行卡列表
     * {@link BlankCardActivity#addBlankCard(com.ooo.main.mvp.model.entity.AddBlankCardBean)}
     * {@link WithdrawalActivity#addBlankCard(com.ooo.main.mvp.model.entity.BlankCardBean.ResultBean)}
     * @param addBlankCardBean
     */
    @Override
    public void getAddBlankCardSuccess(AddBlankCardBean addBlankCardBean) {
        finish ();
        EventBus.getDefault ().post ( addBlankCardBean,"getAddBlankCardSuccess" );
        EventBus.getDefault ().post ( addBlankCardBean,"addBlankCard" );
    }

    @Override
    public void getAddBlankCardFail() {

    }

    enum BLANKTYPE {
        //没选中
        NONE,
        //支付宝
        ALIPY,
        //银行
        BLANK
    }

    enum CARDTYPE {
        //没选中
        NONE(0),
        //借记卡
        PAYCARD(1),
        //不是借记卡
        UNPAYCARD(2);
        int value;
        CARDTYPE(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

    private BLANKTYPE blanktype = BLANKTYPE.NONE;
    private CARDTYPE cardtype = CARDTYPE.NONE;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddBlankCardComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_blank_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "添加银行卡" );
        setListener ();
    }

    private void setListener() {
        rgCardtype.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int i1 = radioGroup.getCheckedRadioButtonId ();
                if (i1 == R.id.rb_yes) {
                    cardtype = CARDTYPE.PAYCARD;
                } else if (i1 == R.id.rb_no) {
                    cardtype = CARDTYPE.UNPAYCARD;
                }
            }
        } );
        etLowerBank.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etLowerBank.getText ().toString ().trim ().length ()>0){
                    ivDelectBlankName.setVisibility ( View.VISIBLE );
                }else{
                    ivDelectBlankName.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        etCardNum.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etCardNum.getText ().toString ().trim ().length ()>0){
                    ivDelectCardNum.setVisibility ( View.VISIBLE );
                }else{
                    ivDelectCardNum.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        etAlipy.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etAlipy.getText ().toString ().trim ().length ()>0){
                    ivDelectAlipyNum.setVisibility ( View.VISIBLE );
                }else{
                    ivDelectAlipyNum.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        ButterKnife.bind ( this );
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.tv_chooseBlank, R2.id.btn_next,R2.id.iv_delect_alipyNum,
            R2.id.iv_delect_blankName,R2.id.iv_delect_cardNum})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_chooseBlank) {
            //选择银行
            initCustomOptionPicker ();
            pvCustomOptions.show ();
        } else if (i == R.id.btn_next) {
            //下一步
            switch (blanktype) {
                case ALIPY:
                    String alipyNum = etAlipy.getText ().toString ().trim ();
                    if (TextUtils.isEmpty ( alipyNum )) {
                        ToastUtils.showShort ( "请输入支付宝账号" );
                    } else {
                        //检验支付宝账号
                        if (RegexUtils.isEmail ( alipyNum ) || RegexUtils.isMobileSimple ( alipyNum )) {
                            //通过验证
                            showCheckDialog ();
                        } else {
                            //支付宝账号不对
                            ToastUtils.showShort ( "支付宝账号格式不正确" );
                        }
                    }
                    break;
                case NONE:
                    ToastUtils.showShort ( "请选择银行" );
                    break;
                case BLANK:
                    String underBlankName = etLowerBank.getText ().toString ().trim ();
                    String cardNum = etCardNum.getText ().toString ().trim ();
                    if (TextUtils.isEmpty ( underBlankName )) {
                        ToastUtils.showShort ( "请输入支行名称" );
                        return;
                    }
                    if (TextUtils.isEmpty ( cardNum )) {
                        ToastUtils.showShort ( "请输入卡号" );
                        return;
                    } else {
                        if (!RegExpValidateUtils.checkBankCard ( cardNum )) {
                            ToastUtils.showShort ( "卡号格式不正确" );
                            return;
                        }
                    }
                    switch (cardtype) {
                        case NONE:
                            ToastUtils.showShort ( "请选择是否为借记卡" );
                            return;
                        case PAYCARD:
                            cardtype = CARDTYPE.PAYCARD;
                            break;
                        case UNPAYCARD:
                            cardtype = CARDTYPE.UNPAYCARD;
                            break;
                        default:
                            break;
                    }
                    showCheckDialog ();
                    break;
                default:
                    break;
            }
        }else if(i == R.id.iv_delect_alipyNum){
            etAlipy.setText ( "" );
        }else if (i==R.id.iv_delect_blankName){
            etLowerBank.setText ( "" );
        }else if (i==R.id.iv_delect_cardNum){
            etCardNum.setText ( "" );
        }
    }

    private void showCheckDialog() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                        switch (blanktype) {
                            case ALIPY:
                                mPresenter.addBlankCard ( "",etAlipy.getText ().toString ().trim (),"支付宝","","1" );
                                break;
                            case BLANK:
                                String blankName = tvChooseBlank.getText ().toString ().trim ();
                                String underBlankName = etLowerBank.getText ().toString ().trim ();
                                String cardNum = etCardNum.getText ().toString ().trim ();
                                mPresenter.addBlankCard ( "12",cardNum,blankName,underBlankName,cardtype.getValue ()+"" );
                                break;
                        }
                    }
                } );
                layout.findViewById ( R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
            }
        } )
                .create ();
        dialog.show ();
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder ( this, new OnOptionsSelectListener () {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get ( options1 );
                tvChooseBlank.setText ( tx );
                if (options1 == 0) {
                    //选中支付宝
                    llAlipy.setVisibility ( View.VISIBLE );
                    llBlank.setVisibility ( View.GONE );
                    blanktype = BLANKTYPE.ALIPY;
                    cardtype = CARDTYPE.PAYCARD;
                } else {
                    //选中其他银行
                    llBlank.setVisibility ( View.VISIBLE );
                    llAlipy.setVisibility ( View.GONE );
                    blanktype = BLANKTYPE.BLANK;
                }
            }
        } )
                .setLayoutRes ( R.layout.item_choose_blank, new CustomListener () {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById ( R.id.tv_finish );
                        TextView ivCancel = (TextView) v.findViewById ( R.id.tv_cancel );
                        tvSubmit.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData ();
                                pvCustomOptions.dismiss ();
                            }
                        } );

                        ivCancel.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss ();
                            }
                        } );
                    }
                } )
                .setLineSpacingMultiplier ( 2.5f )
                .build ();
        getCardData ();
        pvCustomOptions.setPicker ( cardItem );//添加数据


    }

    private void getCardData() {
        cardItem.add ( "支付宝" );
        cardItem.add ( "中国工商银行" );
        cardItem.add ( "中国邮政储蓄银行" );
        cardItem.add ( "中国农业银行" );
        cardItem.add ( "中国建设银行" );
        cardItem.add ( "中国银行" );
        cardItem.add ( "中国民生银行" );
        cardItem.add ( "招商银行" );
        cardItem.add ( "兴业银行" );
        cardItem.add ( "中信银行" );
        cardItem.add ( "浦发银行" );
        cardItem.add ( "中国光大银行" );
        cardItem.add ( "广发银行" );
        cardItem.add ( "中国交通银行" );
        cardItem.add ( "平安银行" );
        cardItem.add ( "华夏银行" );
        cardItem.add ( "北京银行" );
        cardItem.add ( "杭州银行" );
        cardItem.add ( "汇丰银行" );
        cardItem.add ( "上海银行" );
        cardItem.add ( "天津银行" );
        cardItem.add ( "南京银行" );
        cardItem.add ( "东莞银行" );
        cardItem.add ( "宁波银行" );
        cardItem.add ( "无锡银行" );
        cardItem.add ( "恒丰银行" );
        cardItem.add ( "武汉银行" );
        cardItem.add ( "长沙银行" );
        cardItem.add ( "大连银行" );
        cardItem.add ( "西安银行" );
        cardItem.add ( "重庆银行" );
        cardItem.add ( "济南银行" );
        cardItem.add ( "成都银行" );
        cardItem.add ( "贵阳银行" );
        cardItem.add ( "石家庄银行" );
        cardItem.add ( "昆明银行" );
        cardItem.add ( "烟台银行" );
        cardItem.add ( "哈尔滨银行" );
        cardItem.add ( "郑州银行" );
        cardItem.add ( "乌鲁木齐银行" );
        cardItem.add ( "青岛银行" );
        cardItem.add ( "温州银行" );
        cardItem.add ( "合肥银行" );
        cardItem.add ( "淄博银行" );
        cardItem.add ( "苏州银行" );
        cardItem.add ( "太原银行" );
        cardItem.add ( "绍兴银行" );
        cardItem.add ( "台州银行" );
        cardItem.add ( "浙商银行" );
        cardItem.add ( "临沂银行" );
        cardItem.add ( "鞍山银行" );
        cardItem.add ( "潍坊银行" );
    }

}
