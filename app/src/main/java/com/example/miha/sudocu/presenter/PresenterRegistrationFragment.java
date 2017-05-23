package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.View.IView.IFragmentRegistration;
import com.example.miha.sudocu.data.DP.ILogin;
import com.example.miha.sudocu.data.DP.Login;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;

import java.util.Hashtable;
import java.util.Map;

public class PresenterRegistrationFragment implements IPresenterRegistration {
    private ILogin loginInSystem = new Login();
    private IFragmentRegistration view;

    private ILogin.OnLogin onLogin = new ILogin.OnLogin() {
        @Override
        public void onLogin(User user) {
            if (user != null) {
                view.onLogin(user);
            }
        }

        @Override
        public void onError() {
        }
    };

    public PresenterRegistrationFragment(IFragmentRegistration fragment) {
        view = fragment;
    }

    @Override
    public void login(String login, String password) {
        Map<String, String> loginParams = new Hashtable<>();
        loginParams.put("login", login);
        loginParams.put("password", password);
        loginInSystem.login(loginParams, onLogin);
    }
}
