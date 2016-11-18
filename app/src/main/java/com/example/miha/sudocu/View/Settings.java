package com.example.miha.sudocu.View;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.IView.ISettings;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterSettings;
import com.example.miha.sudocu.presenter.PresenterSettings;

public class Settings extends Activity implements ISettings {
    Button btn ;
    EditText setting;
    IPresenterSettings presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btn = (Button)findViewById(R.id.saveSetting);
        setting = (EditText)findViewById(R.id.enterNumber);
        presenter = new PresenterSettings(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save(setting.getText().toString());
            }
        });
    }

    @Override
    public void success() {
        Toast.makeText(Settings.this, "yes", Toast.LENGTH_SHORT).show();
    }
}
