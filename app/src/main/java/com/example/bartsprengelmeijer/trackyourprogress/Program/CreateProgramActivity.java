package com.example.bartsprengelmeijer.trackyourprogress.Program;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bartsprengelmeijer.trackyourprogress.Main.MyDBHandler;
import com.example.bartsprengelmeijer.trackyourprogress.R;

public class CreateProgramActivity extends AppCompatActivity {

    EditText editName, editWeeks;
    MyDBHandler db;
    String action;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);
        db = new MyDBHandler(this);
        action = getIntent().getStringExtra("action");
        editName = (EditText) findViewById(R.id.editText_programeName);
        editWeeks = (EditText) findViewById(R.id.editText_weeks);
    }

    public void createProgram(View view) {
        String programName = editName.getText().toString();
        String numberOfWeeksInput = editWeeks.getText().toString();

        // Check if the user has entered a value;
        if(!numberOfWeeksInput.matches("") && !programName.matches("")) {
            int numberOfWeeks = Integer.parseInt(numberOfWeeksInput);

            long id = db.insertProgram(programName, numberOfWeeks);

            // Cast id to int
            int programId = (int) id;

            // Check if the db insert was successfull
            if(id != -1) {
                Intent i = new Intent(this, SelectWeekActivity.class);
                i.putExtra("programId", programId);
                i.putExtra("action", action);
                startActivity(i);
            } else {
                Toast.makeText(CreateProgramActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CreateProgramActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
