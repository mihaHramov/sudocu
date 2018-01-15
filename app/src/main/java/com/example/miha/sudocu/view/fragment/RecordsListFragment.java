package com.example.miha.sudocu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.presenter.Adapter.AdapterChallenge;
import com.example.miha.sudocu.presenter.PresenterRecordsListFragment;
import com.example.miha.sudocu.view.intf.IRecordsList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecordsListFragment extends Fragment implements IRecordsList {
    @BindView(R.id.recycler_list_records)
    RecyclerView listOfRecords;
    private AdapterChallenge adapter;
    private PresenterRecordsListFragment presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.records_list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        listOfRecords.setLayoutManager(manager);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterRecordsListFragment(RetroClient.getInstance(), this);
        presenter.init(savedInstanceState);
    }

    @Override
    public void showRecords(ArrayList<Challenge> challenges)  {
        adapter = new AdapterChallenge(challenges);
        listOfRecords.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
