package com.example.miha.sudocu.mvp.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.miha.sudocu.mvp.view.events.BusProvider;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

public abstract class BaseMvpFragment extends MvpAppCompatFragment {
    protected Bus bus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bus = BusProvider.getInstance();
        bus.register(this);
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    public abstract int getLayoutId();
}
