package com.elorrieta.didaktikapp.abestiak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.CursorWindow;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityAbestiakMainBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.Song;
import com.elorrieta.didaktikapp.utilities.SoundPlayer;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

public class AbestiakMain extends AppCompatActivity {

    private LinkedList<LinearLayout> views;
    private ActivityAbestiakMainBinding binding;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbestiakMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        Song song = AppDatabase.getDatabase(getApplicationContext()).songDao().findById(1);
        views = stringToViews(song.fillLyrics);
        for (View view : views) {
            binding.containerView.addView(view);
        }
        soundPlayer = new SoundPlayer(song.shortAudio, binding.playPauseButton, binding.seekBar);
        binding.btnCheck.setOnClickListener(view -> checkLyrics(song));
    }

    @SuppressLint("RestrictedApi")
    private LinkedList<LinearLayout> stringToViews(String lyrics) {
        LinkedList<LinearLayout> views = new LinkedList<>();
        String[] lines = lyrics.split("\r\n");
        EditText previousEditText = null;
        // recorremos cada linea
        for (String line : lines) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            // filtros para el edittext
            InputFilter[] filterArray = new InputFilter[2];
            filterArray[0] = new InputFilter.LengthFilter(1);
            filterArray[1] = new InputFilter.AllCaps();

            String[] columnArray = line.split("_");
            //recorremos cada elemento de la linea
            for (int column = 0; column < columnArray.length; column++) {
                TextView textView = new TextView(this);
                textView.setText(columnArray[column]);
                linearLayout.addView(textView);
                // si no es el ultimo elemento de la linea creamos un edittext
                if (column < columnArray.length - 1) {
                    EditText editText = new EditText(this);
                    editText.setFilters(filterArray);
                    editText.setId(View.generateViewId());
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
                                    InputMethodManager imm = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    }
                                    assert imm != null;
                                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                }
                            }
                        }
                    });
                    linearLayout.addView(editText);
                    // hacemos que el edittext anterior salte al siguiente
                    if (previousEditText != null) {
                        previousEditText.setNextFocusForwardId(editText.getId());
                    }
                    previousEditText = editText;
                }
            }
            views.add(linearLayout);
        }
        return views;
    }

    private void checkLyrics(Song song) {
        String lyrics = viewsToString(views);
        Toast.makeText(this, lyrics, Toast.LENGTH_SHORT).show();
        if (lyrics.equals(song.lyrics)) {
            soundPlayer = new SoundPlayer(song.longAudio, binding.playPauseButton, binding.seekBar);
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

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.pause();
    }
}