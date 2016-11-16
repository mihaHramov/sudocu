package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.data.Grid;

/**
 * Created by miha on 16.11.2016.
 */
public interface IPresenterGrid extends IPresenter {
    public Grid getGrid();
    public void answer(String s);
}
