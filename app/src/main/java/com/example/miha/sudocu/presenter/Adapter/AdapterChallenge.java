package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Challenge;

import java.util.ArrayList;



public class AdapterChallenge extends BaseAdapter {
    ArrayList<Challenge> arrayChallenges = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;


    @Override
    public int getCount() {
        return arrayChallenges.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayChallenges.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = lInflater.inflate(R.layout.item_challenge, parent, false);//создать свой вид
        }
        Challenge challenge = (Challenge) getItem(position);
        ((TextView) view.findViewById(R.id.login)).setText(challenge.getLogin());
        return view;
    }


    public void setData(ArrayList<Challenge> challenges) {
        arrayChallenges = challenges;
    }

    public AdapterChallenge(Context ctx) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterChallenge(Context context, ArrayList<Challenge> challenges) {
        ctx = context;
        arrayChallenges = challenges;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
