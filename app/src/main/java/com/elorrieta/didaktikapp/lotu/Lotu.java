package com.elorrieta.didaktikapp.lotu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.elorrieta.didaktikapp.R;

public class Lotu extends AppCompatActivity implements View.OnClickListener {
    Button haritzaB, pinuaB, arteaB, ginkoaB, aukera1, aukera2, aukera3, aukera4;
    boolean a, b, c, d = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotu);

        haritzaB = findViewById(R.id.btn_haritza);
        pinuaB = findViewById(R.id.btn_pinua);
        arteaB = findViewById(R.id.btn_artea);
        ginkoaB = findViewById(R.id.btn_ginkoa);
        aukera1 = findViewById(R.id.btnaukera1);
        aukera2 = findViewById(R.id.btnaukera2);
        aukera3 = findViewById(R.id.btnaukera3);
        aukera4 = findViewById(R.id.btnaukera4);


        haritzaB.setOnClickListener(this);
        pinuaB.setOnClickListener(this);
        arteaB.setOnClickListener(this);
        ginkoaB.setOnClickListener(this);

        aukera1.setOnClickListener(this);
        aukera2.setOnClickListener(this);
        aukera3.setOnClickListener(this);
        aukera4.setOnClickListener(this);


    }

        @Override
        public void onClick (View view){

            Button clickedButton = (Button) view;

            if (pinuaB.isPressed() && aukera1.isPressed()) {
                pinuaB.setBackgroundColor(Color.GREEN);
                aukera1.setBackgroundColor(Color.GREEN);
                aukera1.setText("Pinua");
                a = true;
            } else if (arteaB.isPressed() && aukera2.isPressed()) {
                arteaB.setBackgroundColor(Color.GREEN);
                aukera2.setBackgroundColor(Color.GREEN);
                aukera2.setText("Artea");
                b = true;
            } else if (ginkoaB.isPressed() && aukera3.isPressed()) {
                ginkoaB.setBackgroundColor(Color.GREEN);
                aukera3.setBackgroundColor(Color.GREEN);
                aukera3.setText("Ginkoa");
                c = true;
            } else if (haritzaB.isPressed() && aukera4.isPressed()) {
                haritzaB.setBackgroundColor(Color.GREEN);
                aukera4.setBackgroundColor(Color.GREEN);
                aukera4.setText("Haritza");
                d = true;

            }

           else if (a == true && b==true && c == true && d == true) {
                GalderaEgin();
            }
        }


        public void GalderaEgin () {
            // In startGame() method, create an Intent to launch StartGame Activity
            Intent intent = new Intent(Lotu.this, GaldetegiaZuhaitza.class);
            startActivity(intent);
            // Finish Lotu
            finish();
        }

    }


