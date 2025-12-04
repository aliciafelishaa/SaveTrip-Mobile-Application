package com.example.savetrip.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.model.TripExchangeRate;

import java.util.ArrayList;

public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder> {

    private ArrayList<TripExchangeRate> rates;

    public ExchangeRateAdapter(ArrayList<TripExchangeRate> rates) {
        this.rates = rates;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCurrency, txtRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCurrency = itemView.findViewById(R.id.txtCurrencys);
            txtRate = itemView.findViewById(R.id.txtRates);
        }
    }

    @NonNull
    @Override
    public ExchangeRateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_exchange_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeRateAdapter.ViewHolder holder, int position) {
        TripExchangeRate rate = rates.get(position);
        holder.txtCurrency.setText(rate.getCurrency());
        holder.txtRate.setText(String.valueOf(rate.getRate()));
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }


}
