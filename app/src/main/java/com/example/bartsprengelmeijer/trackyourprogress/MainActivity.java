package com.example.bartsprengelmeijer.trackyourprogress;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    MyDBHandler db;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MyDBHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Start a new intent for to view the existing programs.
    public void viewProgram(View view) {
        Intent i = new Intent(this, SelectProgramActivity.class);
        i.putExtra("action", "view");
        startActivity(i);
    }


    // Start a new intent for creating a new program.
    public void createProgram(View view) {
        Intent i = new Intent(this, CreateProgramActivity.class);
        i.putExtra("action", "create");
        startActivity(i);
    }

    // Start a new intent to edit existing programs.
    public void editProgram(View view) {
        Intent i = new Intent(this, SelectProgramActivity.class);
        i.putExtra("action", "edit");
        startActivity(i);
    }

    public static void printNamesToLogCat(Context context) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Video.VideoColumns.DATA };
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
        int vidsCount = 0;
        if (c != null) {
            vidsCount = c.getCount();
            while (c.moveToNext()) {
                Log.d(TAG, c.getString(0));
            }
            c.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
