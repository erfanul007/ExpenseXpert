package com.expensexpert.expensexpert.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.expensexpert.expensexpert.AddGroup;
import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.Group;
import com.expensexpert.expensexpert.R;
import com.expensexpert.expensexpert.SingleTour;
import com.expensexpert.expensexpert.adapters.TourAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<Group> tourArrayList;
    private RecyclerView recyclerView;
    private TourAdapter.RecyclerViewClickListener listener;
    TourAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.tour_list);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        setTourInfo();
        setAdapter();

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddGroup.class);
                startActivity(intent);
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        setTourInfo();
        setAdapter();
    }

    private void setAdapter() {
        setOnclickListener();
        adapter = new TourAdapter(tourArrayList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnclickListener() {
        listener = new TourAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), SingleTour.class);
                intent.putExtra("GroupId", tourArrayList.get(position).getId());
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTourInfo() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        tourArrayList = db.get_Group_active();
    }
}