package com.epicodus.friendlocator;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by jakemento on 5/8/16.
 */
public class FriendLocatorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
