package com.expensexpert.expensexpert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private List<Expense> expenselist;
//    private ExpenseAdapter.RecyclerViewClickListener listener;

    public ExpenseAdapter(List<Expense> expenselist) {
        this.expenselist = expenselist;
//        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        String title = expenselist.get(position).getName();
        double amount = expenselist.get(position).getAmount();

        holder.title.setText(title);
        holder.amount.setText(String.format("%.2f",amount));
    }

    @Override
    public int getItemCount() {
        return expenselist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.member_name);
            amount = itemView.findViewById(R.id.member_balance);
//            itemView.setOnClickListener(this);
        }

    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);

        return new MyViewHolder(itemView);
    }

//    public interface RecyclerViewClickListener {
//        void onClick(View view, int position);
//    }
}
