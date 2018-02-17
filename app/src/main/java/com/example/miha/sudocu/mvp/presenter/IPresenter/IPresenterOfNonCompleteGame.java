package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.view.intf.IListOfNotCompletedGameFragment;

public interface IPresenterOfNonCompleteGame extends IPresenterOfFragment {
    void setView(IListOfNotCompletedGameFragment view);
}
