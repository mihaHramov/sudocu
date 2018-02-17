package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.view.intf.IFragmentRegistration;
import com.example.miha.sudocu.data.DP.intf.ILogin;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;

import java.util.Hashtable;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterRegistrationFragment implements IPresenterRegistration {
    private ILogin loginInSystem;
    private IRepositoryUser repositoryUser;
    private IFragmentRegistration view;

    @Inject
    public PresenterRegistrationFragment( ILogin loginAPI, IRepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
        loginInSystem = loginAPI;
    }
    @Override
    public void setView(IFragmentRegistration fr ){
        this.view = fr;
    }

    @Override
    public void login(String login, String password) {
        Map<String, String> loginParams = new Hashtable<>();
        loginParams.put("login", login);
        loginParams.put("password", password);
        loginInSystem.login(loginParams)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            repositoryUser.setUser(user);
                            view.onLogin(user);
                        }, e -> view.onFailAuth(e.getMessage()));
    }
}
