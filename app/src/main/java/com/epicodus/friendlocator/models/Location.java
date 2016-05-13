package com.epicodus.friendlocator.models;

/**
 * Created by Guest on 5/11/16.
 */
public class Location {
    String address;
    private String pushId;

    public Location(){}

    public Location(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
