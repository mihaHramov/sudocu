package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.data.model.Grid;

import rx.Scheduler;

public interface IPresenterMainActivity {
   void isPortrait(Boolean isPortrait);
   void isGameOver();
   void onPause();
   void setSchedulers(Scheduler db, Scheduler main);
   Grid getModel();
   void setModel(Grid grid);
}