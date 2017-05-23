package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.User;

import java.util.Map;

public interface ILogin {
    interface OnLogin {
        void onLogin(User user);
        void onError();
    }

    void login(Map params, OnLogin onLogin);
}
