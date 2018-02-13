package com.example.miha.sudocu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterChallenge;
import com.example.miha.sudocu.presenter.PresenterRecordsListFragment;
import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.view.intf.IRecordsList;

import java.util.ArrayList;

import butterknife.BindView;


public class RecordsListFragment extends BaseMvpFragment implements IRecordsList {
    @BindView(R.id.recycler_list_records)
    RecyclerView listOfRecords;
    private AdapterChallenge adapter;
    private PresenterRecordsListFragment presenter;

    @Override
    public int getLayoutId() {
        return R.layout.records_list_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        listOfRecords.setLayoutManager(manager);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterRecordsListFragment(RetroClient.getInstance(), this);
        presenter.init(savedInstanceState);
    }

    @Override
    public void showRecords(ArrayList<Challenge> challenges)  {
        adapter = new AdapterChallenge(R.layout.item_challenge,challenges);
        adapter.setOnClickListener(()->{
            Challenge challenge = adapter.getItem(adapter.getIdChoseItem());
            presenter.choiceChallenge(challenge);
        });
        listOfRecords.setAdapter(adapter);
    }

    @Override
    public void choiceChallenge(Challenge challenge) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Grid.KEY,challenge.getGrid());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
