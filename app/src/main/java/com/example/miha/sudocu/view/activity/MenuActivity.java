package com.example.miha.sudocu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends Activity {

    private AlertDialog dialog;

    @OnClick(R.id.restore)
    void onClickRestore(){
        openNewActivity(new Intent(this, ListOfGameSavesActivity.class));
    }

    @OnClick(R.id.rating)
    void onClickRating(){
        openNewActivity(new Intent(this, OnlineRating.class));
    }

    @OnClick(R.id.newGame)
    void onClickNewGame(){
        dialog.showDialog();
    }
    @OnClick(R.id.open_setting)
     void onClickOpenSettings(){
        openNewActivity(new Intent(this,SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dialog = new AlertDialog(this);
        ButterKnife.bind(this);
    }

    private void openNewActivity(Intent i) {
        startActivity(i);
    }
}