package com.example.miha.sudocu.view.activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.IView.IDialogManager;
import com.example.miha.sudocu.view.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.view.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.view.fragment.ListOfGameFragment;
import com.example.miha.sudocu.view.fragment.RegistrationFragment;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.Adapter.AdapterLocalGameList;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListOfGameSavesActivity extends AppCompatActivity implements RegistrationFragment.LoginCallback, IDialogManager {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AlertDialog dialog;
    private DialogFragment dialogFragment;
    private AdapterLocalGameList adapterLocalGameList;

    @Override
    public void showAuthDialog() {
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void closeAuthDialog() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_game_saves);
        ButterKnife.bind(this);
        dialogFragment = new RegistrationFragment();
        initTabLayout();
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        dialog = new AlertDialog(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                dialog.showDialog();
                break;
        }
        return false;
    }

    public void initTabLayout() {
        adapterLocalGameList = new AdapterLocalGameList(getSupportFragmentManager());
        adapterLocalGameList.addFragment(new ListOfGameFragment(), getString(R.string.un_complete_game));
        adapterLocalGameList.addFragment(new ListOfCompleteGameFragment(), getString(R.string.complete_game));
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterLocalGameList);
        ((TabLayout) findViewById(R.id.tableLayout)).setupWithViewPager(viewPager);

    }

    @Override
    public void onLogin(User user) {
        if (user != null) {
            dialogFragment.dismiss();
            int i = ((TabLayout) findViewById(R.id.tableLayout)).getSelectedTabPosition();
            Fragment fr = adapterLocalGameList.getItem(i);
            if (fr instanceof IListOfCompleteGameFragment){
                ((IListOfCompleteGameFragment)fr).onAfterAuthUser();
            }
        }
    }
}
