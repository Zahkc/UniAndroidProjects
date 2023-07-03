package com.zb147.quizforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class results extends AppCompatActivity {
    TextView splash, scored, right, wrong, overall;
    Intent intent;
    DatabaseHelper myDB;
    boolean quizType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        splash = (TextView) findViewById(R.id.splashtext);
        scored = (TextView) findViewById(R.id.scoreText);
        right = (TextView) findViewById(R.id.rightText);
        wrong = (TextView) findViewById(R.id.wrongText);
        overall = (TextView) findViewById(R.id.overallText);

        myDB = new DatabaseHelper(this,  null, 1);

        quizType = getIntent().getBooleanExtra("quizType", false);

        String quiz;
        if (quizType){
            quiz = "Cartoon";
        } else {
            quiz = "Animal";
        }

        splash.setText("Well done " + ((CurrentUser) this.getApplication()).getUsername() + "\n you have finished the " + quiz + " quiz");

        int[] results = getIntent().getIntArrayExtra("results");
        int total = 0, correct = 0;
        for (int x = 0; x < 4; x++){
            total += results[x];
            if (results[x] > 0){
                correct++;
            }
        }
        myDB.addRecord(((CurrentUser) this.getApplication()).getUsername(), total, quiz.toUpperCase());

        scored.setText("You scored " + total + " points");

        right.setText("You got " + correct + " questions right" );
        wrong.setText("and you got " + (4-correct) + " questions wrong");

        ((CurrentUser) this.getApplication()).updateUserPoints();

        overall.setText("Overall you have " + ((CurrentUser) this.getApplication()).getUserPoints() + " points");

    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    public void retryQuiz(View view){
        intent = new Intent(this, quizQuestion.class);
        intent.putExtra("points", new int[4]);
        intent.putExtra("pos", 1);
        intent.putExtra("completed", new int[4]);
        intent.putExtra("quizType", quizType);
        startActivity(intent);
    }
    public void newQuiz(View view){
        intent = new Intent(this, quizQuestion.class);
        intent.putExtra("points", new int[4]);
        intent.putExtra("pos", 1);
        intent.putExtra("completed", new int[4]);
        intent.putExtra("quizType", !quizType);
        startActivity(intent);
    }
    public void home(View view){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}