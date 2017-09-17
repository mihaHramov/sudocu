package com.example.miha.sudocu.view.IView;

import com.example.miha.sudocu.data.model.Answer;

public interface IGridView {
     IGridView showGrid(Answer[][] ar);
     int getIdAnswer();
     void gameOver();
     void setGameTime(long time);
     long getGameTime();
}
