package com.expensexpert.expensexpert;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.expensexpert.expensexpert.models.Contributors;
import com.expensexpert.expensexpert.models.DatabaseHelper;

public class AddMember extends AppCompatActivity {

    public EditText input_name, input_note;
    int GroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        input_name = findViewById(R.id.new_member_name);
        input_note = findViewById(R.id.new_member_note);

        Intent getgroupid = getIntent();
        GroupId = getgroupid.getIntExtra("GroupId", 1);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMember(View view) {
        String name = input_name.getText().toString();
        String balance = input_note.getText().toString();

        if(name.length()==0){
            Toast.makeText(this, "Add Name", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseHelper db = new DatabaseHelper(this);
        Contributors contributor = new Contributors(GroupId, name, balance);

        boolean success = db.add_Contributor(contributor);
        if(success){
            input_name.setText("");
            input_note.setText("");
            Toast.makeText(this, "Member Added", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }
}