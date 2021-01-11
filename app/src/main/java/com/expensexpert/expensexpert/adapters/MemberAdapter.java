package com.expensexpert.expensexpert.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expensexpert.expensexpert.models.Member;
import com.expensexpert.expensexpert.R;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    private List<Member> memberlist;
    private MemberAdapter.RecyclerViewClickListener listener;

    public MemberAdapter(List<Member> Memberlist, MemberAdapter.RecyclerViewClickListener listener) {
        this.memberlist = Memberlist;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MyViewHolder holder, int position) {
        String name = memberlist.get(position).getName();
        double balance = memberlist.get(position).getBalance();
        double expense = memberlist.get(position).getExpense();

        holder.name.setText(name);
        holder.balance.setText(String.format("%.2f",balance));
        holder.expense.setText(String.format("%.2f",expense));
    }

    @Override
    public int getItemCount() {
        return memberlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, balance, expense;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_member_name);
            expense = itemView.findViewById(R.id.list_member_expense);
            balance = itemView.findViewById(R.id.list_member_balance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

}
