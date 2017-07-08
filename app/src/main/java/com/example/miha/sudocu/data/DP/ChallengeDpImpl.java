package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

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
        Gson g = new Gson();
        instance.addChallenge(g.toJson(grid)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callbacks.onSuccess();
                } else {
                    callbacks.onError("error");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                 callbacks.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getAllScore(final ChallengeDPGetAllScoreCallbacks callbacks) {
        instance.getAllScore().enqueue(new Callback<ArrayList<Challenge>>() {
            @Override
            public void onResponse(Call<ArrayList<Challenge>> call, Response<ArrayList<Challenge>> response) {
                if (response.isSuccessful()) {
                    callbacks.onSuccess(response.body());
                } else {
                    callbacks.onError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Challenge>> call, Throwable t) {
                callbacks.onError();
            }
        });
    }
}
