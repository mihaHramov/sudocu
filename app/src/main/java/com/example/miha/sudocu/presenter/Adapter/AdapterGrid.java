package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.utils.ConverterTime;
import com.example.miha.sudocu.view.intf.ItemClickListener;
import com.example.miha.sudocu.view.intf.ItemContextMenuListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterGrid extends RecyclerView.Adapter<AdapterGrid.ViewHolder> {
    private ArrayList<Grid> arrayGrid = new ArrayList<>();
    private Integer idRecord = null;
    private ItemClickListener onClickListener = null;
    private ItemContextMenuListener onLongClickListener = null;
    private Context ctx;

    public AdapterGrid() {
    }

    public AdapterGrid(ItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(ItemContextMenuListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public ItemClickListener getClickListener() {
        return this.onClickListener;
    }

    public Integer getIdChoseItem() {
        return this.idRecord;
    }

    public ItemContextMenuListener getOnLongClickListener() {
        return this.onLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new AdapterGrid.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Grid grid = arrayGrid.get(position);
        ConverterTime converter = ConverterTime.getInstance();
        String str = ctx.getString(R.string.time) + converter.converterLongToMinutes(grid.getGameTime()) + ":" + converter.converterLongToSeconds(grid.getGameTime());
        holder.gridItem.setText(grid.getName());
        holder.timeText.setText(str);
        if (getClickListener() != null) {
            holder.item.setOnClickListener(v -> {
                this.idRecord = holder.getAdapterPosition();
                getClickListener().onItemClick();
            });
        }
        if (getOnLongClickListener() != null) {
            holder.item.setOnLongClickListener(v -> {
                this.idRecord = holder.getAdapterPosition();
                getOnLongClickListener().addContextMenuToView(v);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayGrid.size();
    }

    public Grid getItem(int gridId) {
        return arrayGrid.get(gridId);
    }

    public void deleteItemById(int gridId) {
        arrayGrid.remove(gridId);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Grid> products) {
        arrayGrid = products;
        notifyDataSetChanged();
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