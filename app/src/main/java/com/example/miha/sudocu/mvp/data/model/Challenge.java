package com.example.miha.sudocu.mvp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Challenge {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("grid")
    @Expose
    private Grid grid;

    public Challenge(String login, String password, Grid grid) {
        this.login = login;
        this.password = password;
        this.grid = grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public String getLogin() {
        return login;
    }
    public Grid getGrid(){
        return grid;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
