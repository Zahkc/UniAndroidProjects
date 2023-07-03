package com.zb147.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton artButton;
    private ImageButton visualButton;
    private ImageButton warButton;
    private ImageButton spaceButton;
    private Button tipButton;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, booking.class);

        artButton = (ImageButton) findViewById(R.id.artButton);
        visualButton = (ImageButton) findViewById(R.id.visualButton);
        warButton = (ImageButton) findViewById(R.id.warButton);
        spaceButton = (ImageButton) findViewById(R.id.spaceButton);
        tipButton = (Button) findViewById(R.id.tipButton);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showTip(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Select a exhibit by clicking on its picture.", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void startArt(View view){
        intent.putExtra("choice", 0);
        startActivity(intent);
    }
    public void startVisual(View view){
        intent.putExtra("choice", 1);
        startActivity(intent);
    }
    public void startWar(View view){
        intent.putExtra("choice", 2);
        startActivity(intent);
    }
    public void startSpace(View view){
        intent.putExtra("choice", 3);
        startActivity(intent);
    }
}