package com.example.miha.sudocu.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.miha.sudocu.mvp.view.intf.ItemClickListener;
import com.example.miha.sudocu.mvp.view.intf.ItemContextMenuListener;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> objects = new ArrayList<>();
    protected int lastShownItem = -1;
    protected int layout;
    protected ItemClickListener onClickListener = null;
    protected ItemContextMenuListener onLongClickListener = null;

    protected abstract VH getMyHolder(View view);


    public BaseRecyclerViewAdapter(@LayoutRes int res, ArrayList<T> ob, ItemClickListener clickListener, ItemContextMenuListener contextMenuListener) {
        this.layout = res;
        this.onClickListener = clickListener;
        this.onLongClickListener = contextMenuListener;
        if (ob != null) {
            this.objects = ob;
        }
    }

    public ItemClickListener getClickListener() {
        return this.onClickListener;
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.onClickListener = listener;
    }

    public ItemContextMenuListener getOnLongClickListener() {
        return this.onLongClickListener;
    }

    public void setOnLongClickListener(ItemContextMenuListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public Integer getIdChoseItem() {
        return this.lastShownItem;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(this.layout, parent, false);
        return this.getMyHolder(view);
    }

    public void setValueList(List<T> valueList) {
        this.objects = valueList;
        this.lastShownItem = -1;
        notifyDataSetChanged();
    }

    public void addValue(T value) {
        objects.add(value);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public T getItem(int position) {
        return objects.get(position);
    }

    public boolean removeObjectAtPosition(int position) {
        if (position >= objects.size()) return false;
        objects.remove(position);
        this.notifyItemRemoved(position);
        return true;
    }
}
