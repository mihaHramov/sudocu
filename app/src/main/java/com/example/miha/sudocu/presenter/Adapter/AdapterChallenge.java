package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.utils.ConverterTime;
import com.example.miha.sudocu.view.intf.ChallengeItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterChallenge extends RecyclerView.Adapter<AdapterChallenge.ViewHolder> {
    private final ChallengeItemClickListener onClickListener;
    ArrayList<Challenge> arrayChallenges = new ArrayList<>();

    public AdapterChallenge(ArrayList<Challenge> arrayChallenges, ChallengeItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
       holder.cardView.setOnClickListener(v -> onClickListener.onItemClick(challenge));
    }

    @Override
    public int getItemCount() {
        return arrayChallenges.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.login) TextView login;
        @BindView(R.id.gameTimeMinute) TextView gameTimeMinute;
        @BindView(R.id.gameName) TextView gameName;
        @BindView(R.id.gameTimeSecond) TextView gameTimeSeconds;
        @BindView(R.id.cardViewItemChallenge) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
