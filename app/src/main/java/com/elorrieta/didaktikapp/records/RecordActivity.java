package com.elorrieta.didaktikapp.records;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityRecordBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.GameRecord;
import com.elorrieta.didaktikapp.model.pojo.GameRecordPOJO;

import java.util.List;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecordBinding binding = ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ListView list = binding.listView;
        List<GameRecordPOJO> records = AppDatabase.getDatabase(this).gameRecordDao().getAllGamesAndRecords();
        GameRecordAdapter adapter = new GameRecordAdapter(this, records);
        list.setAdapter(adapter);

        // funcion al hacer click que borra el registro seleccionado
        list.setOnItemClickListener((parent, view, position, id) -> new AlertDialog.Builder(this)
                .setMessage("Erregistroa ezabatu nahi duzu?")
                .setPositiveButton(R.string.mapara_bueltatu, (dialog, which) -> {
                    GameRecordPOJO recordPOJO = (GameRecordPOJO) parent.getItemAtPosition(position);
                    GameRecord gameRecord = new GameRecord(recordPOJO.getDate(), recordPOJO.getIdGame(), recordPOJO.getCompletions());
                    AppDatabase.getDatabase(this).gameRecordDao().delete(gameRecord);
                    adapter.remove(recordPOJO);
                })
                .show());

        // funcion del boton para borrar todos los registros
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnDeleteAll.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setMessage("Erregistro guztiak ezabatu nahi dituzu?")
                .setPositiveButton(R.string.mapara_bueltatu, (dialog, which) -> {
                    AppDatabase.getDatabase(this).gameRecordDao().deleteAll();
                    adapter.clear();
                })
                .show());
    }
}