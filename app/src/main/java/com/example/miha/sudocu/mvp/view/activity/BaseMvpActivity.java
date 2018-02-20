package com.example.miha.sudocu.mvp.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.view.events.BusProvider;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseMvpActivity extends MvpAppCompatActivity{
    protected Bus bus;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        bus = BusProvider.getInstance();
        bus.register(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    protected abstract int getLayoutId();
}
