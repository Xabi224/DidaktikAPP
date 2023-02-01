package com.elorrieta.didaktikapp.merkatu;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.model.database.AppDatabase;

public class MerkatuActivity extends AppCompatActivity implements View.OnClickListener {

    Button alfonbra, erroskilak, piperrak, detergentea, indabak, artoa, mugikorra, zapatilak, txakolina, yogurta, tomateak, azenarioa;
    int count = 0;

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
        yogurta = findViewById(R.id.btnYogurtak);
        tomateak = findViewById(R.id.btnTomateak);
        azenarioa = findViewById(R.id.btnAzenarioa);

        alfonbra.setOnClickListener(this);
        erroskilak.setOnClickListener(this);
        piperrak.setOnClickListener(this);
        detergentea.setOnClickListener(this);

        indabak.setOnClickListener(this);
        artoa.setOnClickListener(this);
        mugikorra.setOnClickListener(this);
        zapatilak.setOnClickListener(this);
        azenarioa.setOnClickListener(this);
        txakolina.setOnClickListener(this);
        yogurta.setOnClickListener(this);
        tomateak.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (alfonbra.isPressed()) {
            desactivarBotonRojo(alfonbra);
        } else if (erroskilak.isPressed()) {
            desactivarBotonVerde(erroskilak);
            count++;
        } else if (piperrak.isPressed()) {
            desactivarBotonVerde(piperrak);
            count++;
        } else if (detergentea.isPressed()) {
            desactivarBotonRojo(detergentea);
        } else if (indabak.isPressed()) {
            desactivarBotonVerde(indabak);
            count++;
        } else if (artoa.isPressed()) {
            desactivarBotonVerde(artoa);
            count++;
        } else if (mugikorra.isPressed()) {
            desactivarBotonRojo(mugikorra);
        } else if (zapatilak.isPressed()) {
            desactivarBotonRojo(zapatilak);
        } else if (txakolina.isPressed()) {
            desactivarBotonVerde(txakolina);
            count++;
        } else if (yogurta.isPressed()) {
            desactivarBotonRojo(yogurta);
        } else if (tomateak.isPressed()) {
            desactivarBotonVerde(tomateak);
            count++;
        } else if (azenarioa.isPressed()) {
            desactivarBotonVerde(azenarioa);
            count++;
        }
        if (count == 7) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.zorionak_jokoa_amaitu)
                    .setCancelable(false)
                    .setPositiveButton(R.string.mapara_bueltatu, (dialog, which) -> endActivity())
                    .show();
        }
    }

    private void endActivity() {
        int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(this.getClass().getName());
        AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
        finish();
    }

    public void desactivarBotonVerde(Button boton) {
        boton.setEnabled(false);
        boton.setBackgroundColor(Color.GREEN);
        boton.setTextColor(Color.BLACK);
    }

    public void desactivarBotonRojo(Button boton) {
        boton.setEnabled(false);
        boton.setBackgroundColor(Color.RED);
        boton.setTextColor(Color.BLACK);
    }
}