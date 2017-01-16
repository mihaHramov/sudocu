package com.example.miha.sudocu.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.RepositoryImplBD;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onClick(View v) {
        Intent i ;
        switch (v.getId()){
            case R.id.restore:
                i  = new Intent(this, ListOfGameSavesActivity.class);
                startActivity(i);
                break;
            case R.id.newGame:
                i = new Intent(this,MainActivity.class);
                startActivity(i);
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

       /* Grid model  = new Grid();
        model.setComplexity(10);
        model.init();

        RepositoryImplBD bd = new RepositoryImplBD(getApplicationContext());
        //bd.loadGame(model); //saveGame(model);
        bd.getListGames();
        */
    }
}
