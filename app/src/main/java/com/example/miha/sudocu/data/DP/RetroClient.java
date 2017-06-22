package com.example.miha.sudocu.data.DP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static ChallengeApi system;

    public static ChallengeApi getInstance() {
        if (system == null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit =
                    new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                            .baseUrl(ChallengeApi.URL)
                            .client(client).build();//базовый url
            system = retrofit.create(ChallengeApi.class); // интерфейс api с которой будим работать
        }
        return system;
    }
}
