package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.view.intf.IFragmentRegistration;

public interface IPresenterRegistration {
    void login(String login,String password);
    void setView(IFragmentRegistration fr);
}
