package me.jessyan.armscomponent.commonsdk.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/5.
 */

public class LocationEntity implements Serializable {

    private double latitude;
    private double longitude;

    private String address;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
