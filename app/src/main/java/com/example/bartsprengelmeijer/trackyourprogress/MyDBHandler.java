package com.example.bartsprengelmeijer.trackyourprogress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    // Current DB version
    private static final int DATABASE_VERSION = 3;

    // DB name
    public static final String DATABASE_NAME = "powerliftingapp.db";

    // Table Program
    public static final String TABLE_NAME_PROGRAM = "program";
    public static final String TABLE_PROGRAM_COL_ID = "id";
    public static final String TABLE_PROGRAM_COL_PROGRAM_NAME = "program_name";
    public static final String TABLE_PROGRAM_COL_NUMBER_OF_WEEKS = "number_of_weeks";

    // Table Exercise
    public static final String TABLE_NAME_EXERCISE = "exercise";
    public static final String TABLE_EXERCISE_COL_ID = "programId";
    public static final String TABLE_EXERCISE_COL_EXERCISE_NAME = "exercise_name";

    // Table Program_detail
    public static final String TABLE_NAME_PROGRAM_DETAIL = "program_detail";
    public static final String TABLE_PROGRAM_DETAIL_COL_ID = "programId";
    public static final String TABLE_PROGRAM_DETAIL_COL_PROGRAM_ID = "program_id";
    public static final String TABLE_PROGRAM_DETAIL_COL_WEEK_NUMBER = "week_number";
    public static final String TABLE_PROGRAM_DETAIL_COL_DAY_NUMBER = "day_number";

    // Table Workout
    public static final String TABLE_NAME_WORKOUT = "workout";
    public static final String TABLE_WORKOUT_COL_ID = "programId";
    public static final String TABLE_WORKOUT_COL_PROGRAM_DETAIL_ID = "program_detail_id";
    public static final String TABLE_WORKOUT_COL_EXERCISE_ID = "exercise_id";
    public static final String TABLE_WORKOUT_COL_SETS = "sets";
    public static final String TABLE_WORKOUT_COL_REPS = "reps";
    public static final String TABLE_WORKOUT_COL_WEIGHT = "weight";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query for creating table program
        String createTableProgram = "CREATE TABLE " + TABLE_NAME_PROGRAM + " ( " +
                TABLE_PROGRAM_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_PROGRAM_COL_PROGRAM_NAME + " TEXT, " +
                TABLE_PROGRAM_COL_NUMBER_OF_WEEKS + " INTEGER " +
                ");";

        // Query for creating table exercise
        String createTableExercise = "CREATE TABLE " + TABLE_NAME_EXERCISE + " ( " +
                TABLE_EXERCISE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_EXERCISE_COL_EXERCISE_NAME + " TEXT " +
                ");";

        // Query for creating table program_detail
        String createTableProgramDetail = "CREATE TABLE " + TABLE_NAME_PROGRAM_DETAIL + " ( " +
                TABLE_PROGRAM_DETAIL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_PROGRAM_DETAIL_COL_PROGRAM_ID + " INTEGER, " +
                TABLE_PROGRAM_DETAIL_COL_WEEK_NUMBER + " INTEGER, " +
                TABLE_PROGRAM_DETAIL_COL_DAY_NUMBER + " INTEGER " +
                ");";

        // Query for creating table workout
        String createTableWorkout = "CREATE TABLE " + TABLE_NAME_WORKOUT + " ( " +
                TABLE_WORKOUT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_WORKOUT_COL_PROGRAM_DETAIL_ID + " INTEGER, " +
                TABLE_WORKOUT_COL_EXERCISE_ID + " INTEGER, " +
                TABLE_WORKOUT_COL_SETS + " INTEGER, " +
                TABLE_WORKOUT_COL_REPS + " INTEGER, " +
                TABLE_WORKOUT_COL_WEIGHT + " INTEGER " +
                ");";

        // Executing the queries
        db.execSQL(createTableProgram);
        db.execSQL(createTableExercise);
        db.execSQL(createTableProgramDetail);
        db.execSQL(createTableWorkout);
    }

    // Delete the all tables and create new ones
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROGRAM + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXERCISE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROGRAM_DETAIL + ";");
        onCreate(db);
    }

    public long insertProgram (String name, int weeks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_PROGRAM_COL_PROGRAM_NAME, name);
        values.put(TABLE_PROGRAM_COL_NUMBER_OF_WEEKS, weeks);
        long id = db.insert(TABLE_NAME_PROGRAM, null, values);
        db.close();
        return id;
    }

    // Insert program details into database
    public long insertProgramDetails (int programId, int weekNumber, int dayNumber) {
        ContentValues values = new ContentValues();
        values.put(TABLE_PROGRAM_DETAIL_COL_PROGRAM_ID, programId);
        values.put(TABLE_PROGRAM_DETAIL_COL_WEEK_NUMBER, weekNumber);
        values.put(TABLE_PROGRAM_DETAIL_COL_DAY_NUMBER, dayNumber);
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_NAME_PROGRAM_DETAIL, null, values);
        db.close();
        return id;
    }

    // Insert exercise name into database
    public long insertExercise (String exercise) {
        ContentValues values = new ContentValues();
        values.put(TABLE_EXERCISE_COL_EXERCISE_NAME, exercise);
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_NAME_EXERCISE, null, values);
        db.close();
        return id;
    }

    // Insert workout into database
    public long insertWorkout (int programDetailsId, int exerciseId, int sets, int reps, int weight) {
        ContentValues values = new ContentValues();
        values.put(TABLE_WORKOUT_COL_PROGRAM_DETAIL_ID, programDetailsId);
        values.put(TABLE_WORKOUT_COL_EXERCISE_ID, exerciseId);
        values.put(TABLE_WORKOUT_COL_SETS, sets);
        values.put(TABLE_WORKOUT_COL_REPS, reps);
        values.put(TABLE_WORKOUT_COL_WEIGHT, weight);
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_NAME_WORKOUT, null, values);
        db.close();
        return id;
    }

    // Get the number of weeks for a specific program (find by programId)
    public int getNumberOfWeeks(int programId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT number_of_weeks FROM " + TABLE_NAME_PROGRAM + " WHERE id = " + programId, null);
        int numberOfWeeks = 0;

        if(cursor.moveToFirst()) {
            do {
                numberOfWeeks = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return numberOfWeeks;
    }

    // Get all exercises for a specific workout by program programId, week and day
    public Cursor getWorkout(int programId, int weekNumber, int dayNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(
                "SELECT e.exercise_name, w.sets, w.reps, w.weight " +
                        " FROM workout w " +
                        " JOIN exercise e ON w.exercise_id = e.id " +
                        " JOIN program_detail pd ON w.program_detail_id = pd.id " +
                        " WHERE pd.program_id = " + programId +
                        " AND pd.week_number = " + weekNumber +
                        " AND pd.day_number = " + dayNumber,
                null);
    }

    // Get the programs from the database
    public Cursor getPrograms() {
        SQLiteDatabase db = this.getWritableDatabase();
        //return db.query(false, TABLE_NAME_PROGRAM, new String[] {TABLE_PROGRAM_COL_ID, TABLE_PROGRAM_COL_PROGRAM_NAME}, null, null, null, null, null, null);
        return db.rawQuery("SELECT " + TABLE_PROGRAM_COL_ID + ", " + TABLE_PROGRAM_COL_PROGRAM_NAME + ", " + TABLE_PROGRAM_COL_NUMBER_OF_WEEKS + " FROM " + TABLE_NAME_PROGRAM, null);
    }
}






















