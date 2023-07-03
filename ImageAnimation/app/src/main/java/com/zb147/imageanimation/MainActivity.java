package com.zb147.imageanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public ImageView mario;
    public Animation animation;
    public Button fade;
    public Button rotate;
    public Button scale;
    public Button move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fade = (Button) findViewById(R.id.fadeButton);
        rotate = (Button) findViewById(R.id.rotateButton);
        scale = (Button) findViewById(R.id.scaleButton);
        move = (Button) findViewById(R.id.moveButton);

        mario = (ImageView) findViewById(R.id.mario);
    }

    public void fade(View view){
        stopAllBut();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_fade);
        mario.startAnimation(animation);
    }

    public void rotate(View view){
        stopAllBut();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_flip);
        mario.startAnimation(animation);

    }

    public void scale(View view){
        stopAllBut();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_shrink);
        mario.startAnimation(animation);
    }

    public void move(View view){
        stopAllBut();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_swing1);
        mario.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation newanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_swing2);
                newanimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation lastAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mario_swing3);
                        mario.startAnimation(lastAnimation);
                    }
                });
                mario.startAnimation(newanimation);
            }
        });

    }

    public void stopAllBut(){
        fade.setEnabled(false);
        rotate.setEnabled(false);
        scale.setEnabled(false);
        move.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fade.setEnabled(true);
                rotate.setEnabled(true);
                scale.setEnabled(true);
                move.setEnabled(true);
            }
        }, 2000);
    }


}