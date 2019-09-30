package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * creat at 2019/9/7
 * description 提现记录实体类
 */
public class WithdrawalRecordBean implements Parcelable {
    private String takeMoney;
    private String accountMoney;
    private int statue;
    private String takeMoneyTime;
    private String blankAccount;

    public String getTakeMoney() {
        return takeMoney;
    }

    public void setTakeMoney(String takeMoney) {
        this.takeMoney = takeMoney;
    }

    public String getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getStatue() {
        //return statue;
        return "已完成";
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getTakeMoneyTime() {
        return takeMoneyTime;
    }

    public void setTakeMoneyTime(String takeMoneyTime) {
        this.takeMoneyTime = takeMoneyTime;
    }

    public String getBlankAccount() {
        return blankAccount;
    }

    public void setBlankAccount(String blankAccount) {
        this.blankAccount = blankAccount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.takeMoney );
        dest.writeString ( this.accountMoney );
        dest.writeInt ( this.statue );
        dest.writeString ( this.takeMoneyTime );
        dest.writeString ( this.blankAccount );
    }

    public WithdrawalRecordBean() {
    }

    protected WithdrawalRecordBean(Parcel in) {
        this.takeMoney = in.readString ();
        this.accountMoney = in.readString ();
        this.statue = in.readInt ();
        this.takeMoneyTime = in.readString ();
        this.blankAccount = in.readString ();
    }

    public static final Parcelable.Creator <WithdrawalRecordBean> CREATOR = new Parcelable.Creator <WithdrawalRecordBean> () {
        @Override
        public WithdrawalRecordBean createFromParcel(Parcel source) {
            return new WithdrawalRecordBean ( source );
        }

        @Override
        public WithdrawalRecordBean[] newArray(int size) {
            return new WithdrawalRecordBean[size];
        }
    };
}
