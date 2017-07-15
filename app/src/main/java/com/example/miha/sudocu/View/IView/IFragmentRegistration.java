package com.example.miha.sudocu.View.IView;

import com.example.miha.sudocu.data.model.User;



public interface IFragmentRegistration {
    void onFailAuth(String error);
    void onLogin(User user);
}
