package com.elorrieta.didaktikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

               eskupi.start();
               finish();
           }
       });

       boton_frontis.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               frontis.start();
               finish();
           }
       });

       boton_jaiAlai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               jai_alai.start();
               finish();
           }
       });

       boton_pala.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               pala.start();
               finish();
           }
       });

       boton_zesta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               zesta.start();
               finish();
           }
       });



    }
}