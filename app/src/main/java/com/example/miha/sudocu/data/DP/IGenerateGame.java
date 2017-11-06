package com.example.miha.sudocu.data.DP;


import java.util.Map;

public interface IGenerateGame {
    Map<Integer, String> initAnswer(int undefined);

    void transperentMatrix();

    String[][] generateGame();

    void swapRowsSmall();//готовро

    void swapRowsArea();//готово

    void swapColumsSmall();//готово

    void swapColumsArea();
}
