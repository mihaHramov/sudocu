package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.utils.ConverterTime;

import java.util.ArrayList;

public class AdapterGrid extends BaseAdapter {
    private ArrayList<Grid> arrayGrid = new ArrayList<>();
    private Context ctx;
    private LayoutInflater lInflater;


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
        ConverterTime converter = ConverterTime.getInstance();
        String str = ctx.getString(R.string.time) + converter.converterLongToMinutes(grid.getGameTime())+":"+converter.converterLongToSeconds(grid.getGameTime());
        ((TextView) view.findViewById(R.id.gridListItemName)).setText(grid.getName());
        ((TextView) view.findViewById(R.id.timeString)).setText(str);
        return view;
    }


    public void deleteItemById(int gridId){
        arrayGrid.remove(gridId);
        notifyDataSetChanged();
    }
    public void setData(ArrayList<Grid> products) {
        arrayGrid = products;
        notifyDataSetChanged();
    }

    public AdapterGrid(Context ctx) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}