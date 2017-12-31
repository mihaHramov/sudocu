package com.example.miha.sudocu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnUserLogin;
import com.example.miha.sudocu.view.intf.IFragmentRegistration;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;


public class RegistrationFragment extends DialogFragment implements  IFragmentRegistration {
    private Button auth;
    private EditText login, password;
    private IPresenterRegistration presenter;

    @Override
    public void onLogin(User user) {
        // прячем клавиатуру
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(auth.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        BusProvider.getInstance().post(new OnUserLogin(user));
        auth.setEnabled(true);
    }


    @Override
    public void onFailAuth(String error) {
        auth.setEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = DP.get().getPresenterRegistrationFragment();
        presenter.setView(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
        View rootView =
                inflater.inflate(R.layout.registration_fragment, container, false);
        auth = (Button) rootView.findViewById(R.id.auth);
        password = (EditText) rootView.findViewById(R.id.password);
        login = (EditText) rootView.findViewById(R.id.login);
        auth.setOnClickListener(v -> {
            presenter.login(login.getText().toString(), password.getText().toString());
            auth.setEnabled(false);
        });
        return rootView;
    }
}
