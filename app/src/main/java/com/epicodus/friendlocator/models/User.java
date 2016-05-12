package com.epicodus.friendlocator.models;

/**
 * Created by jakemento on 5/8/16.
 */

public class User {
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) { this.email = email;}

    public String getEmail() {
        return email;
    }
}
