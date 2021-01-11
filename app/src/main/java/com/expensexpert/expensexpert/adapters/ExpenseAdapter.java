package com.expensexpert.expensexpert.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expensexpert.expensexpert.R;
import com.expensexpert.expensexpert.models.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private List<Expense> expenselist;
    private ExpenseAdapter.RecyclerViewClickListener listener;

    public ExpenseAdapter(List<Expense> expenselist, ExpenseAdapter.RecyclerViewClickListener listener) {
        this.expenselist = expenselist;
        this.listener = listener;
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.expense_title_a);
            amount = itemView.findViewById(R.id.expense_balance_a);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}
