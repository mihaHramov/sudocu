package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.api.ChallengeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetWorkModule {

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    public OkHttpClient provieOkhttpClient(HttpLoggingInterceptor interceptor){
          return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor(){
       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return   interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    public ChallengeApi provideChallengeApi(OkHttpClient client,Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ChallengeApi.URL)
                .client(client).build();//базовый url
        return retrofit.create(ChallengeApi.class);
    }
}