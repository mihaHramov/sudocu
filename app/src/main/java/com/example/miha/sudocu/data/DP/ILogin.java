package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.User;

import java.util.Map;

import rx.Observable;

public interface ILogin {
    Observable<User> login(Map params);
}
