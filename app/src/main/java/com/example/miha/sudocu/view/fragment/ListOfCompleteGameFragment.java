package com.example.miha.sudocu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.view.intf.IDialogManager;
import com.example.miha.sudocu.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.view.Adapter.AdapterGrid;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class ListOfCompleteGameFragment extends BaseMvpFragment implements IListOfCompleteGameFragment {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_grid)
    RecyclerView recyclerView;

    @Inject  PresenterListOfCompleteGameFragment presenter;
    private IDialogManager activityCallback;
    private Integer lastIdRecords = null;
    private Integer lastIdRecordsDelete = null;
    @Inject AdapterGrid adapter;

    @Override
    public void refreshListOfCompleteGame(ArrayList<Grid> gridList) {
        adapter.setValueList(gridList);
    }

    @Override
    public void authUser() {
        activityCallback.showAuthDialog();
    }

    @Override
    public void onSendGame() {
        Toast.makeText(getContext(), getContext().getString(R.string.send_game_to_challenge), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAfterAuthUser() {
        if (this.lastIdRecords != null) {
            presenter.sendGame(adapter.getItem(lastIdRecords));
        }
    }

    @Override
    public void deleteGameFromList() {
        adapter.removeObjectAtPosition(lastIdRecordsDelete);
    }

    @Override
    public void onErrorSendGame() {
        Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        presenter.setView(this);
        adapter.setOnLongClickListener(v -> {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.inflate(R.menu.list_of_complete_game);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.challenge:
                        lastIdRecords = adapter.getIdChoseItem();
                        presenter.sendGame(adapter.getItem(adapter.getIdChoseItem()));
                        break;
                    case R.id.delete:
                        lastIdRecordsDelete = adapter.getIdChoseItem();
                        presenter.deleteGame(adapter.getItem(adapter.getIdChoseItem()));
                        break;
                }
                return false;
            });
            popup.show();
        });
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
            this.activityCallback = (IDialogManager) context;
        }
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
    public int getLayoutId() {
        return R.layout.list_of_game_saves_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  super.onCreateView(inflater,container,savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return root;
    }
}
