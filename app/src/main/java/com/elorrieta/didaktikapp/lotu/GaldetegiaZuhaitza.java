package com.elorrieta.didaktikapp.lotu;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.model.database.AppDatabase;


public class GaldetegiaZuhaitza extends AppCompatActivity implements View.OnClickListener {


    TextView questionTextView;
    Button ansA, ansB, ansC,ansD;



    int totalQuestion = ZuhaitzagalderaDatuak.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galdetegiazuhaitza);


        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);

        loadNewQuestion();




    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;

        if(clickedButton.getId()==R.id.ans_A && ZuhaitzagalderaDatuak.choices[currentQuestionIndex][0].equals(ZuhaitzagalderaDatuak.correctAnswers[currentQuestionIndex])){

                selectedAnswer = clickedButton.getText().toString();
                clickedButton.setBackgroundColor(Color.GREEN);
                currentQuestionIndex++;
                clickedButton.setBackgroundColor(Color.GREEN);
                new AlertDialog.Builder(this)

                        .setMessage("Asmatu duzu!")
                        .setPositiveButton("Jarraitu", (dialogInterface, i) -> loadNewQuestion())
                        .setCancelable(false)
                        .show();

            }
       else if (clickedButton.getId()==R.id.ans_B && ZuhaitzagalderaDatuak.choices[currentQuestionIndex][1].equals(ZuhaitzagalderaDatuak.correctAnswers[currentQuestionIndex])){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage("Asmatu duzu!")
                    .setPositiveButton("Jarraitu", (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();

        }
       else if(clickedButton.getId()==R.id.ans_C && ZuhaitzagalderaDatuak.choices[currentQuestionIndex][2].equals(ZuhaitzagalderaDatuak.correctAnswers[currentQuestionIndex])){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage("Asmatu duzu!")
                    .setPositiveButton("Jarraitu", (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();

        }
        else if(clickedButton.getId()==R.id.ans_D && ZuhaitzagalderaDatuak.choices[currentQuestionIndex][3].equals(ZuhaitzagalderaDatuak.correctAnswers[currentQuestionIndex])){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage("Asmatu duzu!")
                    .setPositiveButton("Jarraitu", (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();

        }


        else{
                clickedButton.setBackgroundColor(Color.RED);
                    new AlertDialog.Builder(this)

                            .setMessage("Ez duzu asmatu duzu!")
                            .setPositiveButton("Saiatu berriro", (dialogInterface, i) -> loadNewQuestion())
                            .setCancelable(false)
                            .show();
                }
            }



    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        questionTextView.setText(ZuhaitzagalderaDatuak.question[currentQuestionIndex]);
        ansA.setText(ZuhaitzagalderaDatuak.choices[currentQuestionIndex][0]);
        ansB.setText(ZuhaitzagalderaDatuak.choices[currentQuestionIndex][1]);
        ansC.setText(ZuhaitzagalderaDatuak.choices[currentQuestionIndex][2]);
        ansD.setText(ZuhaitzagalderaDatuak.choices[currentQuestionIndex][3]);


    }

    void finishQuiz(){
        String passStatus;
        passStatus = "Zorionak";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Zorionak asmatu duzu galdera!!!")
                .setPositiveButton("Jarraitu",(dialogInterface, i) -> endQuiz() )
                .setCancelable(false)
                .show();
    }

    void endQuiz(){
        int gameId = AppDatabase.getDatabase(getApplicationContext()).gameDao().findIdByClass(Lotu.class.getName());
        AppDatabase.getDatabase(getApplicationContext()).gameRecordDao().addCompletion(gameId);
        finish();
    }

}