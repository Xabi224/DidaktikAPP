package com.elorrieta.didaktikapp.galdetegi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;

public class GaldetegiMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galdetegi_main);
    }

    public void galdetegia(View view) {
        // In startGame() method, create an Intent to launch StartGame Activity
        Intent intent = new Intent(GaldetegiMain.this, Galdetegia.class);
        startActivity(intent);
        // Finish GaldetegiMain
        finish();
    }
}