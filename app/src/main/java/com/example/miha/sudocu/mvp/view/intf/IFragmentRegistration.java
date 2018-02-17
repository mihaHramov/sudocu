package com.example.miha.sudocu.mvp.view.intf;

import com.example.miha.sudocu.mvp.data.model.User;



public interface IFragmentRegistration {
    void onFailAuth(String error);
    void onLogin(User user);
}
