package com.example.miha.sudocu.mvp.view.intf;


import com.example.miha.sudocu.mvp.data.model.Grid;

import java.util.ArrayList;

public interface IListOfNotCompletedGameFragment {
    void showLoad(boolean flag);
    void refreshListOfCompleteGame(ArrayList<Grid> gridList);
}
