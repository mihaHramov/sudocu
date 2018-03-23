package com.example.miha.sudocu.mvp.view.activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.view.events.OnUserLogin;
import com.example.miha.sudocu.mvp.view.intf.IDialogManager;
import com.example.miha.sudocu.mvp.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.view.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.view.fragment.ListOfGameFragment;
import com.example.miha.sudocu.mvp.view.fragment.RegistrationFragment;
import com.example.miha.sudocu.mvp.view.adapter.AdapterLocalGameList;
import com.example.miha.sudocu.mvp.view.adapter.AlertDialog;
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
        adapterLocalGameList = new AdapterLocalGameList(getSupportFragmentManager());
        Fragment completeGameFragment;
        Fragment listOfGameFragment;
        if (savedInstanceState != null) {
            listOfGameFragment =  getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 0);
            completeGameFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 1);
        }else{
            listOfGameFragment = new ListOfGameFragment();
            completeGameFragment = new ListOfCompleteGameFragment();
        }
        adapterLocalGameList.addFragment(listOfGameFragment, getString(R.string.un_complete_game));
        adapterLocalGameList.addFragment(completeGameFragment, getString(R.string.complete_game));

        viewPager.setAdapter(adapterLocalGameList);
        tabLayout.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            getSupportActionBar().setTitle(R.string.games);
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
