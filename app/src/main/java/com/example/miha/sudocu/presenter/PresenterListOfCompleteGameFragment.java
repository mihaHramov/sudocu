package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.widget.ListView;

import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;


public class PresenterListOfCompleteGameFragment implements IPresenterOfFragment {
    private RepositoryImplBD repository;
    private AdapterGrid adapter;
    private Activity activity;
    public PresenterListOfCompleteGameFragment(Activity activity){
        repository = new RepositoryImplBD(activity);
        adapter = new AdapterGrid(activity);
        this.activity = activity;
    }

    @Override
    public void initListView(ListView listView) {
        listView.setAdapter(adapter);

    }


    @Override
    public void onResume() {
        adapter.setData(repository.getListCompleteGames());
        adapter.notifyDataSetChanged();
    }
}
