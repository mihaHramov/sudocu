package com.example.miha.sudocu.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.utils.SerializableGame;
import com.example.miha.sudocu.mvp.view.activity.MainActivity;
import com.example.miha.sudocu.mvp.view.intf.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.view.adapter.AdapterGrid;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class ListOfGameFragment extends BaseMvpFragment implements IListOfNotCompletedGameFragment {
    @BindView(R.id.recycler_view_grid)
    RecyclerView recyclerView;
    @Inject
    RecyclerView.LayoutManager manager;
    @Inject
    PresenterListOfGameFragment presenter;
    @Inject
    AdapterGrid adapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public int getLayoutId() {
        return R.layout.list_of_game_saves_fragment;
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
        App.getComponent().gameListComponent().inject(this);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        presenter.init(savedInstanceState);
        presenter.setView(this);
        adapter.setOnClickListener(() -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.putExtra(Grid.KEY, SerializableGame.serializable(adapter.getItem(adapter.getIdChoseItem())));
            getActivity().startActivity(i);
        });
        return rootView;
    }

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
        adapter.setValueList(gridList);
    }
}