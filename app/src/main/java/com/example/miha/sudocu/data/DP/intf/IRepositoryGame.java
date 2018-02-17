package com.example.miha.sudocu.data.DP.intf;

import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

import rx.Observable;

public interface IRepositoryGame {
    Observable<Integer> saveGame(Grid g);

    Observable<Void> deleteGame(Grid id);

    Observable<ArrayList<Grid>> getListGames();

    Observable<ArrayList<Grid>> getListCompleteGames();
}
