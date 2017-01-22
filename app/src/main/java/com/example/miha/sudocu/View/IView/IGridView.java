package com.example.miha.sudocu.View.IView;


/**
 * Created by miha on 17.10.2016.
 */
public interface IGridView {
     void clearGrid();
     void showGrid(String[][] ar);
     int getIdAnswer();
     void success();
     void gameOver();
     void failure();
     void setGameTime(long time);
     long getGameTime();
}
