package com.example.phy.hochiminhcity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;

/**
 * Created by Phy on 6/19/2017.
 */

public class RecommendItem {
    private String name;
    private String type;
    private String address, phoneNo;
    private String hours;
    double latitude, longitude;
    private Bitmap avatar;
    boolean bookmark;
    public RecommendItem(String name, String type, String address, Bitmap avatar) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.avatar = avatar;
    }

    public RecommendItem(String name, String type, String address, String phoneNo, double latitude, double longitude, String hours, Bitmap avatar) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.phoneNo = phoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avatar = avatar;
        this.hours = hours;
        this.bookmark = false;
    }

    public RecommendItem(String name, String address, String phoneNo, double latitude, double longitude, String hours, Bitmap avatar) {
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avatar = avatar;
        this.hours = hours;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

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

    public boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
