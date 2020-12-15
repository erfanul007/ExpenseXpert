package com.expensexpert.expensexpert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.MyViewHolder> {

    private List<Balance> balancelist;
    private BalanceAdapter.RecyclerViewClickListener listener;

    public BalanceAdapter(List<Balance> balancelist, BalanceAdapter.RecyclerViewClickListener listener) {
        this.balancelist = balancelist;
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull BalanceAdapter.MyViewHolder holder, int position) {
        String name = balancelist.get(position).getName();
        double amount = balancelist.get(position).getAmount();

        holder.name.setText(name);
        holder.amount.setText(String.format("%.2f",amount));
    }

    @Override
    public int getItemCount() {
        return balancelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.member_name);
            amount = itemView.findViewById(R.id.member_balance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public BalanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}
