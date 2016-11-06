package com.example.bartsprengelmeijer.trackyourprogress.Main;

import android.support.v7.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bartsprengelmeijer.trackyourprogress.Program.CreateProgramActivity;
import com.example.bartsprengelmeijer.trackyourprogress.Program.SelectProgramActivity;
import com.example.bartsprengelmeijer.trackyourprogress.Program.SelectVideoActivity;
import com.example.bartsprengelmeijer.trackyourprogress.R;
import com.example.bartsprengelmeijer.trackyourprogress.Video.RecordVideoActivity;

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

        // Request permission on runtime
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO },
                1);
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

    // Start a new intent to edit existing programs.
    public void showVideoFiles (View view) {
        Intent i = new Intent(this, SelectVideoActivity.class);
        startActivity(i);
    }

    // Start a new intent to edit existing programs.
    public void recordVideo (View view) {
        Intent i = new Intent(this, RecordVideoActivity.class);
        startActivity(i);
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
