package com.example.miha.sudocu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.Service.MyMediaPlayerService;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnStopMusicEvent;
import com.example.miha.sudocu.view.events.PlayMusicEvent;
import com.example.miha.sudocu.view.fragment.KeyBoardFragment;
import com.example.miha.sudocu.view.fragment.PlayingFieldFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity {
    private KeyBoardFragment keyBoardFragment;
    private PlayingFieldFragment playingField;
    private Bus bus;

    private Toolbar toolbar;
    public static String myMediaPlayer = "myMediaPlayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus = BusProvider.getInstance();
        bus.register(this);

        keyBoardFragment = new KeyBoardFragment();
        playingField = new PlayingFieldFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.keyboard, keyBoardFragment)
                .replace(R.id.tableLayout1, playingField)
                .commit();
        toolbarInit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
        stopService(new Intent(this, MyMediaPlayerService.class));
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.reloadGame:
                    playingField.reloadGame();
                    break;
            }
            return false;
        });
    }

    @Subscribe
    public void stopMusic(OnStopMusicEvent event) {
        finish();
    }

    @Subscribe
    public void playMusic(PlayMusicEvent event) {
        Intent intent = new Intent(this, MyMediaPlayerService.class)
                .putExtra(myMediaPlayer, event.getResMusic());
        startService(intent);
    }
}