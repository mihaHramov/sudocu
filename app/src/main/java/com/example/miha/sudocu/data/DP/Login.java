package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login implements ILogin {

    @Override
    public void login(Map loginParams, final OnLogin onLogin) {
        RetroClient.getInstance().login(loginParams).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    onLogin.onLogin(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLogin.onError();
            }
        });
    }
}
