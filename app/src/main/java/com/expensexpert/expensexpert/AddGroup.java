package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.Group;

public class AddGroup extends AppCompatActivity {

    EditText et_name;
    EditText et_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        et_name = findViewById(R.id.new_group_name);
        et_note = findViewById(R.id.new_group_note);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addGroup(View view) {
        String name = et_name.getText().toString();
        String note = et_note.getText().toString();
        if(name.length()==0){
            Toast.makeText(AddGroup.this, "Name Required", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseHelper db = new DatabaseHelper(AddGroup.this);
            Group group = new Group(name, note);
            boolean success = db.add_Group(group);
            if (success) {
                et_name.setText("");
                et_note.setText("");
                Toast.makeText(AddGroup.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddGroup.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }
}