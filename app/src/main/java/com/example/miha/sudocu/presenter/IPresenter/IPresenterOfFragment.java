package com.example.miha.sudocu.presenter.IPresenter;

import android.widget.ListView;


public interface IPresenterOfFragment {

    void onResume();

    void initListView(ListView listView);
}
