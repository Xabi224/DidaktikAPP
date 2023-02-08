package com.elorrieta.didaktikapp.urdaibai;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.map.MapsActivity;
import com.elorrieta.didaktikapp.model.database.AppDatabase;

public class Garbitu extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbitu);

        Button btnMapa = findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(v -> endActivity());

            //findViewById(R.id.imgArrautzak).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgBeira).setOnTouchListener(new MyTouchListener());

            findViewById(R.id.imgCafe).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgLata).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgPapera).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgPlastiko1).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgPlastiko2).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgPlatano).setOnTouchListener(new MyTouchListener());
            findViewById(R.id.imgSagarra).setOnTouchListener(new MyTouchListener());

            //--------//
            findViewById(R.id.beira).setOnDragListener(new MyDragListener1());

            findViewById(R.id.papela).setOnDragListener(new MyDragListener1());
            findViewById(R.id.organikoa).setOnDragListener(new MyDragListener1());
            findViewById(R.id.plastikoa).setOnDragListener(new MyDragListener1());


        }

        private final class MyTouchListener implements View.OnTouchListener {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        }

        class MyDragListener1 implements View.OnDragListener {

            Drawable enterShape = getResources().getDrawable(R.drawable.beira);
            Drawable normalShape = getResources().getDrawable(R.drawable.beira);

            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                int contador = 0;
                switch (event.getAction()) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackgroundDrawable(enterShape);

                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundDrawable(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                    /*
                    contador = contador + 1;
                    if (contador == 9) {
                        Intent intent = new Intent(MainActivity.this, IMAGE.class);
                        startActivity(intent);
                        finish();
                        break;
                    }

                     */

                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        view.setVisibility(View.INVISIBLE);

                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundDrawable(normalShape);
                        contador = contador + 1;
                        if (contador == 3) {
                            Intent intent = new Intent(Garbitu.this, MapsActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }

                    default:
                        break;
                }
                return true;
            }

        }


        private void endActivity () {
            int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(this.getClass().getName());
            AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
            finish();
        }
    }
}