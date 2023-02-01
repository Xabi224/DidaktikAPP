package com.elorrieta.didaktikapp.galdetegi;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;

import com.elorrieta.didaktikapp.puzzle.PuzzleActivity;

public class Galdetegia extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC;


    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galdetegia);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);

        totalQuestionsTextView.setText(getString(R.string.galdera_kopurua, totalQuestion));
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;

        if(clickedButton.getId()==R.id.ans_A && getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][0]).equals(getApplicationContext().getText(QuestionAnswer.correctAnswers[currentQuestionIndex]))){

                selectedAnswer = clickedButton.getText().toString();
                clickedButton.setBackgroundColor(Color.GREEN);
                currentQuestionIndex++;
                clickedButton.setBackgroundColor(Color.GREEN);
                new AlertDialog.Builder(this)

                        .setMessage(R.string.asmatu_duzu)
                        .setPositiveButton(R.string.jarraitu, (dialogInterface, i) -> loadNewQuestion())
                        .setCancelable(false)
                        .show();

            }
       else if (clickedButton.getId()==R.id.ans_B && getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][1]).equals(getApplicationContext().getText(QuestionAnswer.correctAnswers[currentQuestionIndex]))){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage(R.string.asmatu_duzu)
                    .setPositiveButton(R.string.jarraitu, (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();

        }
       else if(clickedButton.getId()==R.id.ans_C && getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][2]).equals(getApplicationContext().getText(QuestionAnswer.correctAnswers[currentQuestionIndex]))){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage(R.string.asmatu_duzu)
                    .setPositiveButton(R.string.jarraitu, (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();

        }

        else{
                clickedButton.setBackgroundColor(Color.RED);
                    new AlertDialog.Builder(this)

                            .setMessage(R.string.saiatu_berriro)
                            .setPositiveButton(R.string.saiatu_berriro, (dialogInterface, i) -> loadNewQuestion())
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
        questionTextView.setText(getApplicationContext().getText(QuestionAnswer.question[currentQuestionIndex]));
        ansA.setText(getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][0]));
        ansB.setText(getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][1]));
        ansC.setText(getApplicationContext().getText(QuestionAnswer.choices[currentQuestionIndex][2]));


    }

    void finishQuiz(){
        String passStatus;
        passStatus = getString(R.string.zorionak);

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(R.string.zure_piezak)
                .setPositiveButton(R.string.puzzlea_egin,(dialogInterface, i) -> endQuiz() )
                .setCancelable(false)
                .show();
    }

    void endQuiz(){
        Intent intent = new Intent(Galdetegia.this, PuzzleActivity.class);
        startActivity(intent);
        finish();
    }

}