package com.example.bartsprengelmeijer.trackyourprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProgramActivity extends AppCompatActivity {

    EditText editName, editWeeks;
    MyDBHandler db;
    Button createProgram;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);
        db = new MyDBHandler(this);

        editName = (EditText) findViewById(R.id.editText_programeName);
        editWeeks = (EditText) findViewById(R.id.editText_weeks);
        createProgram = (Button) findViewById(R.id.button_createProgram);
    }

    public void createProgram(View view) {
        String programName = editName.getText().toString();
        String numberOfWeeksInput = editWeeks.getText().toString();
        int numberOfWeeks = 1;

        if(!numberOfWeeksInput.matches("")) {
            numberOfWeeks = Integer.parseInt(numberOfWeeksInput);
        }

        long id = db.createProgram(programName, numberOfWeeks);

        if(id != -1) {
            Intent selectWeekIntent = new Intent(this, SelectWeekActivity.class);
            selectWeekIntent.putExtra("id", id);
            startActivity(selectWeekIntent);
        } else {
            Toast.makeText(CreateProgramActivity.this, "Could not create program", Toast.LENGTH_LONG).show();
        }
    }

//    public void getNumberOfWeeks(View view) {
//        String oke = db.getNumberOfWeeks(4);
//        Toast.makeText(CreateProgramActivity.this, oke, Toast.LENGTH_LONG).show();
//    }
}
