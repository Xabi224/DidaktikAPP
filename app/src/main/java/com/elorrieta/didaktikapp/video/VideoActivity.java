package com.elorrieta.didaktikapp.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.map.MapsActivity;
import com.elorrieta.didaktikapp.model.database.AppDatabase;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView videoView =(VideoView)findViewById(R.id.vdVw);
        //Set MediaController  to enable play, pause, forward, etc options.
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Location of Media File
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        //Starting VideView By Setting MediaController and URI
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        Button btn = findViewById(R.id.btnBack);
        btn.setOnClickListener(v -> endActivity());
    }

    private void endActivity() {
        int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(this.getClass().getName());
        AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
        finish();
    }
}