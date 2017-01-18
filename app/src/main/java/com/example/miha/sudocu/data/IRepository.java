package com.example.miha.sudocu.data;

import java.util.ArrayList;

/**
 * Created by miha on 15.01.17.
 */

public interface IRepository {
    void saveGame(Grid g);

    ArrayList<Grid> getListGames();
}
