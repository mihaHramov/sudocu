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
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;

public class ListOfGameFragment extends Fragment {
    private ListView listView;
    private IPresenterOfFragment presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterListOfGameFragment(getActivity());
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
        presenter.initListView(listView);

        return rootView;
    }
}
