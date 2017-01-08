package com.example.miha.sudocu.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restore:
                Intent i  = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.newGame:
                break;
            case R.id.setting:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.restore).setOnClickListener(this);
        findViewById(R.id.newGame).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
    }
}
