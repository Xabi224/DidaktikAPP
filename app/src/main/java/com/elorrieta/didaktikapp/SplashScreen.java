package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.elorrieta.didaktikapp.map.MapsActivity;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // pedimos los permisos que seran necesarios durante el uso de la app
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO: cambiar a false cuando se termine
        preferences.edit().putBoolean("freeMode", true).apply();

        setContentView(R.layout.activity_splash_screen);

        ImageView imagen = findViewById(R.id.imageView);

        imagen.setOnClickListener(view -> {
            Intent intent = new Intent(SplashScreen.this, MapsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}