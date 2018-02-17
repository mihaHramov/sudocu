package com.example.miha.sudocu.mvp.data.DP;


import android.content.Context;
import android.content.SharedPreferences;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.mvp.data.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RepositoryUser implements IRepositoryUser {
    private static final String MY_SETTINGS = "my_settings";
    private SharedPreferences sp;

    @Override
    public User getUser() {
        String str = sp.getString(MY_SETTINGS,"");
        if(str.isEmpty()){
            return null;
        }
        Gson g = new Gson();
        return g.fromJson(str,User.class);
    }


    public RepositoryUser(Context ctx) {
        sp = ctx.getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
    }

    @Override
    public void setUser(User user) {
        SharedPreferences.Editor e = sp.edit();
        Gson gs = new GsonBuilder().create();
        String str = gs.toJson(user);
        e.putString(MY_SETTINGS,str);
        e.apply();
    }
}
