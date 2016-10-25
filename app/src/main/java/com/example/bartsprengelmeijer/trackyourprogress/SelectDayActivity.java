package com.example.bartsprengelmeijer.trackyourprogress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectDayActivity extends AppCompatActivity {

    MyDBHandler db;
    int id;
    ListView weekList;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MyDBHandler(this);
        id = (int) getIntent().getLongExtra("id", 0);
        weekList = (ListView) findViewById(R.id.listView_weeks);
        populateListView();
    }

    private void populateListView() {
        // Get the number of weeks for the selected program
        int numberOfWeeks = db.getNumberOfWeeks(id);

        // Create array for the list items
        String[] weekItems = new String[numberOfWeeks];

        // Fill the array
        for(int i = 1; i <= numberOfWeeks; i++) {
            weekItems[i] = "Week " + i;
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for the activity
                R.layout.weeklist_item,     // Layout to use (create)
                weekItems);                 // Items to be displayed

        // Configure the list view
        ListView list = (ListView) findViewById(R.id.listView_weeks);
        list.setAdapter(adapter);
    }

}
