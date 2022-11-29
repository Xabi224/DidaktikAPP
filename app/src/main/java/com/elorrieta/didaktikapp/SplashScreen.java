package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.elorrieta.didaktikapp.map.MapsActivity;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;
import com.google.android.gms.maps.model.LatLng;

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

        // Zona de pruebas para Room
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "didaktikapp").allowMainThreadQueries().build();
//        LatLng murala = new LatLng(43.315504984331355, -2.6800469989231344);
//        PlaceOfInterest poi1 = new PlaceOfInterest(1, "Gernikako bonbardaketa (Guernica murala)", murala);
//        LatLng bunker = new LatLng(43.313752475470565, -2.6790814036719923);
//        PlaceOfInterest poi2 = new PlaceOfInterest(2, "Pasealeku Bunkerra", bunker);
//        LatLng zuhaitza = new LatLng(43.313350799395316, -2.6800029250450503);
//        PlaceOfInterest poi3 = new PlaceOfInterest(3, "Gernikako zuhaitza", zuhaitza);
//        LatLng frontoia = new LatLng(43.317368972699555, -2.678771362726186);
//        PlaceOfInterest poi4 = new PlaceOfInterest(4, "Jai Alai frontoia", frontoia);
//        LatLng sanJuanIbarra = new LatLng(43.31717205318912, -2.6772421608758155);
//        PlaceOfInterest poi5 = new PlaceOfInterest(5, "Urriko azken astelehena (San Juan Ibarra plaza)", sanJuanIbarra);
//        LatLng zubia = new LatLng(43.31747284222259, -2.675430714429948);
//        PlaceOfInterest poi6 = new PlaceOfInterest(6, "Urdaibaiko Biosfera Erreserba (Errenteriako zubia)", zubia);
//        LatLng auditorio = new LatLng(43.31344383698917, -2.678843086461924);
//        PlaceOfInterest poi7 = new PlaceOfInterest(7, "Gatibu eta Ken Zazpi (Auditorio Seber Altube)", auditorio);
//        db.placeOfInterestDao().insertAll(poi1, poi2, poi3, poi4, poi5, poi6, poi7);
    }
}