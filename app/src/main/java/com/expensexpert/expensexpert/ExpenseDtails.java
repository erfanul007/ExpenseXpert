package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ExpenseDtails extends AppCompatActivity {
    TextView name, amount, category, note, date, members;
    int GroupId, ExpenseId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_dtails);

        name = findViewById(R.id.expense_details_name);
        amount = findViewById(R.id.expense_details_amount);
        category = findViewById(R.id.expense_details_category);
        note = findViewById(R.id.expense_details_note);
        date = findViewById(R.id.expense_details_date);
        members = findViewById(R.id.expense_details_contrib);

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);
        ExpenseId = getgroupid.getIntExtra("ExpenseId", 1);

        DatabaseHelper db = new DatabaseHelper(this);
        Expense expense = db.get_Expense_specific(GroupId, ExpenseId);
        List<Contributors> everyone = db.get_Expense_Contributors(GroupId, ExpenseId);
        name.setText("Title: "+expense.getName());
        amount.setText("Amount: "+ String.format("%.2f",expense.getAmount()));
        category.setText("Category: "+expense.getCategory());
        note.setText("Note: "+expense.getNote());
        date.setText("Updated at: "+ String.valueOf(expense.getCreatedate()));
        String memberlist="";
        for(int i=0; i<everyone.size(); i++){
            if(i>0) memberlist+=", ";
            memberlist += everyone.get(i).getName();
        }
        members.setText("Contributors: "+memberlist);
    }
}