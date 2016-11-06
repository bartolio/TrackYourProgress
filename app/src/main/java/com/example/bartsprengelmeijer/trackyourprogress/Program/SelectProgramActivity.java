package com.example.bartsprengelmeijer.trackyourprogress.Program;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bartsprengelmeijer.trackyourprogress.Main.MyDBHandler;
import com.example.bartsprengelmeijer.trackyourprogress.Main.Program;
import com.example.bartsprengelmeijer.trackyourprogress.R;

import java.util.ArrayList;
import java.util.List;

public class SelectProgramActivity extends AppCompatActivity {

    MyDBHandler db;
    List<Program> programList;
    ArrayAdapter<Program> adapter;
    ListView list;
    View lastTouchedView;
    String action;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_program);

        db = new MyDBHandler(this);
        action = getIntent().getStringExtra("action");
        programList = new ArrayList<Program>();
        adapter = new CustomAdapter();

        // Configure the list view
        list = (ListView) findViewById(R.id.listView_basicList);



        // Check the DB to see of there is a workout for the selected week/day
        Cursor cursor = db.getPrograms();

        // If there is a result loop through it and add the results to the list
        if(cursor.moveToFirst()){
            do {
                // Get data from database
                int programIdDb = cursor.getInt(cursor.getColumnIndex("id"));
                String programNameDb = cursor.getString(cursor.getColumnIndex("program_name"));
                int numberOfWeeksDb = cursor.getInt(cursor.getColumnIndex("number_of_weeks"));

                programList.add(new Program(programIdDb, programNameDb, numberOfWeeksDb));
            } while (cursor.moveToNext());

            cursor.close();
        }

        list.setAdapter(adapter);
    }

    // Custom ArrayAdapter to for holding extra information regarding the program
    private class CustomAdapter extends ArrayAdapter<Program> {
        public CustomAdapter() {
            super(SelectProgramActivity.this, R.layout.basic_list_item, programList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Make sure we have a view to work with
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.basic_list_item, parent, false);
            }

            // Find the program to work with
            Program currentProgram = programList.get(position);

            // Get the name from the object and set it to the view
            TextView basicTxt = (TextView) itemView.findViewById(R.id.textView_basicText);
            basicTxt.setText(currentProgram.getName());

            return itemView;
        }
    }

    // Start a the create workout activity
    public void openListItem(View view) {

        // Get the list position
        int position = list.getPositionForView(view);

        // Get the list object
        Program program = adapter.getItem(position);

        int programId = program.getId();

        // Highlight/show the the selected list item
        if(lastTouchedView != null) {
            lastTouchedView.setBackgroundColor(Color.parseColor("#494949"));
        }
        view.setBackgroundColor(Color.parseColor("#828282"));
        lastTouchedView = view;

        // Log the intents extra data
        Log.i(TAG, "INTENT EXTRA");
        Log.i(TAG, "Program ID: " + programId);
        Log.i(TAG, "Action: " + action);

        // Start new intent and add extra data
        Intent i = new Intent(this, SelectWeekActivity.class);
        i.putExtra("programId", programId);
        i.putExtra("action", action);
        startActivity(i);
    }
}
