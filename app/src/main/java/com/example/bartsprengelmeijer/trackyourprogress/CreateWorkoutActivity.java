package com.example.bartsprengelmeijer.trackyourprogress;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutActivity extends AppCompatActivity {

    EditText exerciseTxtField, setsTxtField, repsTxtField, weightTxtField;
    int programId, weekNumber, dayNumber;
    List<Exercise> exercises;
    ArrayAdapter<Exercise> adapter;
    ListView list;
    private static final String TAG = "logtag";
    MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_workout);

        // Get intent extras
        String day = getIntent().getStringExtra("dayString");
        dayNumber = getIntent().getIntExtra("dayNumber", 0);
        weekNumber = getIntent().getIntExtra("weekNumber", 0);
        programId = getIntent().getIntExtra("programId", 0);

        // Log the data from the intent
        Log.i(TAG, "Day: " + day);
        Log.i(TAG, "Daynumber: " + dayNumber);
        Log.i(TAG, "Weeknumber: " + weekNumber);
        Log.i(TAG, "ProgramId: " + programId);

        // Initialize variables
        exerciseTxtField = (EditText) findViewById(R.id.editText_exerciseInputField);
        setsTxtField = (EditText) findViewById(R.id.editText_setsInputField);
        repsTxtField = (EditText) findViewById(R.id.editText_repsInputField);
        weightTxtField = (EditText) findViewById(R.id.editText_weightInputField);

        db = new MyDBHandler(this);
        exercises = new ArrayList<Exercise>();
        adapter = new CustomAdapter();
        list = (ListView) findViewById(R.id.listView_workoutList);

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

    public void addExerciseToWorkout(View view){
        String exercise = exerciseTxtField.getText().toString();
        String a = setsTxtField.getText().toString();
        String b = repsTxtField.getText().toString();
        String c = weightTxtField.getText().toString();

        if(!exercise.matches("") && !a.matches("") && !b.matches("") && !c.matches("")){
            int sets = Integer.parseInt(a);
            int reps = Integer.parseInt(b);
            int weight = Integer.parseInt(c);

            Log.i(TAG, "ADD EXERCISE DATA");
            Log.i(TAG, "Exercise: " + weight);
            Log.i(TAG, "Sets: " + sets);
            Log.i(TAG, "Reps: " + reps);
            Log.i(TAG, "Weight: " + weight);

            // Insert workout
            long id = db.insertWorkout(
                    (int) db.insertProgramDetails(programId, weekNumber, dayNumber),
                    (int) db.insertExercise(exercise),
                    sets, reps, weight
            );

            // Check if the insert was successfull
            if(id != -1) {
                Toast.makeText(CreateWorkoutActivity.this, "Exercise was added to workout", Toast.LENGTH_SHORT).show();

                // Add exercise to the workout list and update the view
                exercises.add(new Exercise(exercise, sets, reps, weight));
                adapter.notifyDataSetChanged();

                // Clear all text input and set focus back to first input
                exerciseTxtField.getText().clear();
                setsTxtField.getText().clear();
                repsTxtField.getText().clear();
                weightTxtField.getText().clear();
                exerciseTxtField.requestFocus();

            } else {
                Toast.makeText(CreateWorkoutActivity.this, "Could not insert exercise", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CreateWorkoutActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Custom ArrayAdapter to update the listview for the workout details
    private class CustomAdapter extends ArrayAdapter<Exercise> {
        public CustomAdapter() {
            super(CreateWorkoutActivity.this, R.layout.workout_list_item, exercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Make sure we have a view to work with
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.workout_list_item, parent, false);
            }

            // Find the exercise to work with
            Exercise currentExercise = exercises.get(position);

            // Set the exercise view
            TextView exerciseView = (TextView) itemView.findViewById(R.id.workoutItemExercise);
            exerciseView.setText(currentExercise.getExercise());

            // Set the sets view
            TextView setsView = (TextView) itemView.findViewById(R.id.workoutItemSets);
            setsView.setText(String.valueOf(currentExercise.getSets()));

            // Set the reps view
            TextView repsView = (TextView) itemView.findViewById(R.id.workoutItemReps);
            repsView.setText(String.valueOf(currentExercise.getReps()));

            // Set the weight view
            TextView weightView = (TextView) itemView.findViewById(R.id.workoutItemWeight);
            weightView.setText(String.valueOf(currentExercise.getWeight()));

            return itemView;
        }
    }


    // Function for removing an exercise from the workout list
    public void removeExerciseFromWorkout (View view) {
        // Get the list position of the clicked item
        int position = list.getPositionForView(view);

        // Remove item and update list
        exercises.remove(position);
        adapter.notifyDataSetChanged();

        //TODO delete from DB
    }
}
