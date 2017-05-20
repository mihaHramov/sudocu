package com.example.miha.sudocu.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;

public class MenuActivity extends Activity implements View.OnClickListener {

    private AlertDialog dialog;

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.restore:
                i = new Intent(this, ListOfGameSavesActivity.class);
                startActivity(i);
                break;
            case R.id.newGame:
                dialog.showDialog();
                break;
            case R.id.rating:
                i = new Intent(this,OnlineRating.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dialog = new AlertDialog(this);
        findViewById(R.id.restore).setOnClickListener(this);
        findViewById(R.id.newGame).setOnClickListener(this);
        findViewById(R.id.rating).setOnClickListener(this);
    }
}