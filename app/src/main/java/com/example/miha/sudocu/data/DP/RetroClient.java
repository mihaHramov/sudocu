package com.example.miha.sudocu.data.DP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static ChallengeApi system;

    public static ChallengeApi getInstance() {
        if (system == null) {
            Retrofit retrofit =
                    new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(ChallengeApi.URL).build();//базовый url
            system = retrofit.create(ChallengeApi.class); // интерфейс api с которой будим работать
        }
        return system;
    }
}
