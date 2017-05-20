package com.example.miha.sudocu.data.DP;

import java.util.Map;

public interface ILogin {
    interface OnLogin {
        void onLogin();
        void onError();
    }

    void login(Map params, OnLogin onLogin);
}
