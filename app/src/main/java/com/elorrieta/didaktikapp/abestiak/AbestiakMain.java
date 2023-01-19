package com.elorrieta.didaktikapp.abestiak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityAbestiakMainBinding;
import com.elorrieta.didaktikapp.databinding.ActivityDescriptionBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.Song;
import com.elorrieta.didaktikapp.utilities.SoundPlayer;

public class AbestiakMain extends AppCompatActivity {
    SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAbestiakMainBinding binding = ActivityAbestiakMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_abestiak_main);

        Song song = AppDatabase.getDatabase(getApplicationContext()).songDao().findById(1);
        soundPlayer = new SoundPlayer(song.shortAudio, binding.playPauseButton, binding.seekBar);

    }


    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.pause();
    }
}