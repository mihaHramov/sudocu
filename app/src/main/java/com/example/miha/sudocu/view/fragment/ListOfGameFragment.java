package com.example.miha.sudocu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.utils.SerializableGame;
import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.view.intf.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfGameFragment extends Fragment implements IListOfNotCompletedGameFragment {
    @BindView(R.id.recycler_view_grid)
    RecyclerView recyclerView;
    @Inject
    public PresenterListOfGameFragment presenter;
    private AdapterGrid adapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        presenter.init(savedInstanceState);

        presenter.setView(this);
        adapter = new AdapterGrid(R.layout.item);
        adapter.setOnClickListener(() -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.putExtra(Grid.KEY, SerializableGame.serializable(adapter.getItem(adapter.getIdChoseItem())));
            getActivity().startActivity(i);
        });
    }

    @Override
    public void showLoad(boolean flag) {
        if (flag) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
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
        View rootView = inflater.inflate(R.layout.list_of_game_saves_fragment, container, false);
        ButterKnife.bind(this, rootView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
        adapter.setValueList(gridList);
    }
}