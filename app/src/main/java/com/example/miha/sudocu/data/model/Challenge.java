package com.example.miha.sudocu.data.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Challenge {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("password")
    @Expose
    private String password;




    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
