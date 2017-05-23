package com.example.miha.sudocu.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("id")
    private int id = 0;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}