package com.example.miha.sudocu.View.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;

public class ListOfCompleteGameFragment extends Fragment {
    private ListView listView;
    private IPresenterOfFragment presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterListOfCompleteGameFragment(getActivity());
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
        registerForContextMenu(listView);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.list_of_complete_game,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.challenge:
               // info.position;
                break;
            case R.id.delete:
               // info.position;
                break;
        }
        return super.onContextItemSelected(item);
    }
}
