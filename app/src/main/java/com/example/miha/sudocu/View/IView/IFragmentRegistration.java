package com.example.miha.sudocu.View.IView;

import android.app.Activity;

import com.example.miha.sudocu.data.model.User;



public interface IFragmentRegistration {
    void onAttach(Activity activity);
    void onDetach();
    void onLogin(User user);
}
