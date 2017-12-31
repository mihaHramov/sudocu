package com.example.miha.sudocu.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.fragment.RecordsListFragment;
import com.example.miha.sudocu.view.fragment.RegistrationFragment;
import com.example.miha.sudocu.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineRating extends AppCompatActivity implements RegistrationFragment.LoginCallback {
    private Fragment fragment;
    private IRepositoryUser userRepository = null;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_rating);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        userRepository = new RepositoryUser(this);
        if (userRepository.getUser() == null) {
            fragment = new RegistrationFragment();
            showMenuItemById(R.id.reload_login, false);
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


    @Override
    public void onLogin(User user) {
        fragment = new RecordsListFragment();
        changeFragment(R.id.fragment_container, fragment);
        showMenuItemById(R.id.reload_login, true);
        this.userRepository.setUser(user);
    }
}