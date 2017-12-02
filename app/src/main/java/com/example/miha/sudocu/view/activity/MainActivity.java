package com.example.miha.sudocu.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.presenter.PresenterMainActivity;
import com.example.miha.sudocu.presenter.Service.MyMediaPlayerService;
import com.example.miha.sudocu.view.IView.IMainActivity;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnStopMusicEvent;
import com.example.miha.sudocu.view.events.PlayMusicEvent;
import com.example.miha.sudocu.view.fragment.KeyBoardFragment;
import com.example.miha.sudocu.view.fragment.PlayingFieldFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements IMainActivity {

    private KeyBoardFragment keyBoardFragment;
    private PlayingFieldFragment playingField;
    private Bus bus;
    private IPresenterMainActivity presenter;
    public static String myMediaPlayer = "myMediaPlayer";
    private boolean isPortrait;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container_fragments) LinearLayout layoutContainer;
    @BindView(R.id.keyboard) View keyboard;
    @BindView(R.id.tableLayout1) View play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus = BusProvider.getInstance();
        bus.register(this);
        ButterKnife.bind(this);
        presenter = new PresenterMainActivity(this);
        isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (savedInstanceState != null) {
            keyBoardFragment = (KeyBoardFragment) getSupportFragmentManager().findFragmentById(R.id.keyboard);
            playingField = (PlayingFieldFragment) getSupportFragmentManager().findFragmentById(R.id.tableLayout1);
        } else {
            keyBoardFragment = new KeyBoardFragment();
            playingField = new PlayingFieldFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.keyboard, keyBoardFragment)
                .replace(R.id.tableLayout1, playingField)
                .commit();
        toolbarInit();
    }

    @Override
    protected void onResume() {
        presenter.isPortrait(isPortrait);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
        presenter.unSubscription();
        stopService(new Intent(this, MyMediaPlayerService.class));
    }

    private void toolbarInit() {
        toolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.reloadGame:
                    playingField.reloadGame();
                    break;
                case R.id.open_setting:
                    Intent i = new Intent(this,SettingsActivity.class);
                    startActivity(i);
                    break;
            }
            return false;
        });
    }

    private void replaceView(View first,View second){
        layoutContainer.removeAllViews();
        layoutContainer.addView(first);
        layoutContainer.addView(second);
    }
    @Override
    public void showTheKeyboardOnTheLeftSide() {
        if (!isPortrait) {
            replaceView(keyboard,play);
        }
    }

    @Override
    public void showTheKeyboardOnTheRightSide() {
        if (!isPortrait) {
            replaceView(play,keyboard);
        }
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