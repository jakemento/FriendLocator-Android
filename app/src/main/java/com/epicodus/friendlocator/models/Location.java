package com.epicodus.friendlocator.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Guest on 5/11/16.
 */

@JsonIgnoreProperties(ignoreUnknown=true)

public class Location {
    private String pushId;
    String address;


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
