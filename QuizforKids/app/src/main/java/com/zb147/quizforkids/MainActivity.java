package com.zb147.quizforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView splash;
    Intent intent;
    ImageButton animalQuiz, cartoonQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash = (TextView) findViewById(R.id.splash);
        ((CurrentUser) this.getApplication()).updateUserPoints();
        splash.setText("Welcome " + ((CurrentUser) this.getApplication()).getUsername() + ". \nYou have " + ((CurrentUser) this.getApplication()).getUserPoints() + " points.");

        intent = new Intent(this, quizQuestion.class);
        intent.putExtra("points", new int[4]);
        intent.putExtra("pos", 1);
        intent.putExtra("completed", new int[4]);

        animalQuiz = (ImageButton) findViewById(R.id.animalButton);
        animalQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("quizType", false);
                startActivity(intent);
            }
        });

        cartoonQuiz = (ImageButton) findViewById(R.id.cartoonButton);
        cartoonQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("quizType", true);
                startActivity(intent);
            }
        });
    }

    public void logout(View view){
        Toast.makeText(this, ((CurrentUser) this.getApplication()).getUsername()+", you have overall "+((CurrentUser) this.getApplication()).getUserPoints() + " points.", Toast.LENGTH_SHORT).show();
        ((CurrentUser) this.getApplication()).logout();
        intent = new Intent(this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void history(View view){
        startActivity(new Intent(this, History.class));
    }

    public void tips(View view){
        startActivity(new Intent(this, HowToPlay.class));
    }

}