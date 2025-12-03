package com.example.savetrip.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.MainActivity;
import com.example.savetrip.R;
import com.example.savetrip.model.Trip;
import com.example.savetrip.view.DetailViewActivity;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ItemViewHolder> {
    ArrayList<Trip> trips;

    public TripAdapter(ArrayList<Trip> trip){
        this.trips=trip;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDestination, tvBudget, tvOutcome, tvStartDate, tvEndDate;
        Button btnDetails;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvOutcome = itemView.findViewById(R.id.tvOutcome);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            btnDetails = itemView.findViewById(R.id.btnDetails);
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
        holder.tvDestination.setText("Destination: "+ currTrip.getDestinationCountry());
        holder.tvBudget.setText("Budget: "+ String.valueOf(currTrip.getInitialBudget()));
        holder.tvOutcome.setText("Outcome: "+ String.valueOf(currTrip.getOutcomeTotalTransaction()));
        holder.tvStartDate.setText("Start Date: "+ currTrip.getStartDate());
        holder.tvEndDate.setText("End Date: "+ currTrip.getEndDate());


        holder.btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailViewActivity.class);
            // KIRIM DATA KE DETAIL
            intent.putExtra("trip_id", currTrip.getTripId());
            intent.putExtra("tripName", currTrip.getTripName());
            intent.putExtra("destination", currTrip.getDestinationCountry());
            intent.putExtra("budget", currTrip.getInitialBudget());
            intent.putExtra("outcome", currTrip.getOutcomeTotalTransaction());
            intent.putExtra("startDate", currTrip.getStartDate());
            intent.putExtra("endDate", currTrip.getEndDate());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }




}
