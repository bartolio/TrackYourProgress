package com.example.bartsprengelmeijer.trackyourprogress.Program;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bartsprengelmeijer.trackyourprogress.Main.Program;
import com.example.bartsprengelmeijer.trackyourprogress.R;

public class SelectDayActivity extends AppCompatActivity {

    int weekNumber;
    int programId;
    String action;
    ListView daysList;
    View lastTouchedView;
    //private static final String TAG = SelectDayActivity.class.getName();
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);

        // Get the weeknumber and programid from the intent extras
        weekNumber = getIntent().getIntExtra("weekNumber", 0);
        programId = getIntent().getIntExtra("programId", 0);
        action = getIntent().getStringExtra("action");
        Log.i(TAG, "Weeknumber: " + weekNumber);

        // Get the list of days
        daysList = (ListView) findViewById(R.id.listView_basicList);

        // Set toolbar and title with the selected week
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Week " + weekNumber);
        setSupportActionBar(toolbar);

        // Array with weekdays
        String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.basic_list_item, daysOfTheWeek);

        // Configure the list view
        daysList.setAdapter(adapter);
    }

    // Start a the create workout activity
    public void openListItem(View view) {
        ListView lv = daysList;

        // Listpostion
        int position = lv.getPositionForView(view);

        // Daynumber + 1 because the list starts at 0
        int dayNumber = lv.getPositionForView(view) + 1;

        // Get the text from the list item to set as title for the next activity
        String dayString = lv.getItemAtPosition(position).toString();

        // Logging to see if the values are set
        Log.i(TAG, "Daynumber: " + dayNumber);
        Log.i(TAG, "Weeknumber: " + weekNumber);

        // Highlight/show the the selected list item
        if(lastTouchedView != null) {
            lastTouchedView.setBackgroundColor(Color.parseColor("#494949"));
        }
        view.setBackgroundColor(Color.parseColor("#828282"));
        lastTouchedView = view;

        Log.i(TAG, action);

        // Check which activity is requested and start a new intent
        switch(action){
            case "view":
                startIntent(ViewWorkoutActivity.class, dayNumber, dayString, programId, weekNumber);
                break;
            case "edit":
                startIntent(EditWorkoutActivity.class, dayNumber, dayString, programId, weekNumber);
                break;
            case "create":
                startIntent(CreateWorkoutActivity.class, dayNumber, dayString, programId, weekNumber);
                break;
            default:
                Toast.makeText(SelectDayActivity.this, "An error has occurred.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Start intent
    public void startIntent(Class c, int dayNumber, String day, int programId, int weekNumber){
        Intent i = new Intent(this, c);
        i.putExtra("dayNumber", dayNumber);
        i.putExtra("dayString", day);
        i.putExtra("programId", programId);
        i.putExtra("weekNumber", weekNumber);
        startActivity(i);
    }
}
