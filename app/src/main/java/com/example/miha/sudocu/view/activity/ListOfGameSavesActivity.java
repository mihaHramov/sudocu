package com.example.miha.sudocu.view.activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.events.OnUserLogin;
import com.example.miha.sudocu.view.intf.IDialogManager;
import com.example.miha.sudocu.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.view.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.view.fragment.ListOfGameFragment;
import com.example.miha.sudocu.view.fragment.RegistrationFragment;
import com.example.miha.sudocu.view.Adapter.AdapterLocalGameList;
import com.example.miha.sudocu.view.Adapter.AlertDialog;
import com.squareup.otto.Subscribe;

import butterknife.BindView;


public class ListOfGameSavesActivity extends BaseMvpActivity implements IDialogManager {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tableLayout)
    TabLayout tabLayout;

    private AlertDialog dialog;
    private DialogFragment dialogFragment;
    private AdapterLocalGameList adapterLocalGameList;

    @Override
    public void showAuthDialog() {
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_of_game_saves;
    }

    @Override
    public void closeAuthDialog() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogFragment = new RegistrationFragment();
        initTabLayout();
        if (getSupportActionBar() != null) {
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
        viewPager.setAdapter(adapterLocalGameList);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe
    public void onLogin(OnUserLogin onUserLogin) {
        if (onUserLogin.getUser() == null) return;
        dialogFragment.dismiss();//закрыл диалог
        int i = tabLayout.getSelectedTabPosition();
        Fragment fr = adapterLocalGameList.getItem(i);
        if (fr instanceof IListOfCompleteGameFragment) {
            ((IListOfCompleteGameFragment) fr).onAfterAuthUser();
        }
    }
}
