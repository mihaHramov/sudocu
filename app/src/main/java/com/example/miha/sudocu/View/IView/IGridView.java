package com.example.miha.sudocu.View.IView;


/**
 * Created by miha on 17.10.2016.
 */
public interface IGridView {
     IGridView clearGrid();
     IGridView showGrid(String[][] ar);
     int getIdAnswer();
     void success();
     void gameOver();
     void failure();
     void setGameTime(long time);
     long getGameTime();
}
