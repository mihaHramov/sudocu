package com.example.miha.sudocu.View.IView;


/**
 * Created by miha on 17.10.2016.
 */
public interface IGridView {
    public void showGrid(String[][] ar);
    public int getIdAnswer();
    public void success();
    public void gameOver();
    public void failure();
}
