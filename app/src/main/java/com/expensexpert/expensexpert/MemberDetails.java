package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.expensexpert.expensexpert.models.Contributors;
import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.Expense;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MemberDetails extends AppCompatActivity {
    TextView name, note, date, list, expense_list;
    int GroupId, MemberId;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        name = findViewById(R.id.member_details_name);
        note = findViewById(R.id.member_details_note);
        date = findViewById(R.id.member_details_date);
        list = findViewById(R.id.member_details_list);
        expense_list = findViewById(R.id.member_details_expenese_list);

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);
        MemberId = getgroupid.getIntExtra("MemberId", 1);

        DatabaseHelper db = new DatabaseHelper(this);

        Contributors contributors = db.get_Contributor_specific(GroupId, MemberId);

        List<Expense> expenselistd = db.get_Contributor_Expense_deactive(GroupId, MemberId);
        List<Expense> expenselista = db.get_Contributor_Expense_active(GroupId, MemberId);
        String balances = "", expenses = "";
        for(int i=0; i<expenselistd.size(); i++){
            balances += expenselistd.get(i).getName();
            balances += ": ";
            List<Contributors> lst = db.get_Expense_Contributors(GroupId, expenselistd.get(i).getId());
            double val = expenselistd.get(i).getAmount()/lst.size();
            balances += String.format("%.2f",val);
            balances += "\nTime: ";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy, hh:mm a");
            balances += expenselistd.get(i).getCreatedate().format(formatter);
            balances+="\n";
            balances+="\n";
        }
        for(int i=0; i<expenselista.size(); i++){
            expenses += expenselista.get(i).getName();
            expenses += "\nspent: ";
            List<Contributors> lst = db.get_Expense_Contributors(GroupId, expenselista.get(i).getId());
            double val = expenselista.get(i).getAmount()/lst.size();
            expenses += String.format("%.2f",val);
            expenses += "\nTime: ";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy, hh:mm a");
            expenses += expenselista.get(i).getCreatedate().format(formatter);
            expenses += "\n";
            expenses +="\n";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy, hh:mm a");
        name.setText(contributors.getName());
        note.setText(contributors.getNote());
        date.setText(contributors.getCreatedate().format(formatter));
        list.setText(balances);
        expense_list.setText(expenses);
    }
}