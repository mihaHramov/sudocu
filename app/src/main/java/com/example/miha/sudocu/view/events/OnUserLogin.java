package com.example.miha.sudocu.view.events;

import com.example.miha.sudocu.data.model.User;


public class OnUserLogin {
    private  User user;
    public OnUserLogin(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
