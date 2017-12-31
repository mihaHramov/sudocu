package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.view.intf.IFragmentRegistration;

public interface IPresenterRegistration {
    void login(String login,String password);
    void setView(IFragmentRegistration fr);
}
