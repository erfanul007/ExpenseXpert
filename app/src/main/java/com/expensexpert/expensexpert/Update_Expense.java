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

import com.expensexpert.expensexpert.models.Contributors;
import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.ExGroButors;
import com.expensexpert.expensexpert.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class Update_Expense extends AppCompatActivity {
    List<Integer> checkList;
    final String members = "";
    private EditText expense_title, expense_amount, expense_catagory, expense_note;
    LinearLayout checkBoxContainer;
    CheckBox checkBox;
    List<Contributors> list;
    int GroupId, ExpenseId;
    Expense expense_p;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__expense);

        checkBoxContainer = findViewById(R.id.checkbox_container_u);
        expense_title = findViewById(R.id.new_expense_title_u);
        expense_amount = findViewById(R.id.new_expense_amount_u);
        expense_catagory = findViewById(R.id.new_expense_category_u);
        expense_note = findViewById(R.id.new_expense_note_u);


        DatabaseHelper db = new DatabaseHelper(this);



        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);
        ExpenseId = getgroupid.getIntExtra("ExpenseId", 1);

        expense_p = db.get_Expense_specific(GroupId, ExpenseId);

        expense_title.setText(expense_p.getName());
        expense_amount.setText(Double.toString(expense_p.getAmount()));
        expense_catagory.setText(expense_p.getCategory());
        expense_note.setText(expense_p.getNote());

        List<Contributors> contributors = db.get_Expense_Contributors(GroupId, ExpenseId);
        Log.e("Contrib Size: ", Integer.toString(contributors.size()));



        list = db.get_Contributors(GroupId);
        checkList = new ArrayList<>();

        for (int i=0; i<list.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(list.get(i).getId());
            checkBox.setText(list.get(i).getName());
            checkBox.setTextSize(20);
            if(contributors.contains(list.get(i))){
                Log.e("Contrib Name: ", list.get(i).getName());
                checkBox.setChecked(true);
                checkList.add(list.get(i).getId());
            }
            checkBoxContainer.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int val = buttonView.getId();
                    if (isChecked) {
//                        Log.e("checked", buttonView.getText().toString());
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
    public void updateExpense(View view) {
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
        Expense expense = new Expense(expense_p.getId(), expense_p.getGroupId(), expensename, Double.parseDouble(amount), category, note, true, expense_p.getCreatedate());
        DatabaseHelper db = new DatabaseHelper(this);
        boolean success = db.update_Expense(expense);
        if(success==false){
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
            return;
        }

        success = db.delete_ExGroButors_byexpense(ExpenseId);

        for(int i=0; i<checkList.size(); i++){
            ExGroButors exGroButors = new ExGroButors(GroupId, ExpenseId, checkList.get(i));
            success = db.add_ExGroButors(exGroButors);
        }
        checkList.clear();
        expense_title.setText("");
        expense_catagory.setText("");
        expense_amount.setText("");
        expense_note.setText("");
        Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void SelectAll(View view) {
        checkBoxContainer.removeAllViews();

        checkList.clear();
        for (int i=0; i<list.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(list.get(i).getId());
            checkBox.setText(list.get(i).getName());
            checkBox.setTextSize(20);
            checkBox.setChecked(true);
            checkList.add(list.get(i).getId());
            checkBoxContainer.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int val = buttonView.getId();
                    if (isChecked) {
//                        Log.e("checked", buttonView.getText().toString());
//                        members.concat(buttonView.getText().toString()+", ");
                        checkList.add(val);
                    }
                    else{
                        checkList.remove(checkList.indexOf(val));
                    }
                }
            });
        }
        checkBoxContainer.invalidate();
    }
}