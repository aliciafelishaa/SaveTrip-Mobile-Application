package com.example.savetrip.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.model.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ItemViewHolder> {
    ArrayList<Trip> trips;

    public TripAdapter(ArrayList<Trip> trip){
        this.trips=trip;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDestination, tvBudget, tvOutcome, tvStartDate, tvEndDate;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvOutcome = itemView.findViewById(R.id.tvOutcome);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
        }
    }

    @NonNull
    @Override
    public TripAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_trip,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ItemViewHolder holder, int position) {
        if (trips == null || trips.size() == 0) return;
        Trip currTrip = trips.get(position);
        holder.tvName.setText(currTrip.getTripName());
        holder.tvDestination.setText(currTrip.getDestinationCountry());
        holder.tvBudget.setText(String.valueOf(currTrip.getInitialBudget()));
        holder.tvOutcome.setText(String.valueOf(currTrip.getOutcomeTotalTransaction()));
        holder.tvStartDate.setText(currTrip.getStartDate());
        holder.tvEndDate.setText(currTrip.getEndDate());

    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }


}
