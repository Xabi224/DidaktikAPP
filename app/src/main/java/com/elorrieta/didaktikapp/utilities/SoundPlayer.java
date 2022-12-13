package com.elorrieta.didaktikapp.utilities;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundPlayer {

    MediaPlayer mediaPlayer;
    ImageButton playPauseButton;
    SeekBar progressBar;

    public SoundPlayer(byte[] audio, ImageButton playPauseButton, SeekBar progressBar) {
        this.mediaPlayer = createMediaPlayer(audio);
        this.playPauseButton = playPauseButton;
        this.progressBar = progressBar;
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

}
