package com.expensexpert.expensexpert;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BalanceFragment extends Fragment {
    TextView current_balance, current_deposit, current_expense;
    private List<Balance> balanceArrayList;
    private RecyclerView recyclerView;
    private BalanceAdapter.RecyclerViewClickListener listener;

    int GroupId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_balance, container, false);

        current_balance = root.findViewById(R.id.current_balance);
        current_deposit = root.findViewById(R.id.current_deposit);
        current_expense = root.findViewById(R.id.current_expense);

        Bundle bundle = this.getArguments();
        GroupId = bundle.getInt("GroupId");

        DatabaseHelper db = new DatabaseHelper(getContext());
        double getamount = db.get_Expense_Amount(db.get_Expense_deactive(GroupId));
        double getexpense = db.get_Expense_Amount(db.get_Expense_active(GroupId));

        current_balance.setText(String.format("%.2f",getamount-getexpense));
        current_deposit.setText(String.format("%.2f", getamount));
        current_expense.setText(String.format("%.2f",getexpense));

        recyclerView = root.findViewById(R.id.balance_list);
        balanceArrayList = new ArrayList<>();

        setBalanceInfo();
        setAdapter();
        return root;
    }

    private void setAdapter() {
        setOnclickListener();
        BalanceAdapter adapter = new BalanceAdapter(balanceArrayList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnclickListener() {
        listener = new BalanceAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), AddBalance.class);
                intent.putExtra("GroupId", GroupId);
                intent.putExtra("ContribId", balanceArrayList.get(position).getId());
                startActivity(intent);
            }
        };
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setBalanceInfo() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Contributors> everyone = db.get_Contributors(GroupId);

        for(int i=0; i<everyone.size(); i++){
            double gave = db.get_Expense_Amount(db.get_Contributor_Expense_deactive(GroupId, everyone.get(i).getId()));
            double spent = db.get_Expense_Amount_div(db.get_Contributor_Expense_active(GroupId, everyone.get(i).getId()));
            Balance balance = new Balance(everyone.get(i).getId(), everyone.get(i).getName(), gave-spent);
            balanceArrayList.add(balance);
        }
    }


    public void onClick(View view, int position) {
        Log.e("works", "works");
        Intent intent = new Intent(getContext(), AddBalance.class);
        intent.putExtra("GroupId", GroupId);
        intent.putExtra("ContribId", balanceArrayList.get(position).getId());
        startActivity(intent);
    }
}
