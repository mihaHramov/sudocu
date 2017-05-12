package com.example.miha.sudocu.View;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.RepositoryImplBD;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;


public class ListOfGameSavesActivity extends Activity {
    private AdapterGrid adapter;
    private RepositoryImplBD repository;
    private ListView listView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_game_saves);
        repository = new RepositoryImplBD(getApplicationContext());
        adapter = new AdapterGrid(getApplicationContext());

        listView = (ListView) findViewById(R.id.listViewGrid);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(Grid.KEY, (Grid) adapter.getItem(position));
                startActivity(i);
            }
        });

        dialog = new AlertDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.list_game);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigateButtonBack:
                        finish();
                        break;
                    case R.id.newGame:
                        dialog.showDialog();
                        break;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setData(repository.getListGames());
        adapter.notifyDataSetChanged();
    }
}
