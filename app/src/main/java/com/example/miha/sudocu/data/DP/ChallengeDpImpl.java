package com.example.miha.sudocu.data.DP;

import android.util.Log;

import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChallengeDpImpl implements ChallengeDP {

    @Override
    public void sendGame(User user, Grid grid, final ChallengeDPSendGameCallbacks callbacks) {
        Gson gson = new Gson();
        Map<String ,String> map = new Hashtable<>();
        map.put("grid",gson.toJson(grid));
        map.put("user",gson.toJson(user));
        RetroClient.getInstance().addChallenge(map).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.d("mihaHramov","success send game to server");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

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
