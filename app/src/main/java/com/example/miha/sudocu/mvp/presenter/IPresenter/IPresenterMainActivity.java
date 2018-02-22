package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.data.model.Grid;

import rx.Scheduler;

public interface IPresenterMainActivity {
   void isPortrait(Boolean isPortrait);
   void replayGame();
   void isGameOver();
   void onPause();
   void setSchedulers(Scheduler db, Scheduler main,Scheduler newSche);
   Grid getModel();
   void setModel(Grid grid);
   void reloadGame();
}