package com.elorrieta.didaktikapp.abestiak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.CursorWindow;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
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

    private LinkedList<LinearLayout> stringToViews(String lyrics) {
        LinkedList<LinearLayout> views = new LinkedList<>();
        String[] lines = lyrics.split("\r\n");
        for (String line : lines) {
            views.add(lineToView(line));
        }
        return views;
    }

    @SuppressLint("RestrictedApi")
    private LinearLayout lineToView(String line) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(1);
        filterArray[1] = new InputFilter.AllCaps();
        String[] words = line.split("_");
        // TODO: saltar de una linea a la siguiente tambien
        EditText previousEditText = null;
        for (int i = 0; i < words.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(words[i]);
            linearLayout.addView(textView);
            if (i < words.length - 1) {
                EditText editText = new EditText(this);
                editText.setFilters(filterArray);
                editText.setId(View.generateViewId());
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
                            }
                        }
                    }
                });
                linearLayout.addView(editText);
                if (previousEditText != null) {
                    previousEditText.setNextFocusForwardId(editText.getId());
                }
                previousEditText = editText;
            }

        }
        return linearLayout;
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
        for (int i = 0; i < views.size(); i++) {
            LinearLayout view = views.get(i);
            for (int j = 0; j < view.getChildCount(); j++) {
                if (view.getChildAt(j) instanceof EditText) {
                    lyrics += ((EditText) view.getChildAt(j)).getText().toString();
                } else {
                    lyrics += ((TextView) view.getChildAt(j)).getText().toString();
                }
            }
            if (i < views.size() - 1) {
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