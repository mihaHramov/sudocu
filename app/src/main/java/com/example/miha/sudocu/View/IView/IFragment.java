package com.example.miha.sudocu.View.IView;

import android.app.Activity;


public interface IFragment {
    void onAttach(Activity activity);
    void onDetach();
    void callbacks();
}
