package com.example.miha.sudocu.view.IView;

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
