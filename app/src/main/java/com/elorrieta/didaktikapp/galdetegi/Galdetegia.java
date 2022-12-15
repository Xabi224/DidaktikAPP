package com.elorrieta.didaktikapp.galdetegi;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.SplashScreen;
import com.elorrieta.didaktikapp.map.MapsActivity;
import com.elorrieta.didaktikapp.puzzle.PuzzleActivity;

public class Galdetegia extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC;


    int score=0;
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




        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();




    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;

        if(clickedButton.getId()==R.id.ans_A && QuestionAnswer.choices[currentQuestionIndex][0]==QuestionAnswer.correctAnswers[currentQuestionIndex]){

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
       else if (clickedButton.getId()==R.id.ans_B && QuestionAnswer.choices[currentQuestionIndex][1]==QuestionAnswer.correctAnswers[currentQuestionIndex]){

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
            currentQuestionIndex++;

            new AlertDialog.Builder(this)

                    .setMessage("Asmatu duzu!")
                    .setPositiveButton("Jarraitu", (dialogInterface, i) -> loadNewQuestion())
                    .setCancelable(false)
                    .show();
       ;
        }
       else if(clickedButton.getId()==R.id.ans_C && QuestionAnswer.choices[currentQuestionIndex][2]==QuestionAnswer.correctAnswers[currentQuestionIndex]){

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
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);


    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*1){
            passStatus = "Zorionak";
        }else{
            passStatus = "Zorionak";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Zure piezak 4/4 dira")
                .setPositiveButton("Puzzlea egin!!",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();


    }

    void restartQuiz(){
        Intent intent = new Intent(Galdetegia.this, PuzzleActivity.class);
        startActivity(intent);
        finish();
    }

}