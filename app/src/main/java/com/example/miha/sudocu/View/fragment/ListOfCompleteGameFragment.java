package com.example.miha.sudocu.View.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.IView.IDialogManager;
import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;

import java.util.ArrayList;

public class ListOfCompleteGameFragment extends Fragment implements IListOfCompleteGameFragment {
    private ListView listView;

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
            adapter.setData(gridList);
    }

    private IPresenterOfCompleteGame presenter;
    private IDialogManager activityCallback;
    private int lastIdRecords;
    private AdapterGrid adapter;

    @Override
    public void authUser() {
        activityCallback.showAuthDialog();
    }

    @Override
    public void onSendGame() {
        Toast.makeText(getContext(),getContext().getString(R.string.send_game_to_challenge),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAfterAuthUser() {
        if (this.lastIdRecords > 0) {
            presenter.sendGame((Grid) adapter.getItem(lastIdRecords));
        }
    }

    @Override
    public void onErrorSendGame() {
        Toast.makeText(getContext(),getContext().getString(R.string.error),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterListOfCompleteGameFragment(getActivity());
        presenter.setView(this);
        adapter = new AdapterGrid(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IDialogManager) {
            this.activityCallback = (IDialogManager) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.list_of_game_saves_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewGrid);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.list_of_complete_game, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.challenge:
                lastIdRecords = info.position;
                presenter.sendGame((Grid) adapter.getItem(info.position));
                break;
            case R.id.delete:
                presenter.deleteGame((Grid) adapter.getItem(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }
}
