package com.example.miha.sudocu.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.fragment.RecordsListFragment;
import com.example.miha.sudocu.View.fragment.RegistrationFragment;

public class OnlineRating extends FragmentActivity implements RegistrationFragment.LoginCallback{
private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_rating);
        fragment = new RegistrationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onLogin() {
        fragment = new RecordsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}