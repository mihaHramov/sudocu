package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.Grid;

import java.util.ArrayList;

public class AdapterGrid extends BaseAdapter {
    ArrayList<Grid> arrayGrid = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;


    @Override
    public int getCount() {
        return arrayGrid.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGrid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }
        Grid grid = (Grid) getItem(position);
        ((TextView) view.findViewById(R.id.gridListItemName)).setText(grid.getName());
        Chronometer ch = new Chronometer(ctx);
        ch.setBase(SystemClock.elapsedRealtime() - grid.getGameTime());
        ((TextView) view.findViewById(R.id.timeString)).setText(ctx.getString(R.string.time) + ch.getText().toString());
        ((TextView) view.findViewById(R.id.gridListItemUndefined)).setText(ctx.getString(R.string.undefined) + "(" + Integer.toString(grid.getUndefined()) + ")");
        return view;
    }


    public void setData(ArrayList<Grid> products) {
        arrayGrid = products;
    }

    public AdapterGrid(Context ctx) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterGrid(Context context, ArrayList<Grid> products) {
        ctx = context;
        arrayGrid = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
