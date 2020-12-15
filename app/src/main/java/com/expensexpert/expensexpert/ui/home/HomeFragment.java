package com.expensexpert.expensexpert.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.expensexpert.expensexpert.DatabaseHelper;
import com.expensexpert.expensexpert.Group;
import com.expensexpert.expensexpert.R;
import com.expensexpert.expensexpert.SingleTour;
import com.expensexpert.expensexpert.Tour;
import com.expensexpert.expensexpert.TourAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TourAdapter.RecyclerViewClickListener {

    private HomeViewModel homeViewModel;
    private List<Group> tourArrayList;
    private RecyclerView recyclerView;
    private TourAdapter.RecyclerViewClickListener listener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.tour_list);
        FloatingActionButton fab = root.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddGroup.class);
                startActivity(intent);
                setTourInfo();
                setAdapter();
            }
        });


//        tourArrayList = new ArrayList<>();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        setTourInfo();
        setAdapter();
        return root;
    }

    private void setAdapter() {
        setOnclickListener();
        TourAdapter adapter = new TourAdapter(tourArrayList, listener);
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
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTourInfo() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        tourArrayList = db.get_Group_active();
    }



    @Override
    public void onClick(View view, int position) {
        Log.e("works", "works");
        Intent intent = new Intent(getContext(), SingleTour.class);
        intent.putExtra("GroupId", tourArrayList.get(position).getId());
        startActivity(intent);
    }
}