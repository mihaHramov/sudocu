package com.example.miha.sudocu.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.Adapter.AlertDialog;

import butterknife.OnClick;

public class MenuActivity extends BaseMvpActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
    }

    private AlertDialog dialog;

    @OnClick(R.id.restore)
    void onClickRestore(){
        openNewActivity(ListOfGameSavesActivity.class);
    }
    @OnClick(R.id.rating)
    void onClickRating(){
        openNewActivity(OnlineRating.class);
    }

    @OnClick(R.id.newGame)
    void onClickNewGame(){
        dialog.showDialog();
    }
    @OnClick(R.id.open_setting)
    void onClickOpenSettings(){
        openNewActivity(SettingsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new AlertDialog(this);
    }

    @Override
    public void onDestroy() {
        dialog.dismissDialog();
        super.onDestroy();
    }

    private void openNewActivity(Class<?> classId ) {
        startActivity(new Intent(this,classId));
    }
}