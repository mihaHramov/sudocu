package com.example.miha.sudocu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.intf.IDialogManager;
import com.example.miha.sudocu.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;

import java.util.ArrayList;

public class ListOfCompleteGameFragment extends Fragment implements IListOfCompleteGameFragment {
    private ProgressBar progressBar;
    private IPresenterOfCompleteGame presenter;
    private IDialogManager activityCallback;
    private Integer lastIdRecords = null;
    private Integer lastIdRecordsDelete = null;
    private AdapterGrid adapter;

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
            adapter.setData(gridList);
    }

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
        if (this.lastIdRecords != null) {
            presenter.sendGame((Grid) adapter.getItem(lastIdRecords));
        }
    }

    @Override
    public void deleteGameFromList() {
        adapter.deleteItemById(lastIdRecordsDelete);
    }

    @Override
    public void onErrorSendGame() {
        Toast.makeText(getContext(),getContext().getString(R.string.error),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = DP.get().getPresenterListOfCompleteGameFragment();
        presenter.setView(this);
        adapter = new AdapterGrid(getContext());
        presenter.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IDialogManager) {
            this.activityCallback = (IDialogManager)context ;
        }
    }

    @Override
    public void showLoad(boolean flag) {
        if(flag){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }else {
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.savePresenterData(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.list_of_game_saves_fragment, container, false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewGrid);
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
                Log.d("mihaHramov",""+info.position);
                presenter.sendGame((Grid) adapter.getItem(info.position));
                break;
            case R.id.delete:
                lastIdRecordsDelete = info.position;
                presenter.deleteGame((Grid) adapter.getItem(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }
}
