package com.example.miha.sudocu.IView;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

/**
 * Created by miha on 17.10.2016.
 */
public interface IGridView {
    public void showGrid(String[][] ar);
    public EditText getLastEditText();
    public Context getContext();
}
