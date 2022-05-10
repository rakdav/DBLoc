package com.example.dbloc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    interface OnCountryClickListener{
        void onCountryClick(Country state, int position);
    }

    private final OnCountryClickListener onClickListener;
    private final LayoutInflater inflater;
    private List<Country> states;

    StateAdapter(Context context, List<Country> states,OnCountryClickListener onClick) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener=onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Country state = states.get(position);
        holder.nameView.setText(state.getName());
        holder.capitalView.setText(state.getCapital());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onCountryClick(state,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, capitalView;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.capital);
        }
    }

}