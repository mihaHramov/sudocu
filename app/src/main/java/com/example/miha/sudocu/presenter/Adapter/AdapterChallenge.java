package com.example.miha.sudocu.presenter.Adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.utils.ConverterTime;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterChallenge extends BaseRecyclerViewAdapter<AdapterChallenge.ViewHolder,Challenge>
{
    @Override
    protected ViewHolder getMyHolder(View view) {
        return new ViewHolder(view);
    }

    public AdapterChallenge(@LayoutRes int res) {
        super(res,null,null,null);
    }

    public AdapterChallenge(@LayoutRes int res, ArrayList<Challenge> challenges) {
        super(res,challenges,null,null);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Challenge challenge = objects.get(position);
        ConverterTime converter = ConverterTime.getInstance();
        Long time = challenge.getGrid().getGameTime();
        String minute = Long.toString(converter.converterLongToMinutes(time));
        String seconds = Long.toString(converter.converterLongToSeconds(time));
        holder.login.setText(challenge.getLogin());
        holder.gameName.setText(challenge.getGrid().getName());
        holder.gameTimeMinute.setText(minute);
        holder.gameTimeSeconds.setText(seconds);
        holder.cardView.setOnClickListener(v ->{lastShownItem = holder.getAdapterPosition();onClickListener.onItemClick();});
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.login) TextView login;
        @BindView(R.id.gameTimeMinute) TextView gameTimeMinute;
        @BindView(R.id.gameName) TextView gameName;
        @BindView(R.id.gameTimeSecond) TextView gameTimeSeconds;
        @BindView(R.id.cardViewItemChallenge) CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
