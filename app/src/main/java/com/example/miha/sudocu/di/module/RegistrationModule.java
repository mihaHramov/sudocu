package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.api.ChallengeApi;
import com.example.miha.sudocu.mvp.data.DP.Login;
import com.example.miha.sudocu.mvp.data.DP.intf.ILogin;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationModule {

    @Provides
    @Singleton
    public ILogin provideLogin(ChallengeApi api){
        return new Login(api);
    }

}
