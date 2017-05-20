package com.example.miha.sudocu.View.IView;

import android.app.Activity;

/**
 * Created by miha on 19.05.17.
 */

public interface IFragment {
    void onAttach(Activity activity);
    void onDetach();
    void callbacks();
}
