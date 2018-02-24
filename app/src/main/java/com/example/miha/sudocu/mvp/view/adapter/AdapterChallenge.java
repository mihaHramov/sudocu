package com.example.miha.sudocu.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;
import com.example.miha.sudocu.utils.ConverterTime;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterChallenge extends BaseRecyclerViewAdapter<AdapterChallenge.ViewHolder, LocalChallenge> {
    Context context;
    @Override
    protected ViewHolder getMyHolder(View view) {
        return new ViewHolder(view);
    }

    public AdapterChallenge(@LayoutRes int res, Context ctx) {
        super(res, null, null, null);
        context = ctx;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocalChallenge challenge = objects.get(position);
        ConverterTime converter = ConverterTime.getInstance();
        Long time = challenge.getChallenge().getGrid().getGameTime();
        String minute = Long.toString(converter.converterLongToMinutes(time));
        String seconds = Long.toString(converter.converterLongToSeconds(time));

        if (challenge.getLocalGame() == null) {
            holder.status.setText("Не начато");
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.localGameView.setVisibility(View.VISIBLE);
            if (challenge.getLocalGame().isGameOver()) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.status.setText("завершенно");
            } else {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.poleFocus));
                holder.status.setText("начал");
            }
            String localSecond = Long.toString(converter.converterLongToSeconds(challenge.getLocalGame().getGameTime()));
            String localMinute = Long.toString(converter.converterLongToMinutes(challenge.getLocalGame().getGameTime()));
            String localTime =  localMinute + ":" + localSecond;
            holder.localGameTime.setText(localTime);

        }
        holder.cardView.setOnClickListener(v -> {
            lastShownItem = holder.getAdapterPosition();
            onClickListener.onItemClick();
        });
        holder.login.setText(challenge.getChallenge().getLogin());
        holder.gameName.setText(challenge.getChallenge().getGrid().getName());
        String challengeTime = minute + ":" + seconds;
        holder.gameTime.setText(challengeTime);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.localGameTime)
        TextView localGameTime;
        @BindView(R.id.localGame)
        View localGameView;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.gameTime)
        TextView gameTime;
        @BindView(R.id.login)
        TextView login;
        @BindView(R.id.gameName)
        TextView gameName;
        @BindView(R.id.cardViewItemChallenge)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
