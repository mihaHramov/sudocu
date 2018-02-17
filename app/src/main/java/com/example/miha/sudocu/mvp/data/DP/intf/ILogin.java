package com.example.miha.sudocu.mvp.data.DP.intf;

import com.example.miha.sudocu.mvp.data.model.User;

import java.util.Map;

import rx.Observable;

public interface ILogin {
    Observable<User> login(Map<String,String> params);
}
