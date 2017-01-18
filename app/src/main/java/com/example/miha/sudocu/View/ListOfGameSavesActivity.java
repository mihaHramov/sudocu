package com.example.miha.sudocu.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.RepositoryImplBD;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;

public class ListOfGameSavesActivity extends Activity {
    AdapterGrid adapter ;
    RepositoryImplBD repository;

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new AdapterGrid(getApplicationContext(),repository.getListGames());
        ListView lv = (ListView)findViewById(R.id.listViewGrid);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(Grid.KEY,(Grid)adapter.getItem(position));
                startActivity(i);
            }
        });
    //    adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_game_saves);
        repository = new RepositoryImplBD(getApplicationContext());
    }
}
