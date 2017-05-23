package com.example.miha.sudocu.View.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.IView.IFragmentRegistration;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterRegistration;
import com.example.miha.sudocu.presenter.PresenterRegistrationFragment;


public class RegistrationFragment extends Fragment implements View.OnClickListener, IFragmentRegistration {
    private Button auth;
    private EditText login, password;
    private IPresenterRegistration presenter;

    private LoginCallback mCallbacks;


    public interface LoginCallback {
        void onLogin(User user);
    }

    @Override
    public void onLogin(User user) {
        mCallbacks.onLogin(user);
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        presenter = new PresenterRegistrationFragment(this);
        if (activity instanceof LoginCallback) {
            mCallbacks = (LoginCallback) activity;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.registration_fragment, container, false);
        auth = (Button) rootView.findViewById(R.id.auth);
        password = (EditText) rootView.findViewById(R.id.password);
        login = (EditText) rootView.findViewById(R.id.login);
        auth.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth:
                presenter.login(login.getText().toString(), password.getText().toString());
                break;
        }
    }
}
