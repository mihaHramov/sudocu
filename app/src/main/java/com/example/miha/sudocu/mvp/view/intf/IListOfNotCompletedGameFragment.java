package com.example.miha.sudocu.mvp.view.intf;


import com.arellomobile.mvp.MvpView;
import com.example.miha.sudocu.mvp.data.model.Grid;

import java.util.ArrayList;

public interface IListOfNotCompletedGameFragment extends MvpView {
    void showLoad(boolean flag);
    void refreshListOfCompleteGame(ArrayList<Grid> gridList);
}
