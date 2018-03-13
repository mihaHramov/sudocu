package com.example.miha.sudocu.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;
import com.example.miha.sudocu.mvp.view.adapter.AdapterChallenge;
import com.example.miha.sudocu.mvp.presenter.PresenterRecordsListFragment;
import com.example.miha.sudocu.mvp.view.activity.MainActivity;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;
import com.example.miha.sudocu.utils.SerializableGame;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class RecordsListFragment extends BaseMvpFragment implements IRecordsList {
    @BindView(R.id.recycler_list_records)
    RecyclerView listOfRecords;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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
        adapter.setOnClickListener(() -> presenter.choiceChallenge(adapter.getItem(adapter.getIdChoseItem())));
        return v;
    }

    @Override
    public void showRecords(List<LocalChallenge> challenges) {
        adapter.setValueList(challenges);
    }

    @Override
    public void choiceChallenge(Grid game) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Grid.KEY, SerializableGame.serializable(game));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void dontStartCompleteGame() {
        Toast.makeText(getActivity(),"dont start complete game",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(Boolean flag) {
        if(flag){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
