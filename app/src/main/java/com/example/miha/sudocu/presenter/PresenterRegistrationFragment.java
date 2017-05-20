package com.example.miha.sudocu.presenter;


import android.util.Log;

import com.example.miha.sudocu.View.IView.IFragment;
import com.example.miha.sudocu.data.DP.ILogin;
import com.example.miha.sudocu.data.DP.Login;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;

import java.util.Hashtable;
import java.util.Map;

public class PresenterRegistrationFragment implements IPresenterRegistration {
    private ILogin loginInSystem = new Login();
    private IFragment view;
    private ILogin.OnLogin onLogin = new ILogin.OnLogin() {
        @Override
        public void onLogin() {
            view.callbacks();
        }

        @Override
        public void onError() {
            Log.d("errorConnect","mama");
        }
    };

    public  PresenterRegistrationFragment(IFragment fragment){
        view = fragment;
    }

    @Override
    public void login(String login,String password) {
        Map<String,String> loginParams = new Hashtable<>();
        loginParams.put("login",login);
        loginParams.put("password",password);
        loginInSystem.login(loginParams,onLogin);
    }
}
