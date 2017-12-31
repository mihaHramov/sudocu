package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.view.intf.IListOfNotCompletedGameFragment;

public interface IPresenterOfNonCompleteGame extends IPresenterOfFragment {
    void setView(IListOfNotCompletedGameFragment view);
}
