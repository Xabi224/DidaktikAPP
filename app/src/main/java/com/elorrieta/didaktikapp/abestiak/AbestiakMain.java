package com.elorrieta.didaktikapp.abestiak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityAbestiakMainBinding;
import com.elorrieta.didaktikapp.databinding.ActivityDescriptionBinding;
import com.elorrieta.didaktikapp.utilities.SoundPlayer;

public class AbestiakMain extends AppCompatActivity {
    SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAbestiakMainBinding binding = ActivityAbestiakMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_abestiak_main);

        soundPlayer = new SoundPlayer(getApplicationContext(), R.raw.gatibu_loretxoa_moztuta, binding.playPauseButton, binding.seekBar);


    }


    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.pause();
    }
}