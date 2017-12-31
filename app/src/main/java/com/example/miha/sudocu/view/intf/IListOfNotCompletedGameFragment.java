package com.example.miha.sudocu.view.intf;


import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

public interface IListOfNotCompletedGameFragment {
    void showLoad(boolean flag);
    void refreshListOfCompleteGame(ArrayList<Grid> gridList);
}
