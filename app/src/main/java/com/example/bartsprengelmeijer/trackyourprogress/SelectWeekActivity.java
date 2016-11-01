package com.example.bartsprengelmeijer.trackyourprogress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectWeekActivity extends AppCompatActivity {

    MyDBHandler db;
    int programId;
    ListView weekList;
    View lastTouchedView;
    String action;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select a week");
        setSupportActionBar(toolbar);

        db = new MyDBHandler(this);
        programId = getIntent().getIntExtra("programId", 0);
        weekList = (ListView) findViewById(R.id.listView_basicList);
        action = getIntent().getStringExtra("action");

        int numberOfWeeks = db.getNumberOfWeeks(programId);

        // Create array for the list items
        String[] weekItems = new String[numberOfWeeks];

        // Fill the array
        for(int i = 0; i < numberOfWeeks; i++) {
            int x = i+1;
            Log.i(TAG, "Weeknumner: " + x);
            weekItems[i] = "Week " + x;
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for the activity
                R.layout.basic_list_item,     // Layout to use (create)
                weekItems);                 // Items to be displayed

        // Configure the list view
        weekList.setAdapter(adapter);
    }


    // Start a new activity and send the weeknumber with it
    public void openListItem(View view) {

        ListView lv = weekList;

        // Increment with 1 because the list starts at 0 and the week at 1
        int weekNumber = lv.getPositionForView(view) + 1;

        // Highlight/show the the selected list item
        if(lastTouchedView != null) {
            lastTouchedView.setBackgroundColor(Color.parseColor("#494949"));
        }
        view.setBackgroundColor(Color.parseColor("#828282"));
        lastTouchedView = view;

        // Log the intents extra data
        Log.i(TAG, "INTENT EXTRA");
        Log.i(TAG, "Program ID: " + programId);
        Log.i(TAG, "Weeknumber: " + weekNumber);
        Log.i(TAG, "Action: " + action);

        // Start new intent and add extra data
        Intent i = new Intent(this, SelectDayActivity.class);
        i.putExtra("programId", programId);
        i.putExtra("weekNumber", weekNumber);
        i.putExtra("action", action);
        startActivity(i);
    }
}
