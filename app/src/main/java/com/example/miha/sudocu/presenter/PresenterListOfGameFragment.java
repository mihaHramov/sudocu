package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miha.sudocu.View.Activity.MainActivity;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;


public class PresenterListOfGameFragment implements IPresenterOfFragment {
    private RepositoryImplBD repository;
    private AdapterGrid adapter;
    private Activity activity;
    public PresenterListOfGameFragment(Activity activity){
        repository = new RepositoryImplBD(activity);
        adapter = new AdapterGrid(activity);
        this.activity = activity;
    }

    @Override
    public void initListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(activity, MainActivity.class);
                i.putExtra(Grid.KEY, (Grid) adapter.getItem(position));
                activity.startActivity(i);
            }
        });
        listView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        adapter.setData(repository.getListGames());
        adapter.notifyDataSetChanged();
    }
}
