package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.utils.ConverterTime;

import java.util.ArrayList;


public class AdapterChallenge extends BaseAdapter {
    ArrayList<Challenge> arrayChallenges = new ArrayList<>();
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
        ((TextView) view.findViewById(R.id.gameName)).setText(challenge.getGrid().getName());
        Long minute = ConverterTime.getInstance().converterLongToMinutes(challenge.getGrid().getGameTime());
        Long second = ConverterTime.getInstance().converterLongToSeconds(challenge.getGrid().getGameTime());
        ((TextView) view.findViewById(R.id.gameTime)).setText(minute.toString()+":"+second.toString());
        return view;
    }


    public void setData(ArrayList<Challenge> challenges) {
        arrayChallenges = challenges;
        notifyDataSetChanged();
    }

    public AdapterChallenge(Context ctx) {
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
