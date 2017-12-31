package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.view.intf.IMainActivity;

public interface IPresenterMainActivity {
   void isPortrait(Boolean isPortrait);
   void setView(IMainActivity activity);
   void unSubscription();
}