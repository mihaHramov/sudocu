package com.example.miha.sudocu.di.module;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class SchedulersModule {

    @Provides
    @Singleton
    @Named("db")
    public Scheduler provideDatabeseScheduler() {
        return Schedulers.from(Executors.newSingleThreadExecutor());
    }

    @Provides
    @Singleton
    @Named("main")
    public Scheduler provideMainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public Scheduler provideScheduler() {
        return Schedulers.newThread();
    }
}
