package com.example.miha.sudocu.data.DP;

import android.util.Log;

import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.MyClass;
import com.example.miha.sudocu.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChallengeDpImpl implements ChallengeDP {

    private ChallengeApi instance;

    public ChallengeDpImpl(ChallengeApi instance) {
        this.instance = instance;
    }

    @Override
    public void sendGame(User user, Grid grid, final ChallengeDPSendGameCallbacks callbacks) {
        MyClass myClass = new MyClass();
        myClass.setLast("last");
        myClass.setFirst("first");
        instance.addChallenge(myClass).enqueue(new Callback<MyClass>() {
            @Override
            public void onResponse(Call<MyClass> call, Response<MyClass> response) {
                if (response.isSuccessful()) {
                    Log.d("mihaHramov1", response.body().getLast());
                } else {
                    Log.d("mihaHramov", "error");
                }
            }

            @Override
            public void onFailure(Call<MyClass> call, Throwable t) {
                Log.d("mihaHramov", t.getMessage());
            }
        });
    }

    @Override
    public void getAllScore(final ChallengeDPGetAllScoreCallbacks callbacks) {
        RetroClient.getInstance().getAllScore().enqueue(new Callback<ArrayList<Challenge>>() {
            @Override
            public void onResponse(Call<ArrayList<Challenge>> call, Response<ArrayList<Challenge>> response) {
                if (response.isSuccessful()) {
                    callbacks.onSuccess(response.body());
                } else {
                    Log.d("error", "mihaSuccess");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Challenge>> call, Throwable t) {
                callbacks.onError();
            }
        });
    }

}
