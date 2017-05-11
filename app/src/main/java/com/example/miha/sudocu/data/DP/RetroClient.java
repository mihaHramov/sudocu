package com.example.miha.sudocu.data.DP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static RecordScore system;

    public static RecordScore getInstance() {//типа синглтон
        if (system == null) {
            Retrofit retrofit =
                    new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(RecordScore.URL).build();//базовый url
            system = retrofit.create(RecordScore.class); // интерфейс api с которой будим работать
        }
        return system;
    }
}
