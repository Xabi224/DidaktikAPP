package com.elorrieta.didaktikapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.databinding.ActivityDescriptionBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.Game;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;
import com.elorrieta.didaktikapp.utilities.SoundPlayer;

public class DescriptionActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;
    private SoundPlayer soundPlayerExtra;

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

        // establecer los textos
        binding.tvDescription.setText(game.description);
        if (game.descriptionExtra != null) {
            binding.tvDescriptionExtra.setText(game.descriptionExtra);
        } else {
            binding.tvDescriptionExtra.setVisibility(View.GONE);
        }

        // establecer los audios
        soundPlayer = new SoundPlayer(game.audio, binding.playPauseButton, binding.seekBar);
        if (game.audioExtra != null) {
            soundPlayerExtra = new SoundPlayer(game.audioExtra, binding.playPauseButtonExtra, binding.seekBarExtra);
        } else {
            binding.playPauseButtonExtra.setVisibility(View.GONE);
            binding.seekBarExtra.setVisibility(View.GONE);
        }

        // funcion del boton de iniciar el juego
        binding.button.setOnClickListener(view -> changeActivity(game.gameClass));
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
        soundPlayer.pause();
        if (soundPlayerExtra != null) {
            soundPlayerExtra.pause();
        }
    }
}