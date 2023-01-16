package com.elorrieta.didaktikapp.utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundPlayer {

    private final Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    ImageButton playPauseButton;
    SeekBar progressBar;

    public SoundPlayer(byte[] audio, ImageButton playPauseButton, SeekBar progressBar) {
        this.mediaPlayer = createMediaPlayer(audio);
        init(playPauseButton, progressBar);
    }

    public SoundPlayer(Context context, int resid, ImageButton playPauseButton, SeekBar progressBar) {
        this.mediaPlayer = MediaPlayer.create(context, resid);
        init(playPauseButton, progressBar);
    }

    private void init(ImageButton playPauseButton, SeekBar progressBar){
        this.playPauseButton = playPauseButton;
        this.progressBar = progressBar;
        progressBar.setMax(mediaPlayer.getDuration());
        playPauseButton.setContentDescription("play");
        playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        // funcion del boton de Play/Pause
        playPauseButton.setOnClickListener(createOnClickListener(playPauseButton, mediaPlayer, progressBar));
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
    }

    private MediaPlayer createMediaPlayer(byte[] binary) {
        MediaPlayer player = null;
        try {
            File file = File.createTempFile("temp", "m4a");
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

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

}
