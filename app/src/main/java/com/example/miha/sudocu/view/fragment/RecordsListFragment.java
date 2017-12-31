package com.example.miha.sudocu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.presenter.Adapter.AdapterChallenge;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;
import com.example.miha.sudocu.presenter.PresenterRecordsListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecordsListFragment extends Fragment {
    @BindView(R.id.list_records)
    ListView listOfRecords;
    private AdapterChallenge adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.records_list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        listOfRecords.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IPresenterOfFragment presenter = new PresenterRecordsListFragment(RetroClient.getInstance());
        adapter = new AdapterChallenge(getContext());
    }
}
