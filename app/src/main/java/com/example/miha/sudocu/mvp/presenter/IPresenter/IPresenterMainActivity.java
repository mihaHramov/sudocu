package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.view.intf.IMainActivity;

public interface IPresenterMainActivity {
   void isPortrait(Boolean isPortrait);
   void setView(IMainActivity activity);
   void unSubscription();
}