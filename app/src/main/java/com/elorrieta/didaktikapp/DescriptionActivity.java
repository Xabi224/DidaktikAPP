package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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
    private MediaPlayer mediaPlayerExtra;
    private SeekBar progressBarExtra;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDescriptionBinding binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PlaceOfInterest poi = (PlaceOfInterest) getIntent().getSerializableExtra("poi");
        Game game = AppDatabase.getDatabase(getApplicationContext()).gameDao().findById(poi.idPoI);

        if (game.description == null) {
            changeActivity(game.gameClass);
        }

        TextView description = binding.tvDescription;
        description.setText(game.description);

        mediaPlayer = createMediaPlayer(game.audio);

        progressBar = binding.seekBar;
        progressBar.setMax(mediaPlayer.getDuration());

        ImageButton playPauseButton = binding.playPauseButton;
        playPauseButton.setContentDescription("play");
        playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        // funcion del boton de Play/Pause
        playPauseButton.setOnClickListener(createOnClickListener(playPauseButton, mediaPlayer, progressBar));

        // funcion de la barra de progreso
        progressBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
            changeActivity(game.gameClass);
        });
    }

//    private final Runnable UpdateSongTime = new Runnable() {
//        @Override
//        public void run() {
//            progressBar.setProgress(mediaPlayer.getCurrentPosition());
//            if (mediaPlayer.isPlaying()) {
//                handler.postDelayed(this, 100);
//            }
//        }
//    };
//
//    private final Runnable UpdateSongTimeExtra = new Runnable() {
//        @Override
//        public void run() {
//            progressBarExtra.setProgress(mediaPlayer.getCurrentPosition());
//            if (mediaPlayerExtra.isPlaying()) {
//                handler.postDelayed(this, 100);
//            }
//        }
//    };

    private View.OnClickListener createOnClickListener(ImageButton button, MediaPlayer player, SeekBar bar) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getContentDescription().equals("play")) {
                    button.setContentDescription("pause");
                    button.setImageResource(android.R.drawable.ic_media_pause);
                    player.start();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.setProgress(player.getCurrentPosition());
                            if (player.isPlaying()) {
                                handler.postDelayed(this, 100);
                            }
                        }
                    }, 100);
                } else {
                    button.setContentDescription("play");
                    button.setImageResource(android.R.drawable.ic_media_play);
                    player.pause();
                }
            }
        };
    }

    private MediaPlayer createMediaPlayer(byte[] binary) {
        MediaPlayer player = null;
        try {
            File file = File.createTempFile("temp", "m4a", getCacheDir());
            file.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(binary);
            fos.close();
            player = new MediaPlayer();
            FileInputStream fis = new FileInputStream(file);
            player.setDataSource(fis.getFD());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    private void changeActivity(String className) {
        Class<?> activityClass = null;
        try {
            activityClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}