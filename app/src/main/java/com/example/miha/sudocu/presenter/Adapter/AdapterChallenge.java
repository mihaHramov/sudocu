package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.utils.ConverterTime;

import java.util.ArrayList;


public class AdapterChallenge extends RecyclerView.Adapter<AdapterChallenge.ViewHolder> {
    ArrayList<Challenge> arrayChallenges = new ArrayList<>();

    public AdapterChallenge(ArrayList<Challenge> arrayChallenges) {
        this.arrayChallenges = arrayChallenges;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.item_challenge,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Challenge challenge =  arrayChallenges.get(position);
       ConverterTime converter =  ConverterTime.getInstance();
       Long time = challenge.getGrid().getGameTime();
       String minute =  Long.toString(converter.converterLongToMinutes(time));
       String seconds =  Long.toString(converter.converterLongToSeconds(time));
       holder.login.setText(challenge.getLogin());
       holder.gameName.setText(challenge.getGrid().getName());
       holder.gameTimeMinute.setText(minute);
       holder.gameTimeSeconds.setText(seconds);
    }

    @Override
    public int getItemCount() {
        return arrayChallenges.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView login;
        TextView gameTimeMinute;
        TextView gameName;
        TextView gameTimeSeconds;
        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            gameName = (TextView) itemView.findViewById(R.id.gameName);
            gameTimeMinute = (TextView) itemView.findViewById(R.id.gameTimeMinute);
            gameTimeSeconds = (TextView) itemView.findViewById(R.id.gameTimeSecond);
        }
    }
}
