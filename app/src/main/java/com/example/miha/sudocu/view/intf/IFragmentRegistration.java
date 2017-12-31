package com.example.miha.sudocu.view.intf;

import com.example.miha.sudocu.data.model.User;



public interface IFragmentRegistration {
    void onFailAuth(String error);
    void onLogin(User user);
}
