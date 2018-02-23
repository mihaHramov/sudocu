package com.example.miha.sudocu.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.view.adapter.AdapterChallenge;
import com.example.miha.sudocu.mvp.presenter.PresenterRecordsListFragment;
import com.example.miha.sudocu.mvp.view.activity.MainActivity;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;
import com.example.miha.sudocu.utils.SerializableGame;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;


public class RecordsListFragment extends BaseMvpFragment implements IRecordsList {
    @BindView(R.id.recycler_list_records)
    RecyclerView listOfRecords;
    @Inject
    AdapterChallenge adapter;

    @InjectPresenter
    PresenterRecordsListFragment presenter;

    @ProvidePresenter
    PresenterRecordsListFragment providePresenter(){
        return App.getComponent().ratingComponent().getPresenter();
    }
    @Inject
    RecyclerView.LayoutManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.records_list_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        App.getComponent().ratingComponent().inject(this);
        listOfRecords.setLayoutManager(manager);
        listOfRecords.setAdapter(adapter);
        return v;
    }

    @Override
    public void showRecords(ArrayList<Challenge> challenges) {
        adapter.setValueList(challenges);
        adapter.setOnClickListener(() -> {
            Challenge challenge = adapter.getItem(adapter.getIdChoseItem());
            presenter.choiceChallenge(challenge);
        });
    }

    @Override
    public void choiceChallenge(Challenge challenge) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Grid.KEY, SerializableGame.serializable(challenge.getGrid()));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
