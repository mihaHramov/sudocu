package com.example.miha.sudocu.View;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SETTINGS = "MenuActivitySettingsComplexity";
    private TextView dialogComplexityTextValue;
    private SeekBar dialogComplexitySeekbar;
    private Dialog dialog;
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int level;
            if (progress < 20) {
                level = R.string.level_easy;
            } else if (progress < 45) {
                level = R.string.level_medium;
            } else {
                level = R.string.level_hard;
            }

            dialogComplexityTextValue.setText(getResources().getString(level));
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.restore:
                i = new Intent(this, ListOfGameSavesActivity.class);
                startActivity(i);
                break;
            case R.id.newGame:
                dialog.show();
                break;
            case R.id.newGameWithSettings:
                dialog.hide();
                i = new Intent(this, MainActivity.class);
                i.putExtra(this.SETTINGS,dialogComplexitySeekbar.getProgress());
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initDialog();
        findViewById(R.id.restore).setOnClickListener(this);
        findViewById(R.id.newGame).setOnClickListener(this);
    }

    private void initDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_settings_view);
        dialogComplexityTextValue = (TextView) dialog.findViewById(R.id.textTitleValue);
        dialogComplexitySeekbar = (SeekBar) dialog.findViewById(R.id.complexity_grid);
        dialogComplexitySeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        dialog.findViewById(R.id.newGameWithSettings).setOnClickListener(this);
    }
}
