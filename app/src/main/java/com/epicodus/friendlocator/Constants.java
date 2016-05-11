package com.epicodus.friendlocator;

/**
 * Created by Guest on 4/29/16.
 */
public class Constants {

    public static final String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;

    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATION_USERS = "users";

    public static final String FIREBASE_PROPERTY_EMAIL = "email";

    public static final String KEY_UID = "UID";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    public static final String FIREBASE_LOCATION_SAVED_LOCATION = "savedLocation";
    public static final String FIREBASE_URL_SAVED_LOCATION = FIREBASE_URL + "/" + FIREBASE_LOCATION_SAVED_LOCATION;
}
