package com.hikarisakamoto.myportfoliobasic.Video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.hikarisakamoto.myportfoliobasic.R;

public class VideoStarter extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_starter);

        setTitle(getResources().getString(R.string.app3));

        Button buttonStartVideo = (Button) findViewById(R.id.buttonStartVideo);
        buttonStartVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, VideoCurriculum.GOOGLE_API_KEY, VideoCurriculum.YOUTUBE_VIDEO_ID, 0, true, true);
        startActivity(intent);
    }
}
