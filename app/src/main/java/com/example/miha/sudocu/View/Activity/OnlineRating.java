package com.example.miha.sudocu.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.fragment.RecordsListFragment;
import com.example.miha.sudocu.View.fragment.RegistrationFragment;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.model.User;

public class OnlineRating extends FragmentActivity implements RegistrationFragment.LoginCallback {
    private Fragment fragment;
    private IRepositoryUser userRepository = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_rating);

        initToolbar();
        userRepository = new RepositoryUser(this);
        if (userRepository.getUser() == null) {
            fragment = new RegistrationFragment();
            showMenuItemById(R.id.reload_login,false);
        } else {
            fragment = new RecordsListFragment();
        }

        changeFragment(R.id.fragment_container,fragment);
    }

    private void changeFragment(int id,Fragment fr){
        getSupportFragmentManager().beginTransaction().replace(id, fr).commit();
    }

    private void showMenuItemById(int id,boolean visible){
        toolbar.getMenu().findItem(id).setVisible(visible);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.reiting_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.reload_login:
                    fragment = new RegistrationFragment();
                    changeFragment(R.id.fragment_container,fragment);
                    showMenuItemById(R.id.reload_login,false);
                    break;
            }

            return false;
        });
    }


    @Override
    public void onLogin(User user) {
        fragment = new RecordsListFragment();
        changeFragment(R.id.fragment_container,fragment);
        showMenuItemById(R.id.reload_login,true);
        this.userRepository.setUser(user);
    }
}