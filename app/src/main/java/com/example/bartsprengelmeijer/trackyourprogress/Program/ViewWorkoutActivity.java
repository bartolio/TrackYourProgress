package com.example.bartsprengelmeijer.trackyourprogress.Program;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bartsprengelmeijer.trackyourprogress.Main.Exercise;
import com.example.bartsprengelmeijer.trackyourprogress.Main.MyDBHandler;
import com.example.bartsprengelmeijer.trackyourprogress.R;

import java.util.ArrayList;
import java.util.List;

public class ViewWorkoutActivity extends AppCompatActivity {

    int dayNumber, weekNumber, programId;
    List<Exercise> exercises;
    ArrayAdapter<Exercise> adapter;
    ListView list;
    private static final String TAG = "logtag";
    MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_overview);

        db = new MyDBHandler(this);
        exercises = new ArrayList<Exercise>();
        adapter = new CustomAdapter();
        list = (ListView) findViewById(R.id.listView_displayWorkout);

        // Get intent extras
        String day = getIntent().getStringExtra("dayString");
        dayNumber = getIntent().getIntExtra("dayNumber", 0);
        weekNumber = getIntent().getIntExtra("weekNumber", 0);
        programId = getIntent().getIntExtra("programId", 0);

        // Check the DB to see of there is a workout for the selected week/day
        Cursor cursor = db.getWorkout(programId, weekNumber, dayNumber);

        // If there is a result loop through it and add the results to the list
        if(cursor.moveToFirst()){
            do {
                // Get data from database
                String exerciseDb = cursor.getString(cursor.getColumnIndex("exercise_name"));
                int setsDb = cursor.getInt(cursor.getColumnIndex("sets"));
                int repsDb = cursor.getInt(cursor.getColumnIndex("reps"));
                int weightDb = cursor.getInt(cursor.getColumnIndex("weight"));

                // Add data to the list
                exercises.add(new Exercise(exerciseDb, setsDb, repsDb, weightDb));
            } while (cursor.moveToNext());

            cursor.close();
        }

        list.setAdapter(adapter);

    }

    // Custom ArrayAdapter to update the listview for the workout details
    private class CustomAdapter extends ArrayAdapter<Exercise> {
        public CustomAdapter() {
            super(ViewWorkoutActivity.this, R.layout.view_workout_list_item, exercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Make sure we have a view to work with
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.view_workout_list_item, parent, false);
            }

            // Find the exercise to work with
            Exercise currentExercise = exercises.get(position);

            // Set the exercise view
            TextView displayExercise = (TextView) itemView.findViewById(R.id.textView_displayExercise);
            displayExercise.setText(currentExercise.getExercise());

            // Set the sets view
            TextView displaySets = (TextView) itemView.findViewById(R.id.textView_displaySets);
            displaySets.setText(String.valueOf(currentExercise.getSets()));

            // Set the reps view
            TextView displayReps = (TextView) itemView.findViewById(R.id.textView_displayReps);
            displayReps.setText(String.valueOf(currentExercise.getReps()));

            // Set the weight view
            TextView displayWeight = (TextView) itemView.findViewById(R.id.textView_displayWeight);
            displayWeight.setText(String.valueOf(currentExercise.getWeight()));

            return itemView;
        }
    }
}
