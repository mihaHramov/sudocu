package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;



public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private RepositoryImplBD repository;
    private AdapterGrid adapter;
    private Activity activity;

    @Override
    public void deleteGame(int id) {
        repository.deleteGame((Grid) adapter.getItem(id));
        adapter.deleteItemById(id);

    }

    @Override
    public void sendGame(int id) {
        Log.d("mihaHramovGame",id+"");
    }

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
