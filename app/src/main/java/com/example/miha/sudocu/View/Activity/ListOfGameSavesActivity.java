package com.example.miha.sudocu.View.Activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.IView.IDialogManager;
import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.View.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.View.fragment.ListOfGameFragment;
import com.example.miha.sudocu.View.fragment.RegistrationFragment;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.Adapter.AdapterLocalGameList;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;


public class ListOfGameSavesActivity extends FragmentActivity implements RegistrationFragment.LoginCallback, IDialogManager {
    private AlertDialog dialog;
    private DialogFragment dialogFragment;
    private AdapterLocalGameList adapterLocalGameList;

    @Override
    public void showAuthDialog() {
        dialogFragment.show(getSupportFragmentManager(), "tag");
        Toast.makeText(this, "show Auth dialog", Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeAuthDialog() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_game_saves);
        dialogFragment = new RegistrationFragment();
        initTabLayout();
        initToolbar();
        dialog = new AlertDialog(this);
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.list_game);
        toolbar.setTitle(R.string.games);
        toolbar.setOnMenuItemClickListener(item -> {
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
        });
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
