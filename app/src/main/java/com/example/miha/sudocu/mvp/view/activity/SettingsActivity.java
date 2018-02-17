package com.example.miha.sudocu.mvp.view.activity;

import android.os.Bundle;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.view.fragment.SettingsFragment;

public class SettingsActivity extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null){
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment,new SettingsFragment())
                .commit();
    }
}
