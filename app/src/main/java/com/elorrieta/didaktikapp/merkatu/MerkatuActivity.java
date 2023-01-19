package com.elorrieta.didaktikapp.merkatu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.lotu.GaldetegiaZuhaitza;
import com.elorrieta.didaktikapp.lotu.Lotu;
import com.elorrieta.didaktikapp.map.MapsActivity;

public class MerkatuActivity extends AppCompatActivity implements View.OnClickListener {

    Button alfonbra, erroskilak, piperrak, detergentea, indabak, artoa, mugikorra, zapatilak, txakolina, yogurta, tomateak, azenarioa;
    boolean alfonbraB = false, erroskilakB = false, piperrakB = false, detergenteaB = false, indabakB = false, artoaB = false, mugikorraB = false, zapatilakB = false, txakolinaB = false, yogurtaB = false, tomateakB = false, azenarioaB = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkatu);

        alfonbra = findViewById(R.id.btnAlfombra);
        erroskilak = findViewById(R.id.btnErroxkilak);
        piperrak = findViewById(R.id.btnPiperrak);
        detergentea = findViewById(R.id.btnDetergentea);
        indabak = findViewById(R.id.btnIndabak);
        artoa = findViewById(R.id.btnArtoa);
        mugikorra = findViewById(R.id.btnMugikorrak);
        zapatilak = findViewById(R.id.btnZapatila);
        txakolina = findViewById(R.id.btnTxakolina);
        yogurta = findViewById(R.id.btnTomateak);
        tomateak = findViewById(R.id.btnYogurtak);
        azenarioa = findViewById(R.id.btnAzenarioa);

        alfonbra.setOnClickListener(this);
        erroskilak.setOnClickListener(this);
        piperrak.setOnClickListener(this);
        detergentea.setOnClickListener(this);

        indabak.setOnClickListener(this);
        artoa.setOnClickListener(this);
        mugikorra.setOnClickListener(this);
        zapatilak.setOnClickListener(this);

    }
    @Override
    public void onClick (View view){

        Button clickedButton = (Button) view;

        if (alfonbra.isPressed()) {
            alfonbra.setBackgroundColor(Color.GREEN);
            alfonbraB = true;
        } else if (erroskilak.isPressed()) {
            erroskilak.setBackgroundColor(Color.GREEN);
            erroskilakB = true;
        } else if (piperrak.isPressed()) {
            piperrak.setBackgroundColor(Color.GREEN);
            piperrakB = true;
        } else if (detergentea.isPressed()) {
            detergentea.setBackgroundColor(Color.GREEN);
            detergenteaB = true;
        } else if (indabak.isPressed()) {
            indabak.setBackgroundColor(Color.GREEN);
            indabakB = true;
        } else if (artoa.isPressed()) {
            artoa.setBackgroundColor(Color.GREEN);
            artoaB = true;
        } else if (mugikorra.isPressed()) {
            mugikorra.setBackgroundColor(Color.GREEN);
            mugikorraB = true;
        } else if (zapatilak.isPressed()) {
            zapatilak.setBackgroundColor(Color.GREEN);
            zapatilakB = true;
        } else if (txakolina.isPressed()) {
            txakolina.setBackgroundColor(Color.GREEN);
            txakolinaB = true;
        } else if (yogurta.isPressed()) {
            yogurta.setBackgroundColor(Color.GREEN);
            yogurtaB = true;
        } else if (tomateak.isPressed()) {
            tomateak.setBackgroundColor(Color.GREEN);
            tomateakB = true;
        } else if (azenarioa.isPressed()) {
            azenarioa.setBackgroundColor(Color.GREEN);
            azenarioaB = true;
        }
        else if(alfonbraB==true && erroskilakB==true && piperrakB==true && detergenteaB==true && indabakB==true && artoaB==true && mugikorraB==true && zapatilakB==true && txakolinaB==true && yogurtaB==true && tomateakB==true && azenarioaB==true){
            GalderaEgin();

        }
}
    public void GalderaEgin () {
        // In startGame() method, create an Intent to launch StartGame Activity
        Intent intent = new Intent(MerkatuActivity.this, MapsActivity.class);
        startActivity(intent);
        // Finish Lotu
        finish();
    }
}