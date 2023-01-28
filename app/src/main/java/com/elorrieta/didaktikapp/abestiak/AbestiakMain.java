package com.elorrieta.didaktikapp.abestiak;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityAbestiakMainBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.Song;
import com.elorrieta.didaktikapp.utilities.SoundPlayer;
import com.google.android.material.internal.TextWatcherAdapter;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class AbestiakMain extends AppCompatActivity {

    private LinkedList<LinearLayout> views;
    private ActivityAbestiakMainBinding binding;
    private SoundPlayer soundPlayer;
    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbestiakMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int songNumber = getIntent().getIntExtra("song", 1);

        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        song = AppDatabase.getDatabase(getApplicationContext()).songDao().findById(songNumber);
        views = stringToViews();
        for (View view : views) {
            binding.containerView.addView(view);
        }
        soundPlayer = new SoundPlayer(song.shortAudio, binding.playPauseButton, binding.seekBar);
        binding.btnCheck.setOnClickListener(view -> checkLyrics());
    }

    private LinkedList<LinearLayout> stringToViews() {
        LinkedList<LinearLayout> views = new LinkedList<>();
        String[] lines = song.fillLyrics.split("\r\n");
        EditText editText = null;
        // recorremos cada linea
        for (String line : lines) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(params);

            String[] columnArray = line.split("_");
            //recorremos cada elemento de la linea rellenando los espacios con edittext
            for (int column = 0; column < columnArray.length; column++) {
                    TextView textView = new TextView(this);
                    textView.setText(columnArray[column]);
                    linearLayout.addView(textView);

                // si aun quedan espacios por rellenar creamos un edittext
                if (column < columnArray.length - 1) {
                    editText = chainEditText(editText);
                    linearLayout.addView(editText);
                }
            }

            int fillSpacesAtEnd = countCharsAtEnd(line, '_');
            // rellenamos los espacios al final
            for (int i = 0; i < fillSpacesAtEnd; i++) {
                editText = chainEditText(editText);
                linearLayout.addView(editText);
            }

            views.add(linearLayout);
        }
        return views;
    }

    private int countCharsAtEnd(String line, char c) {
        int count = 0;
        for (int i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == c) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    @SuppressLint("RestrictedApi")
    private EditText chainEditText(EditText previous){
        // filtros para el edittext
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(1);
        filterArray[1] = new InputFilter.AllCaps();

        EditText editText = new EditText(this);
        editText.setFilters(filterArray);
        editText.setId(View.generateViewId());
        editText.setSelectAllOnFocus(true);
        // metodo que hace saltar de un edittext a otro al escribir una letra
        editText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(@NonNull Editable s) {
                if (s.length() == 1) {
                    int nextFocus = editText.getNextFocusForwardId();
                    if (nextFocus != View.NO_ID) {
                        View next = editText.getRootView().findViewById(nextFocus);
                        if (next != null) {
                            next.requestFocus();
                        }
                    } else {
                        //cerrar el teclado
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                }
            }
        });
        // hacemos que el edittext anterior salte al siguiente
        if (previous != null) {
            previous.setNextFocusForwardId(editText.getId());
        }
        return editText;
    }

    private void checkLyrics() {
        String lyrics = viewsToString(views);
        if (lyrics.equals(song.lyrics)) {
            soundPlayer = new SoundPlayer(song.longAudio, binding.playPauseButton, binding.seekBar);

            // Abrimos un prompt para preguntar el grupo
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("zein taldek jotzen du abestia?");
            alert.setCancelable(false);

            // Layout para el prompt
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;
            layout.setLayoutParams(params);

            Button button1 = new Button(this);
            layout.addView(button1);
            Button button2 = new Button(this);
            layout.addView(button2);
            alert.setView(layout);
            // Hay que abrir el dialogo antes de establecer los metodos de los botones para que luego puedan cerrarlo
            AlertDialog dialog = alert.show();

            button1.setText("Gatibu");
            button1.setOnClickListener(view -> alertButtonFunction(button1, "Gatibu", dialog));

            button2.setText("Ken zazpi");
            button2.setOnClickListener(view -> alertButtonFunction(button2, "Ken Zazpi", dialog));
        }
    }

    private void alertButtonFunction(Button button, String band, AlertDialog dialog) {
        if (song.band.equals(band)) {
            button.setBackgroundColor(Color.GREEN);
            new AlertDialog.Builder(AbestiakMain.this)
                    .setMessage("Asmatu duzu! Orain abesti osoa entzun dezakezu")
                    .setPositiveButton("Jarraitu", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setCancelable(false)
                    .show();
            dialog.dismiss();
            int lastSong = AppDatabase.getDatabase(getApplicationContext()).songDao().lastSong();
            if (song.idSong < lastSong) {
                binding.btnCheck.setText("Hurrengo abestia");
                binding.btnCheck.setOnClickListener(view -> nextSong());
            } else {
                binding.btnCheck.setText(R.string.mapara_bueltatu);
                binding.btnCheck.setOnClickListener(view -> endActivity());
            }
        } else {
            button.setBackgroundColor(Color.RED);
        }
    }

    private String viewsToString(LinkedList<LinearLayout> views) {
        String lyrics = "";

        for (int line = 0; line < views.size(); line++) {
            LinearLayout view = views.get(line);

            for (int column = 0; column < view.getChildCount(); column++) {
                if (view.getChildAt(column) instanceof EditText) {
                    lyrics += ((EditText) view.getChildAt(column)).getText().toString();
                } else {
                    lyrics += ((TextView) view.getChildAt(column)).getText().toString();
                }
            }
            if (line < views.size() - 1) {
                lyrics += "\r\n";
            }
        }
        return lyrics;
    }

    private void nextSong() {
        Intent intent = new Intent(this, AbestiakMain.class);
        intent.putExtra("song", song.idSong + 1);
        startActivity(intent);
        finish();
    }

    private void endActivity() {
        int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(this.getClass().getName());
        AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.pause();
    }
}