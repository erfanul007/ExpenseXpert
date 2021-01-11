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

import java.util.List;

public class MemberDetails extends AppCompatActivity {
    TextView name, note, date, list;
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

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);
        MemberId = getgroupid.getIntExtra("MemberId", 1);

        DatabaseHelper db = new DatabaseHelper(this);

        Contributors contributors = db.get_Contributor_specific(GroupId, MemberId);

        List<Expense> expenselistd = db.get_Contributor_Expense_deactive(GroupId, MemberId);
        List<Expense> expenselista = db.get_Contributor_Expense_active(GroupId, MemberId);
        String s = "";
        for(int i=0; i<expenselistd.size(); i++){
            s += expenselistd.get(i).getName();
            s += ": ";
            List<Contributors> lst = db.get_Expense_Contributors(GroupId, expenselistd.get(i).getId());
            double val = expenselistd.get(i).getAmount()/lst.size();
            s += String.format("%.2f",val);
            s += "\nDate: ";
            s += expenselistd.get(i).getCreatedate().toString();
            s+="\n";
            s+="\n";
        }
        for(int i=0; i<expenselista.size(); i++){
            s += expenselista.get(i).getName();
            s += "\nspent: ";
            List<Contributors> lst = db.get_Expense_Contributors(GroupId, expenselista.get(i).getId());
            double val = expenselista.get(i).getAmount()/lst.size();
            s += String.format("%.2f",val);
            s += "\nDate: ";
            s += expenselista.get(i).getCreatedate().toString();
            s += "\n";
            s+="\n";
        }
        name.setText(contributors.getName());
        note.setText(contributors.getNote());
        date.setText(contributors.getCreatedate().toString());
        list.setText(s);

    }
}