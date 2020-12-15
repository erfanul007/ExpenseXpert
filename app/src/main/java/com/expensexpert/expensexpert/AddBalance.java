package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddBalance extends AppCompatActivity {
    private EditText added_balance;
    private TextView member_name;
    int GroupId, ContribId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);
        ContribId = getgroupid.getIntExtra("ContribId", 1);

        added_balance = findViewById(R.id.add_member_balance);
        member_name = findViewById(R.id.member_name_add_balance);

        DatabaseHelper db = new DatabaseHelper(this);
        Contributors contributors = db.get_Contributor_specific(GroupId, ContribId);
        member_name.setText("Member Name: "+contributors.getName());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addBalance(View view) {
        String balance = added_balance.getText().toString();

        if(balance.length()==0){
            Toast.makeText(this, "Add Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(balance);
        Expense expense = new Expense(GroupId, "", amount, "", "", false);
        DatabaseHelper db = new DatabaseHelper(this);
        boolean success = db.add_Expense(expense);
        if(success==false){
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
        int expenseid = db.getlastExpenseId(GroupId);

        success = db.add_ExGroButors(new ExGroButors(GroupId, expenseid, ContribId));

        if(success==false){
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
        added_balance.setText("");
        Toast.makeText(this, "Added Balance", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SingleTour.class);
        intent.putExtra("GroupId", GroupId);
        startActivity(intent);
    }
}