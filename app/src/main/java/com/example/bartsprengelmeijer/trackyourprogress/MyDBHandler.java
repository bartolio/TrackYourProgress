package com.example.bartsprengelmeijer.trackyourprogress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    // Current DB version
    private static final int DATABASE_VERSION = 2;

    // DB name
    public static final String DATABASE_NAME = "powerliftingapp.db";

    // Table Program
    public static final String TABLE_NAME_PROGRAM = "program";
    public static final String PROGRAM_COL_ID = "id";
    public static final String PROGRAM_COL_PROGRAM_NAME = "program_name";
    public static final String PROGRAM_COL_NUMBER_OF_WEEKS = "number_of_weeks";

    // Table Exercise
    public static final String TABLE_NAME_EXERCISE = "excercise";
    public static final String EXERCISE_COL_ID = "id";
    public static final String EXERCISE_COL_EXERCISE_NAME = "exercise_name";

    // Table Program_detail
    public static final String TABLE_NAME_PROGRAM_DETAIL = "program_detail";
    public static final String PROGRAM_DETAIL_COL_ID = "id";
    public static final String PROGRAM_DETAIL_COL_PROGRAM_ID = "program_id";
    public static final String PROGRAM_DETAIL_COL_WEEK_NUMBER = "week_number";
    public static final String PROGRAM_DETAIL_COL_DAY_NUMBER = "day_number";

    // Table Workout
    public static final String TABLE_NAME_WORKOUT = "workout";
    public static final String WORKOUT_COL_ID = "id";
    public static final String WORKOUT_COL_PROGRAM_DETAIL_ID = "program_detail_id";
    public static final String WORKOUT_COL_EXERCISE_ID = "exercise_id";
    public static final String WORKOUT_COL_SETS = "sets";
    public static final String WORKOUT_COL_REPS = "reps";
    public static final String WORKOUT_COL_WEIGHT = "weight";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query for creating table program
        String createTableProgram = "CREATE TABLE " + TABLE_NAME_PROGRAM + " ( " +
                PROGRAM_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROGRAM_COL_PROGRAM_NAME + " TEXT, " +
                PROGRAM_COL_NUMBER_OF_WEEKS + " INTEGER " +
                ");";

        // Query for creating table exercise
        String createTableExercise = "CREATE TABLE " + TABLE_NAME_EXERCISE + " ( " +
                EXERCISE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXERCISE_COL_EXERCISE_NAME + " TEXT " +
                ");";

        // Query for creating table program_detail
        String createTableProgramDetail = "CREATE TABLE " + TABLE_NAME_PROGRAM_DETAIL + " ( " +
                PROGRAM_DETAIL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROGRAM_DETAIL_COL_PROGRAM_ID + " INTEGER, " +
                PROGRAM_DETAIL_COL_WEEK_NUMBER + " INTEGER, " +
                PROGRAM_DETAIL_COL_DAY_NUMBER + " INTEGER " +
                ");";

        // Query for creating table workout
        String createTableWorkout = "CREATE TABLE " + TABLE_NAME_WORKOUT + " ( " +
                WORKOUT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_COL_PROGRAM_DETAIL_ID + " INTEGER, " +
                WORKOUT_COL_EXERCISE_ID + " INTEGER, " +
                WORKOUT_COL_SETS + " INTEGER, " +
                WORKOUT_COL_REPS + " INTEGER, " +
                WORKOUT_COL_WEIGHT + " INTEGER " +
                ");";

        // Executing the queries
        db.execSQL(createTableProgram);
        db.execSQL(createTableExercise);
        db.execSQL(createTableProgramDetail);
        db.execSQL(createTableWorkout);
    }

    // Delete the all tables and create new ones;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROGRAM + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXERCISE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROGRAM_DETAIL + ";");
        onCreate(db);
    }

    public long createProgram(String name, int weeks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROGRAM_COL_PROGRAM_NAME, name);
        contentValues.put(PROGRAM_COL_NUMBER_OF_WEEKS, weeks);
        return db.insert(TABLE_NAME_PROGRAM, null, contentValues);
    }

    public int getNumberOfWeeks(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT number_of_weeks FROM " + TABLE_NAME_PROGRAM + " WHERE id = " + id, null);
        int numberOfWeeks = 0;

        if(cursor.moveToFirst()) {
            do {
                numberOfWeeks = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return numberOfWeeks;
    }




//
//    // Add a new exercise name to the database
//    public void addExercise(Exercises exercise) {
//        ContentValues values = new ContentValues();
//        values.put(EXERCISE_COL_EXERCISE_NAME, exercise.get_exercisename());
//        SQLiteDatabase db = getWritableDatabase();
//        db.insert(TABLE_NAME_EXERCISE, null, values);
//        db.close();
//    }
//
//    // Add a new program name to the database
//    public void addProgramName(Program program) {
//        ContentValues values = new ContentValues();
//        values.put(TABLE_NAME_PROGRAM, program.get_program_name());
//        SQLiteDatabase db = getWritableDatabase();
//        db.insert(TABLE_NAME_PROGRAM, null, values);
//        db.close();
//    }
//
//    // Add a new row to the program_details table
//    public void addProgramDetails(ProgramDetails programDetails) {
//        ContentValues values = new ContentValues();
//        values.put(WORKOUT_COL_PROGRAM_DETAIL_ID, programDetails.get_program_detail_id());
//        values.put(WORKOUT, programDetails.get_reps());
//        values.put(PROGRAM_DETAILS_COLUMN_WEIGHT, programDetails.get_weight());
//        values.put(PROGRAM_DETAILS_COLUMN_EXERCISE_ID, programDetails.get_exercise_id());
//        values.put(PROGRAM_DETAILS_COLUMN_PROGRAM_ID, programDetails.get_program_id());
//        SQLiteDatabase db = getWritableDatabase();
//        db.insert(TABLE_PROGRAM_DETAILS, null, values);
//        db.close();
//    }
}
