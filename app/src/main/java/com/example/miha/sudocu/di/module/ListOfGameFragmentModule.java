package com.example.miha.sudocu.di.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.view.adapter.AdapterGrid;


import dagger.Module;
import dagger.Provides;

@Module
public class ListOfGameFragmentModule {

    @Provides
    public AdapterGrid provideAdapterGrid() {
        return new AdapterGrid(R.layout.item);
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
