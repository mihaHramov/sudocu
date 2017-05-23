package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

public interface IRepository {
    void saveGame(Grid g);
    void deleteGame(Grid id);
    ArrayList<Grid> getListGames();
    ArrayList<Grid> getListCompleteGames();
}
