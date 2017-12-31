package com.example.miha.sudocu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.view.intf.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfNonCompleteGame;

import java.util.ArrayList;

public class ListOfGameFragment extends Fragment implements IListOfNotCompletedGameFragment{
    private ListView listView;
    private IPresenterOfNonCompleteGame presenter;
    private AdapterGrid adapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = DP.get().getPresenterListOfGameFragment();
        presenter.init(savedInstanceState);
        presenter.setView(this);
        adapter = new AdapterGrid(getContext());
    }

    @Override
    public void showLoad(boolean flag) {
        if(flag){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }else {
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.savePresenterData(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.list_of_game_saves_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewGrid);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.putExtra(Grid.KEY, (Grid) adapter.getItem(position));
            getActivity().startActivity(i);
        });
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
        adapter.setData(gridList);
    }
}
