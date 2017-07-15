package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.View.IView.IFragmentRegistration;
import com.example.miha.sudocu.data.DP.ILogin;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;

import java.util.Hashtable;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterRegistrationFragment implements IPresenterRegistration {
    private ILogin loginInSystem;
    private IFragmentRegistration view;

    public PresenterRegistrationFragment(IFragmentRegistration fragment,ILogin loginAPI) {
        view = fragment;//внедрил зависимости
        loginInSystem = loginAPI;
    }

    @Override
    public void login(String login, String password) {
        Map<String, String> loginParams = new Hashtable<>();
        loginParams.put("login", login);
        loginParams.put("password", password);
        loginInSystem.login(loginParams)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> view.onLogin(user),
                        e -> view.onFailAuth(e.getMessage()));
    }
}
