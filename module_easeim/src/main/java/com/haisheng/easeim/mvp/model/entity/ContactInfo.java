package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

public class ContactInfo {

    @SerializedName("server")
    private List<UserInfo> customerServiceUsers;
    @SerializedName("myagent")
    private List<UserInfo> inviteMyUsers;
    @SerializedName("mydownlist")
    private List<UserInfo> myInviteUsers;

    public List<UserInfo> getCustomerServiceUsers() {
        return customerServiceUsers;
    }

    public void setCustomerServiceUsers(List<UserInfo> customerServiceUsers) {
        this.customerServiceUsers = customerServiceUsers;
    }

    public List<UserInfo> getInviteMyUsers() {
        return inviteMyUsers;
    }

    public void setInviteMyUsers(List<UserInfo> inviteMyUsers) {
        this.inviteMyUsers = inviteMyUsers;
    }

    public List<UserInfo> getMyInviteUsers() {
        return myInviteUsers;
    }

    public void setMyInviteUsers(List<UserInfo> myInviteUsers) {
        this.myInviteUsers = myInviteUsers;
    }
}
