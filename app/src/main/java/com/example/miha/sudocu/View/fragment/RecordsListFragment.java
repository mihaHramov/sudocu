package com.example.miha.sudocu.View.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;
import com.example.miha.sudocu.presenter.PresenterRecordsListFragment;


public class RecordsListFragment extends Fragment {
    private ListView listOfRecords;
    private IPresenterOfFragment presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.records_list_fragment, container, false);
        listOfRecords = (ListView) rootView.findViewById(R.id.list_records);
        presenter.initListView(listOfRecords);
        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterRecordsListFragment(getActivity());
    }
}
