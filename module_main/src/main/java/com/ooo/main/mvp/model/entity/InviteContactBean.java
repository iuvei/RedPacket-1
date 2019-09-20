package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lanjian
 * creat at 2019/9/20
 * description
 * 邀请手机联系人实体类
 */
public class InviteContactBean implements Parcelable {
    /**
     * name : “1”
     * phone : ok
     */

    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.name );
        dest.writeString ( this.phone );
    }

    public InviteContactBean() {
    }

    protected InviteContactBean(Parcel in) {
        this.name = in.readString ();
        this.phone = in.readString ();
    }

    public static final Parcelable.Creator <InviteContactBean> CREATOR = new Parcelable.Creator <InviteContactBean> () {
        @Override
        public InviteContactBean createFromParcel(Parcel source) {
            return new InviteContactBean ( source );
        }

        @Override
        public InviteContactBean[] newArray(int size) {
            return new InviteContactBean[size];
        }
    };
}
