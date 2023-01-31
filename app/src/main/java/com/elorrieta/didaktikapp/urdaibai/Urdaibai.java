package com.elorrieta.didaktikapp.urdaibai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;

public class Urdaibai extends AppCompatActivity {

    private Button btnNext;
    private Button btnGarbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urdaibai);

        btnNext = findViewById(R.id.btnNext);
        btnGarbi = findViewById(R.id.btnGarbi);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Urdaibai.this, Garbitu.class);
                startActivity(intent);
                finish();
            }
        });

    }
}