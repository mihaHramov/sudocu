package com.example.miha.sudocu.data.DP;


import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login implements ILogin {

    @Override
    public void login(Map loginParams, final OnLogin onLogin) {
        RetroClient.getInstance().login(loginParams).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    onLogin.onLogin();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                onLogin.onError();
                Log.d("mihaError",t.getMessage());
            }
        });
    }
}
