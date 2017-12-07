package com.example.miha.sudocu.presenter.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.R;

public class AlertDialog  {
    public static final String SETTINGS = "SettingsComplexityDialog";
    private TextView dialogComplexityTextValue;
    private SeekBar dialogComplexitySeekbar;
    private Dialog dialog;
    private Context ctx;
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

            dialogComplexityTextValue.setText(ctx.getResources().getString(level));
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
    };

    public AlertDialog(Context context){
        ctx = context;
    }
    public void showDialog() {
        if(dialog==null){
            initDialog();
        }
        dialog.show();
    }
public void dismissDialog(){
    dialog.dismiss();
}

    private void initDialog() {
        dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.dialog_settings_view);
        dialogComplexityTextValue = (TextView) dialog.findViewById(R.id.textTitleValue);
        dialogComplexitySeekbar = (SeekBar) dialog.findViewById(R.id.complexity_grid);
        dialogComplexitySeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        dialog.findViewById(R.id.newGameWithSettings).setOnClickListener(v -> {
            Intent i;
            switch (v.getId()) {
                case R.id.newGameWithSettings:
                    dialog.hide();
                    i = new Intent(ctx, MainActivity.class);
                    i.putExtra(SETTINGS, dialogComplexitySeekbar.getProgress());
                    ctx.startActivity(i);
                    break;
            }
        });
    }
}
