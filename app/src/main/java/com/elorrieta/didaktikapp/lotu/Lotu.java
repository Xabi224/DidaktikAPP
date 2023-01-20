package com.elorrieta.didaktikapp.lotu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;

public class Lotu extends AppCompatActivity implements View.OnTouchListener {
    Button haritzaB, pinuaB, arteaB, ginkoaB, aukera1, aukera2, aukera3, aukera4;
    boolean a, b, c, d = false;
    int count = 0;

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


        haritzaB.setOnTouchListener(this);
        pinuaB.setOnTouchListener(this);
        arteaB.setOnTouchListener(this);
        ginkoaB.setOnTouchListener(this);

        aukera1.setOnTouchListener(this);
        aukera2.setOnTouchListener(this);
        aukera3.setOnTouchListener(this);
        aukera4.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        if (pinuaB.isPressed() && aukera1.isPressed()) {
            desactivarBoton(pinuaB);
            desactivarBoton(aukera1);
            aukera1.setText("Pinua");

            count++;


        } else if (arteaB.isPressed() && aukera2.isPressed()) {
            desactivarBoton(arteaB);
            desactivarBoton(aukera2);
            aukera2.setText("Artea");
            count++;

        } else if (ginkoaB.isPressed() && aukera3.isPressed()) {
            desactivarBoton(aukera3);
            desactivarBoton(ginkoaB);
            aukera3.setText("Ginkoa");
            count++;

        } else if (haritzaB.isPressed() && aukera4.isPressed()) {
            aukera4.setText("Haritza");
            desactivarBoton(haritzaB);
            desactivarBoton(aukera4);
            count++;


        }

        if (count == 4) {
            galderaEgin();
        }

        return false;
    }


    public void galderaEgin() {
        // In startGame() method, create an Intent to launch StartGame Activity
        Intent intent = new Intent(Lotu.this, GaldetegiaZuhaitza.class);
        startActivity(intent);
        // Finish Lotu
        finish();
    }

    public void desactivarBoton(Button boton) {
        boton.setEnabled(false);
        boton.setBackgroundColor(Color.GREEN);
        boton.setTextColor(Color.BLACK);
    }
}