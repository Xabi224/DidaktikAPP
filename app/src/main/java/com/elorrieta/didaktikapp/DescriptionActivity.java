package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.elorrieta.didaktikapp.databinding.ActivityDescriptionBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.Game;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescriptionActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar progressBar;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDescriptionBinding binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PlaceOfInterest poi = (PlaceOfInterest) getIntent().getSerializableExtra("poi");
        Game game = AppDatabase.getDatabase(getApplicationContext()).gameDao().findById(poi.idPoI);

        if (game.description == null) {
            Class<?> activityClass = null;
            try {
                activityClass = Class.forName(game.gameClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
            return;
        }

        TextView description = binding.tvDescription;
        description.setText(game.description);

        try {
            File file = File.createTempFile("temp", "m4a", getCacheDir());
            file.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(game.audio);
            fos.close();
            mediaPlayer = new MediaPlayer();
            FileInputStream fis = new FileInputStream(file);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progressBar = binding.seekBar;
        progressBar.setMax(mediaPlayer.getDuration());

        ImageButton playPauseButton = binding.playPauseButton;
        playPauseButton.setContentDescription("play");
        playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        // funcion del boton de Play/Pause
        playPauseButton.setOnClickListener(view -> {
            if (playPauseButton.getContentDescription().equals("play")) {
                playPauseButton.setContentDescription("pause");
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                mediaPlayer.start();
                handler.postDelayed(UpdateSongTime, 100);
            } else {
                playPauseButton.setContentDescription("play");
                playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                mediaPlayer.pause();
            }
        });

        // funcion de la barra de progreso
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    progressBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button button = binding.button;
        // funcion del boton de iniciar el juego
        button.setOnClickListener(view -> {
            Class<?> activityClass = null;
            try {
                activityClass = Class.forName(game.gameClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        });
    }

    private final Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            progressBar.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.isPlaying()) {
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}