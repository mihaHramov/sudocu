package com.example.miha.sudocu.data.DP;


import retrofit2.Call;
import retrofit2.http.GET;


public interface RecordScore {
    String URL = "http://mysimple.zzz.com.ua/";

    @GET("getAllScore")
    Call<Object> getAllScore();// метод к которому будим обращатся
}
