package com.expensexpert.expensexpert;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {

    private List<Expense> expenseArrayList;
    private RecyclerView recyclerView;
//    private ExpenseAdapter.RecyclerViewClickListener listener;
    private FloatingActionButton fab;
    int GroupId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_expense, container, false);


        recyclerView = root.findViewById(R.id.expense_list);
        expenseArrayList = new ArrayList<>();
        fab = root.findViewById(R.id.add_expense);

        Bundle bundle = this.getArguments();
        GroupId = bundle.getInt("GroupId");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddExpense.class);
                intent.putExtra("GroupId", GroupId);
                startActivity(intent);
            }
        });

        setexpenseInfo();
        setAdapter();
        return root;
    }

    private void setAdapter() {
//        setOnclickListener();
        ExpenseAdapter adapter = new ExpenseAdapter(expenseArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

//    private void setOnclickListener() {
//        listener = new ExpenseAdapter.RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(getContext(), ExpenseDtails.class);
//                intent.putExtra("GroupId", GroupId);
//                intent.putExtra("ExpenseId", expenseArrayList.get(position).getId());
//                startActivity(intent);
//            }
//        };
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setexpenseInfo() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        expenseArrayList = db.get_Expense_active(GroupId);
    }

//    public void onClick(View view, int position) {
//        Log.e("works", "works");
//        Intent intent = new Intent(getContext(), ExpenseDtails.class);
//        intent.putExtra("GroupId", GroupId);
//        intent.putExtra("ExpenseId", expenseArrayList.get(position).getId());
//        startActivity(intent);
//    }
}
