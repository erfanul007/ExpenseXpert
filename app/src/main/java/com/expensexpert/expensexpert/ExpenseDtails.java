package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.expensexpert.expensexpert.models.Contributors;
import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.Expense;

import java.time.format.DateTimeFormatter;
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
//        Log.e("Expense ID",Integer.toString(ExpenseId));
//        Log.e("GroupId ID",Integer.toString(GroupId));
        Expense expense = db.get_Expense_specific(GroupId, ExpenseId);
        List<Contributors> everyone = db.get_Expense_Contributors(GroupId, ExpenseId);
        name.setText(expense.getName());
        amount.setText(String.format("%.2f",expense.getAmount()));
        category.setText(expense.getCategory());
        note.setText(expense.getNote());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy, hh:mm a");
        date.setText(expense.getCreatedate().format(formatter));
        String memberlist="";
        for(int i=0; i<everyone.size(); i++){
            if(i>0) memberlist+=", ";
            memberlist += everyone.get(i).getName();
        }
        members.setText(memberlist);
    }

    public void UpdateExpense(View view) {
        Intent intent = new Intent(getApplicationContext(), Update_Expense.class);
        intent.putExtra("GroupId", GroupId);
        intent.putExtra("ExpenseId", ExpenseId);
        startActivity(intent);
        finish();
    }

    public void DeleteExpense(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.delete_ExGroButors_byexpense(ExpenseId);
        db.delete_Expense(ExpenseId);

        Toast.makeText(this, "Expense Deleted", Toast.LENGTH_LONG).show();
        finish();
    }
}