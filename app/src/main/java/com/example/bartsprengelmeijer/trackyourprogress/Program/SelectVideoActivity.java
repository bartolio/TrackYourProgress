package com.example.bartsprengelmeijer.trackyourprogress.Program;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bartsprengelmeijer.trackyourprogress.R;
import com.example.bartsprengelmeijer.trackyourprogress.Video.PlayVideoActivity;
import com.example.bartsprengelmeijer.trackyourprogress.Video.VideoFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectVideoActivity extends AppCompatActivity {

    ArrayList<String> fileList;
    ListView list;
    File root;
    List<VideoFile> videoList;
    ArrayAdapter<VideoFile> adapter;
    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);

        fileList = new ArrayList<String>();
        list = (ListView) findViewById(R.id.listView_videoFiles);

        videoList = new ArrayList<VideoFile>();
        adapter = new CustomAdapter();

        String state = Environment.getExternalStorageState();

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.i(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());

        ArrayList<String> videoFiles = getVideoFiles(root);

        //list.setAdapter(new ArrayAdapter<String>(this, R.layout.basic_list_item, videoFiles));

        //videoFiles = getVideoFiles(root);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.basic_list_item, videoFiles);

        list.setAdapter(adapter);
    }


    // Custom ArrayAdapter to for holding extra information regarding the program
    private class CustomAdapter extends ArrayAdapter<VideoFile> {
        public CustomAdapter() {
            super(SelectVideoActivity.this, R.layout.video_clip_list_item, videoList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Make sure we have a view to work with
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.video_clip_list_item, parent, false);
            }

            // Find the program to work with
            VideoFile currentFile = videoList.get(position);

            // Get the name from the object and set it to the view
            TextView fileName = (TextView) itemView.findViewById(R.id.textView_videoName);
            fileName.setText(currentFile.getFilename());

            //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(currentFile.getFilepath(), MediaStore.Images.Thumbnails.MINI_KIND);


            return itemView;
        }
    }

    public ArrayList<String> getVideoFiles(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getVideoFiles(listFile[i]);

                } else {
                    if (listFile[i].getName().endsWith(".mp4") || listFile[i].getName().endsWith(".3gp")) {
                        videoList.add(new VideoFile(listFile[i].getName(), listFile[i].getAbsolutePath(), null));
                    }
                }

            }
        }
        return fileList;
    }



    // Start a the create workout activity
    public void openListItem(View view) {
        // Listpostion
        int position = list.getPositionForView(view);

        VideoFile vid = adapter.getItem(position);

        String filepath = vid.getFilepath();

        String filename = list.getItemAtPosition(position).toString();

        Intent i  = new Intent(this, PlayVideoActivity.class);
        i.putExtra("filepath", filepath);
        startActivity(i);
    }


}
