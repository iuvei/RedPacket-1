package com.ooo.main.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BillRecordInfo implements Serializable {

    @SerializedName("allmoney")
    private double totalMoney;

    @SerializedName("paytype")
    private List<String> types;

    @SerializedName("list")
    private List<BillBean> billBeans;

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<BillBean> getBillBeans() {
        return billBeans;
    }

    public void setBillBeans(List<BillBean> billBeans) {
        this.billBeans = billBeans;
    }
}
