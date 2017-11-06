package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.User;

import java.util.ArrayList;
import java.util.Map;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface ChallengeApi {
    String URL = "http://mysimple.zzz.com.ua/";//"http://httpbin.org/";

    @GET("getAllScore")
    Observable<ArrayList<Challenge>> getAllScore();// метод к которому будим обращатся

    @GET("login")
    Observable<User> login(@QueryMap Map<String, String> options);

    @POST("addChallenge")
    Observable<Object> addChallenge(@Body Challenge options);
}
