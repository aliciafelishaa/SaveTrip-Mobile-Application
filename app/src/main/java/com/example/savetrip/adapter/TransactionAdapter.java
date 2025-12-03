package com.example.savetrip.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.model.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ItemViewHolder> {
    ArrayList<Transaction> trans;

    public TransactionAdapter(ArrayList<Transaction> tran){this.trans = tran;};

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvTransNames, tvTransDesc, tvCategory, tvDate,tvAmount;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransNames = itemView.findViewById(R.id.tvTransName);
            tvTransDesc = itemView.findViewById(R.id.tvDescription);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

    @NonNull
    @Override
    public TransactionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_transaction,parent,false);
        return new TransactionAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ItemViewHolder holder, int position) {
        if(trans==null || trans.size()==0) return;

        Transaction curr = trans.get(position);
        holder.tvTransNames.setText(curr.getName());
        holder.tvCategory.setText(curr.getCategoryName());
        holder.tvTransDesc.setText(curr.getDescription());
        holder.tvAmount.setText(String.valueOf(curr.getAmount()));
        holder.tvDate.setText(curr.getTransactionDate());

        // Tambahkan tanda + / - dan warna
        if(curr.getType().equalsIgnoreCase("income")){
            holder.tvAmount.setText("+ " + curr.getAmount());
            holder.tvAmount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green)); // hijau
        } else if(curr.getType().equalsIgnoreCase("expense")){
            holder.tvAmount.setText("- " + curr.getAmount());
            holder.tvAmount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red)); // merah
        }


    }

    @Override
    public int getItemCount() {
        return trans == null ? 0 : trans.size();
    }
}
