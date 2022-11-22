package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.elorrieta.didaktikapp.map.MapsActivity;

public class SplashScreen extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // aplicamos el tema que este guardado en las opciones
            SharedPreferences opciones = PreferenceManager.getDefaultSharedPreferences(this);
            if (opciones.getBoolean(getString(R.string.tema_defecto), true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            } else if (opciones.getBoolean(getString(R.string.tema_oscuro), true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            setContentView(R.layout.activity_splash_screen);

            ImageView imagen = findViewById(R.id.imageView);
            imagen.setOnClickListener(view -> {
                Intent intent;
                intent = new Intent(SplashScreen.this, MapsActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }