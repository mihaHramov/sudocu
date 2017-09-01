package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.view.IView.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;

public interface IPresenterOfNonCompleteGame extends IPresenterOfFragment {
    void setView(IListOfNotCompletedGameFragment view);
}
