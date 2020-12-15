package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddExpense extends AppCompatActivity {
    List<Integer> checkList;
    final String members = "";
    private EditText expense_title, expense_amount, expense_catagory, expense_note;
    LinearLayout checkBoxContainer;
    CheckBox checkBox;

    int GroupId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        checkBoxContainer = findViewById(R.id.checkbox_container);
        expense_title = findViewById(R.id.new_expense_title);
        expense_amount = findViewById(R.id.new_expense_amount);
        expense_catagory = findViewById(R.id.new_expense_category);
        expense_note = findViewById(R.id.new_expense_note);



        DatabaseHelper db = new DatabaseHelper(this);

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);

        List<Contributors> list = db.get_Contributors(GroupId);
        checkList = new ArrayList<>();

        for (int i=0; i<list.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(list.get(i).getId());
            checkBox.setText(list.get(i).getName());
            checkBoxContainer.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int val = buttonView.getId();
                    if (isChecked) {
                        Log.e("checked", buttonView.getText().toString());
//                        members.concat(buttonView.getText().toString()+", ");

                        checkList.add(val);

                    }
                    else{
                        checkList.remove(checkList.indexOf(val));
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addExpense(View view) {
        if(checkList.size()==0){
            Toast.makeText(this, "Add at least one member", Toast.LENGTH_SHORT).show();
            return;
        }
        String expensename = expense_title.getText().toString();
        String amount = expense_amount.getText().toString();
        String category = expense_catagory.getText().toString();
        String note = expense_note.getText().toString();

        if(expensename.length()==0 || amount.length()==0){
            Toast.makeText(this, "Add Title and Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        Expense expense = new Expense(GroupId, expensename, Double.parseDouble(amount), category, note, true);
        DatabaseHelper db = new DatabaseHelper(this);
        boolean success = db.add_Expense(expense);
        if(success==false){
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
            return;
        }

        int expenseid = db.getlastExpenseId(GroupId);

        for(int i=0; i<checkList.size(); i++){
            ExGroButors exGroButors = new ExGroButors(GroupId, expenseid, checkList.get(i));
            success = db.add_ExGroButors(exGroButors);
        }
        checkList.clear();
        expense_title.setText("");
        expense_catagory.setText("");
        expense_amount.setText("");
        expense_note.setText("");
        Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SingleTour.class);
        intent.putExtra("GroupId", GroupId);
        startActivity(intent);
    }
}