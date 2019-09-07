package com.ooo.main.mvp.model.entity;

/**
 * @author lanjian
 * creat at 2019/9/7
 * description 提现记录实体类
 */
public class WithdrawalRecordBean {
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
}
