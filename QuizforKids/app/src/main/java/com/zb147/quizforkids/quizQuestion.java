package com.zb147.quizforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class quizQuestion extends AppCompatActivity {

  public boolean quizType;
    public Button aButt, bButt, cButt, sButt;
    public EditText userAnswer;
    public ImageView quizImage;
    public TextView quizQuestion, questionNumber;
    public Intent intent;
    private DatabaseHelper myDB;

    public int completed[], points[], pos, qnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        quizType = getIntent().getBooleanExtra("quizType", false);

        aButt = (Button) findViewById(R.id.buttonA);
        bButt = (Button) findViewById(R.id.buttonB);
        cButt = (Button) findViewById(R.id.buttonC);
        sButt = (Button) findViewById(R.id.submit);
        userAnswer = (EditText) findViewById(R.id.userAnswer);
        quizQuestion = (TextView) findViewById(R.id.questionText);
        questionNumber = (TextView) findViewById(R.id.questionNumber);
        quizImage = (ImageView) findViewById(R.id.questionImage);

        myDB = new DatabaseHelper(this,  null, 1);

        points = getIntent().getIntArrayExtra("points");
        pos = getIntent().getIntExtra("pos", 1);
        completed = getIntent().getIntArrayExtra("completed");

        if(pos == 5){
            //if all questions are done, proceed
            intent = new Intent(this, results.class);
            intent.putExtra("results", points);
            intent.putExtra("quizType", quizType);
            startActivity(intent);
        } else {
            questionNumber.setText("Q" + pos);

            // false = animal TEXT, true = cartoon BUTTONS
            if (quizType){
                //cartoon

                aButt.setEnabled(true);
                aButt.setVisibility(View.VISIBLE);
                bButt.setEnabled(true);
                bButt.setVisibility(View.VISIBLE);
                cButt.setEnabled(true);
                cButt.setVisibility(View.VISIBLE);
                getCQuestion();
                aButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitAnswer(1);
                    }
                });
                bButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitAnswer(2);
                    }
                });
                cButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitAnswer(3);
                    }
                });

            } else {
                //animal
                quizImage.setVisibility(View.VISIBLE);
                sButt.setEnabled(true);
                sButt.setVisibility(View.VISIBLE);
                userAnswer.setEnabled(true);
                userAnswer.setVisibility(View.VISIBLE);
                getAQuestion();
                sButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitAnswer(userAnswer.getText().toString());
                    }
                });

            }
        }

    }


    public void getAQuestion(){
        if (completed[pos-1] != 0){
            qnum = completed[pos-1];
        } else {
            qnum = 1;
            boolean pass = false;
            while (pass == false){
                pass = true;
                qnum = new Random().nextInt(10) +1;
                for (int y = 0; y < 3; y++){
                    if (completed[y] == qnum){
                        pass = false;
                    }
                }
            }
        }

        completed[pos-1] = qnum;
        quizQuestion.setText(myDB.getQuestion(false, qnum));
        quizImage.setImageResource(getResources().getIdentifier(myDB.getImage(qnum), "drawable", getPackageName()));
        quizImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade));
        quizQuestion.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin));

    }

    public void getCQuestion(){
        if (completed[pos-1] != 0){
            qnum = completed[pos-1];
        } else {
            qnum = 1;
            boolean pass = false;
            while (pass == false){
                pass = true;
                qnum = new Random().nextInt(10) +1;
                for (int y = 0; y < 3; y++){
                    if (completed[y] == qnum){
                        pass = false;
                    }
                }
            }
        }
        completed[pos-1] = qnum;
        quizQuestion.setText(myDB.getQuestion(true, qnum));
        quizQuestion.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin));
    }

    public void submitAnswer(String answer){
        if (myDB.getAnswer(false, qnum).equals(answer)){
            points[pos-1] = 3;
        } else {
            points[pos-1] = -1;
        }
        proceed();
    }
    public void submitAnswer(int answer){
        if (answer == Integer.parseInt(myDB.getAnswer(true, qnum))){
            points[pos-1] = 3;
        } else {
            points[pos-1] = -1;
        }
        proceed();
    }

    public void proceed(){
        intent = new Intent(this, quizQuestion.class);
        intent.putExtra("quizType", quizType);
        intent.putExtra("points", points);
        intent.putExtra("pos", ++pos);
        intent.putExtra("completed", completed);
        startActivity(intent);
    }

    public void previousQuestion(View view){
        if (pos - 1 > 0){
            intent = new Intent(this, quizQuestion.class);
            intent.putExtra("quizType", quizType);
            intent.putExtra("points", points);
            intent.putExtra("pos", --pos);
            intent.putExtra("completed", completed);
            startActivity(intent);
        }
    }
    public void nextQuestion(View view){
        if (points[pos-1] == 0){ points[pos-1] = -1;};
        intent = new Intent(this, quizQuestion.class);
        intent.putExtra("quizType", quizType);
        intent.putExtra("points", points);
        intent.putExtra("pos", ++pos);
        intent.putExtra("completed", completed);
        startActivity(intent);
    }
}