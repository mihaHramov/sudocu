package com.example.miha.sudocu.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.Grid;

import java.util.ArrayList;

/**
 * Created by miha on 16.01.17.
 */

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
        Grid grid  = (Grid) getItem(position);
        ((TextView) view.findViewById(R.id.gridListItemText)).setText(grid.getName());

        return view;
    }


    public AdapterGrid(Context context, ArrayList<Grid> products) {
        ctx = context;
        arrayGrid = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
