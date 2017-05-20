package com.example.miha.sudocu.data.DP;

import android.util.Log;

import com.example.miha.sudocu.data.model.Challenge;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChallengeDpImpl implements ChallengeDP {

    @Override
    public void getAllScore(final ChallengeDPCallbacks callbacks) {
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
