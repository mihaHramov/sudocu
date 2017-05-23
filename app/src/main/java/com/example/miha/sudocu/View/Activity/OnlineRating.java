package com.example.miha.sudocu.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.fragment.RecordsListFragment;
import com.example.miha.sudocu.View.fragment.RegistrationFragment;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.model.User;

public class OnlineRating extends FragmentActivity implements RegistrationFragment.LoginCallback {
    private Fragment fragment;
    private IRepositoryUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_rating);
        user = new RepositoryUser(this);
        if (user.getUser() == null) {
            fragment = new RegistrationFragment();
        }else{
            fragment = new RecordsListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onLogin(User user) {
        fragment = new RecordsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        this.user.setUser(user);
    }
}