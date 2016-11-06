package com.example.bartsprengelmeijer.trackyourprogress.Video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import android.widget.MediaController;

import com.example.bartsprengelmeijer.trackyourprogress.R;

import java.io.File;

public class PlayVideoActivity extends AppCompatActivity {

    VideoView videoView;
    String filename;
    Uri video;
    MediaController mediaController;
    public static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        videoView = (VideoView) findViewById(R.id.videoView1);
        filename = getIntent().getStringExtra("filepath");
        File f = new File(filename);
        video = Uri.fromFile(f);

        Log.v(TAG, "Filepath: " + filename);

        // Set the position of the mediaplayer after the video dimensions are known
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Add media controller
                        mediaController = new MediaController(PlayVideoActivity.this);
                        videoView.setMediaController(mediaController);

                        // Set position on screen
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        // Add the video URI and start playing the video
        videoView.setVideoURI(video);
        videoView.start();
    }
}
