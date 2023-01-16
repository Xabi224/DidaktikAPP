package com.elorrieta.didaktikapp.merkatu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;

public class MerkatuActivity extends AppCompatActivity {
    Button alfonbra = findViewById(R.id.btnAlfombra);
    Button erroskilak = findViewById(R.id.btnErroxkilak);
    Button piperrak = findViewById(R.id.btnPiperrak);
    Button detergentea = findViewById(R.id.btnDetergentea);
    Button indabak = findViewById(R.id.btnIndabak);
    Button artoa = findViewById(R.id.btnArtoa);
    Button mugikorra = findViewById(R.id.btnMugikorrak);
    Button zapatilak = findViewById(R.id.btnZapatila);
    Button txakolina = findViewById(R.id.btnTxakolina);
    Button yogurta = findViewById(R.id.btnTomateak);
    Button tomateak = findViewById(R.id.btnYogurtak);
    Button azenarioa = findViewById(R.id.btnAzenarioa);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkatu);

        alfonbra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alfonbra.setBackgroundColor(Color.RED);
            }
        });

        erroskilak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erroskilak.setBackgroundColor(Color.GREEN);
            }
        });

        piperrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piperrak.setBackgroundColor(Color.GREEN);
            }
        });

        detergentea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detergentea.setBackgroundColor(Color.RED);
            }
        });

        indabak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indabak.setBackgroundColor(Color.GREEN);
            }
        });

        artoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artoa.setBackgroundColor(Color.GREEN);
            }
        });

        mugikorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mugikorra.setBackgroundColor(Color.RED);
            }
        });

        zapatilak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zapatilak.setBackgroundColor(Color.RED);
            }
        });

        txakolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txakolina.setBackgroundColor(Color.GREEN);
            }
        });

        yogurta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yogurta.setBackgroundColor(Color.GREEN);
            }
        });

        tomateak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomateak.setBackgroundColor(Color.GREEN);
            }
        });

        azenarioa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                azenarioa.setBackgroundColor(Color.GREEN);
            }
        });
    }
}