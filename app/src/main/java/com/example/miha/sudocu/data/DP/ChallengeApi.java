package com.example.miha.sudocu.data.DP;


import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.MyClass;
import com.example.miha.sudocu.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ChallengeApi {
    String URL = "http://mysimple.zzz.com.ua/";

    @GET("getAllScore")
    Call<ArrayList<Challenge>> getAllScore();// метод к которому будим обращатся

    @GET("login")
    Call<User> login(@QueryMap Map<String, String> options);

    /*    @POST("addChallenge")
        Call<Object> addChallenge(@Body Grid first);
    */
    @POST("addChallenge")
    Call<MyClass> addChallenge(@Body  MyClass params);
}
