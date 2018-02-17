package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.api.ChallengeApi;
import com.example.miha.sudocu.data.DP.intf.ILogin;
import com.example.miha.sudocu.data.model.User;

import java.util.Map;

import rx.Observable;


public class Login implements ILogin {
    private ChallengeApi instance;
    public Login(ChallengeApi instance){
        this.instance = instance;
    }

    @Override
    public Observable<User> login(Map<String,String> loginParams) {
       return instance.login(loginParams);
    }
}
