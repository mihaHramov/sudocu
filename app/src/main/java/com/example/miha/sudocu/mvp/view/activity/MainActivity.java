package com.example.miha.sudocu.mvp.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.PresenterMainActivity;
import com.example.miha.sudocu.mvp.view.intf.IGetGame;
import com.example.miha.sudocu.service.MyMediaPlayerService;
import com.example.miha.sudocu.mvp.view.intf.IMainActivity;
import com.example.miha.sudocu.mvp.view.events.OnStopMusicEvent;
import com.example.miha.sudocu.mvp.view.events.PlayMusicEvent;
import com.example.miha.sudocu.mvp.view.fragment.KeyBoardFragment;
import com.example.miha.sudocu.mvp.view.fragment.PlayingFieldFragment;
import com.example.miha.sudocu.utils.SerializableGame;
import com.squareup.otto.Subscribe;


import butterknife.BindView;


public class MainActivity extends BaseMvpActivity implements IMainActivity,IGetGame {

    private PlayingFieldFragment playingField;

    @InjectPresenter
    PresenterMainActivity presenter;

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @ProvidePresenter
    PresenterMainActivity providePresenter() {
        Grid model = SerializableGame.unSerializable(getIntent());
        presenter = App.getComponent().playingComponent().providePresenterMainActivity();
        presenter.setModel(model);
        return presenter;
    }

    private boolean isPortrait;

    @BindView(R.id.container_fragments)
    LinearLayout layoutContainer;
    @BindView(R.id.keyboard)
    View keyboard;
    @BindView(R.id.tableLayout1)
    View play;

    @Override
    public void changeTitleToolbar(String str) {
        toolbar.setTitle(str);
    }

    @Override
    public void changeSubTitleToolbar(String str) {
        toolbar.setSubtitle(str);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().playingComponent().inject(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        initFragments(savedInstanceState);
    }

    private void initFragments(Bundle savedInstanceState) {
        KeyBoardFragment keyBoardFragment;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.isPortrait(isPortrait);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyMediaPlayerService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.reload_game:
                playingField.reloadGame();
                break;
            case R.id.open_setting:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.replay_game:
                playingField.replayGame();
                break;
        }
        return false;
    }

    private void replaceView(View first, View second) {
        layoutContainer.removeAllViews();
        layoutContainer.addView(first);
        layoutContainer.addView(second);
    }

    @Override
    public void showTheKeyboardOnTheLeftSide() {
        replaceView(keyboard, play);
    }

    @Override
    public void showTheKeyboardOnTheRightSide() {
        if (!isPortrait) {
            replaceView(play, keyboard);
        }
    }

    @Subscribe
    public void stopMusic(OnStopMusicEvent event) {
        finish();
    }

    @Subscribe
    public void playMusic(PlayMusicEvent event) {
        Intent intent = new Intent(this, MyMediaPlayerService.class)
                .putExtra(MyMediaPlayerService.name, event.getResMusic());
        startService(intent);
    }

    @Override
    public Grid getGame() {
        return presenter.getModel();
    }
}