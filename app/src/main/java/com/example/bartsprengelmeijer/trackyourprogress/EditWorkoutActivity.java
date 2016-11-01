package com.example.bartsprengelmeijer.trackyourprogress;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity {

    int dayNumber, weekNumber, programId;
    List<Exercise> exercises;
    ArrayAdapter<Exercise> adapter;
    ListView list;
    private static final String TAG = "logtag";
    MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        db = new MyDBHandler(this);
        exercises = new ArrayList<Exercise>();
        adapter = new CustomAdapter();
        list = (ListView) findViewById(R.id.listView_editWorkout);

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
            super(EditWorkoutActivity.this, R.layout.edit_workout_list_item, exercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Make sure we have a view to work with
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.edit_workout_list_item, parent, false);
            }

            // Find the exercise to work with
            Exercise currentExercise = exercises.get(position);

            // Set the exercise view
            EditText editExercise = (EditText) itemView.findViewById(R.id.editText_editExercise);
            editExercise.setText(currentExercise.getExercise());

            // Set the sets view
            EditText editSets = (EditText) itemView.findViewById(R.id.editText_editSets);
            editSets.setText(String.valueOf(currentExercise.getSets()));

            // Set the reps view
            EditText editReps = (EditText) itemView.findViewById(R.id.editText_editReps);
            editReps.setText(String.valueOf(currentExercise.getReps()));

            // Set the weight view
            EditText editWeight = (EditText) itemView.findViewById(R.id.editText_editWeight);
            editWeight.setText(String.valueOf(currentExercise.getWeight()));

            return itemView;
        }
    }
}
