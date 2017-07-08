package com.example.miha.sudocu.View.Activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.View.fragment.ListOfGameFragment;
import com.example.miha.sudocu.presenter.Adapter.AdapterLocalGameList;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;


public class ListOfGameSavesActivity extends FragmentActivity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_game_saves);
        initTabLayout();
        initToolbar();
        dialog = new AlertDialog(this);
    }

    public void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.list_game);
        toolbar.setTitle(R.string.games);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigateButtonBack:
                        finish();
                        break;
                    case R.id.newGame:
                        dialog.showDialog();
                        break;
                }

                return false;
            }
        });
    }
    public void initTabLayout(){
        AdapterLocalGameList adapterLocalGameList = new AdapterLocalGameList(getSupportFragmentManager());
        adapterLocalGameList.addFragment(new ListOfGameFragment(),getString(R.string.un_complete_game));
        adapterLocalGameList.addFragment(new ListOfCompleteGameFragment(),getString(R.string.complete_game));
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterLocalGameList);
        ((TabLayout )findViewById(R.id.tableLayout)).setupWithViewPager(viewPager);
    }
}
