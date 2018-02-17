package com.example.miha.sudocu.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.view.events.OnUserLogin;
import com.example.miha.sudocu.mvp.view.fragment.RecordsListFragment;
import com.example.miha.sudocu.mvp.view.fragment.RegistrationFragment;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.mvp.data.DP.RepositoryUser;
import com.squareup.otto.Subscribe;

public class OnlineRating extends BaseMvpActivity {
    private Fragment fragment;
    private IRepositoryUser userRepository = null;

    @Override
    protected int getLayoutId() {
        return R.layout.online_rating;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        userRepository = new RepositoryUser(this);
        if (userRepository.getUser() == null) {
            fragment = new RegistrationFragment();
        } else {
            fragment = new RecordsListFragment();
        }
        changeFragment(R.id.fragment_container, fragment);
    }

    private void changeFragment(int id, Fragment fr) {
        getSupportFragmentManager().beginTransaction().replace(id, fr).commit();
    }

    private void showMenuItemById(int id, boolean visible) {
        toolbar.getMenu().findItem(id).setVisible(visible);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reiting_menu, menu);
        if (userRepository.getUser() == null) {
            showMenuItemById(R.id.reload_login, false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload_login:
                fragment = new RegistrationFragment();
                changeFragment(R.id.fragment_container, fragment);
                showMenuItemById(R.id.reload_login, false);
                break;
        }
        return false;
    }

    @Subscribe
    public void onLogin(OnUserLogin onUserLogin) {
        fragment = new RecordsListFragment();
        changeFragment(R.id.fragment_container, fragment);
        showMenuItemById(R.id.reload_login, true);
        this.userRepository.setUser(onUserLogin.getUser());
    }
}