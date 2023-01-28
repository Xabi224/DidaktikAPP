package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.elorrieta.didaktikapp.model.database.AppDatabase;

public class JaiAlai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jai_alai);



       Button boton_eskupilota  = findViewById(R.id.eskupilota);
       Button boton_frontis = findViewById(R.id.frontoia);
       Button boton_jaiAlai = findViewById(R.id.jaialai);
       Button boton_pala = findViewById(R.id.pala);
       Button boton_zesta = findViewById(R.id.zesta);

       //creacion de los audios

        MediaPlayer eskupi = MediaPlayer.create(this, R.raw.eskupilota);

        MediaPlayer frontis = MediaPlayer.create(this, R.raw.frontisa);

        MediaPlayer jai_alai = MediaPlayer.create(this, R.raw.jaialai);

        MediaPlayer pala = MediaPlayer.create(this, R.raw.pala);

        MediaPlayer zesta = MediaPlayer.create(this, R.raw.zestapunta);



       boton_eskupilota.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int posicion1;
               if(eskupi != null && eskupi.isPlaying()) {
                   posicion1 = eskupi.getCurrentPosition();
                   eskupi.pause();
               }else {
                   eskupi.start();
               }
           }
       });

       boton_frontis.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               int posicion2;
               if(frontis != null && frontis.isPlaying()) {
                   posicion2 = frontis.getCurrentPosition();
                   frontis.pause();
               }else {
                   frontis.start();
               }
           }
       });

       boton_jaiAlai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int posicion3;
               if(jai_alai != null && jai_alai.isPlaying()) {
                   posicion3 = jai_alai.getCurrentPosition();
                   jai_alai.pause();
               }else {
                   jai_alai.start();
               }
           }
       });

       boton_pala.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int posicion4;
               if(pala != null && pala.isPlaying()) {
                   posicion4 = pala.getCurrentPosition();
                   pala.pause();
               }else {
                   pala.start();
               }
           }
       });

       boton_zesta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int posicion5;
               if(zesta != null && zesta.isPlaying()) {
                   posicion5 = zesta.getCurrentPosition();
                   zesta.pause();
               }else {
                   zesta.start();
               }
           }
       });


       Button btnMapa = findViewById(R.id.btnMapa);
         btnMapa.setOnClickListener(v -> endActivity());

    }

    private void endActivity() {
        int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(this.getClass().getName());
        AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
        finish();
    }
}