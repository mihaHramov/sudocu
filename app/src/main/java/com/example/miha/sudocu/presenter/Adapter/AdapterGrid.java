package com.example.miha.sudocu.presenter.Adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.utils.ConverterTime;
import com.example.miha.sudocu.view.intf.ItemClickListener;
import com.example.miha.sudocu.view.intf.ItemContextMenuListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterGrid extends BaseRecyclerViewAdapter<AdapterGrid.ViewHolder, Grid> {

    public AdapterGrid(@LayoutRes int res) {
        super(res,new ArrayList<>(),null,null);
    }

    @Override
    public ViewHolder getMyHolder(View view) {
        return new AdapterGrid.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Grid grid = objects.get(position);
        ConverterTime converter = ConverterTime.getInstance();
        String str = context.getString(R.string.time) + converter.converterLongToMinutes(grid.getGameTime()) + ":" + converter.converterLongToSeconds(grid.getGameTime());
        holder.gridItem.setText(grid.getName());
        holder.timeText.setText(str);
        if (getClickListener() != null) {
            holder.item.setOnClickListener(v -> {
                this.lastShownItem = holder.getAdapterPosition();
                getClickListener().onItemClick();
            });
        }
        if (getOnLongClickListener() != null) {
            holder.item.setOnLongClickListener(v -> {
                this.lastShownItem = holder.getAdapterPosition();
                getOnLongClickListener().addContextMenuToView(v);
                return false;
            });
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.timeString)
        TextView timeText;
        @BindView(R.id.gridListItemName)
        TextView gridItem;
        @BindView(R.id.cardView)
        CardView item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}