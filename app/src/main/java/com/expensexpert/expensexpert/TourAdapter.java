package com.expensexpert.expensexpert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.MyViewHolder> {

    private List<Group> tourList;

    private RecyclerViewClickListener listener;

    public TourAdapter(List<Group> tourList, RecyclerViewClickListener listener) {
        this.tourList = tourList;
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull TourAdapter.MyViewHolder holder, int position) {
        String title = tourList.get(position).getName();
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tour_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TourAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}
