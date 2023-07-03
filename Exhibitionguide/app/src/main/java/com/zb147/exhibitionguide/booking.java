package com.zb147.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class booking extends AppCompatActivity {

    private Spinner daySpinner;
    private Spinner timeSpinner;
    private static final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String[] times = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
    private static final String[] visTimes = {"15:00", "17:00", "19:00"};
    private ImageView gallaryLogo;
    private TextView gallaryName, totalView, endTime;
    private EditText quan;
    private Button calcButton;
    private Button tipButton;
    private int choice, opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Intent myIntent = getIntent();

        gallaryLogo = (ImageView) findViewById(R.id.gallaryLogo);
        gallaryName = (TextView) findViewById(R.id.gallaryName);
        totalView = (TextView) findViewById(R.id.total);
        endTime = (TextView) findViewById(R.id.endTime);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        quan = (EditText) findViewById(R.id.quanNumber);
        calcButton = (Button) findViewById(R.id.calcButton);
        tipButton = (Button) findViewById(R.id.tipButton);

        Calendar cal = Calendar.getInstance();

        choice = myIntent.getIntExtra("choice", 0);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(cal.get(Calendar.DAY_OF_WEEK) - 1);

        ArrayAdapter<String> timeAdapter;
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (choice == 1){
            //visual
            timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visTimes);

            if (hour < 15) {
                opt = 0;
            } else if (hour < 17){
                opt = 1;
            } else if (hour < 19){
                opt = 2;
            } else {
                opt = 0;

            }

        } else {
            if (hour > 9 && (hour < 22 && cal.get(Calendar.MINUTE) < 30)) {
                opt = (cal.get(Calendar.HOUR_OF_DAY) - 9) * 2;
                if (cal.get(Calendar.MINUTE) < 30){
                    opt ++;
                } else {
                    opt += 2;
                }
            } else {
                opt = 0;
            }

            timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        }


        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setSelection(opt);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (choice == 1) {
                    String selected = visTimes[timeSpinner.getSelectedItemPosition()];
                    int selectedHour = Integer.valueOf(selected.substring(0,2));
                    endTime.setText(String.valueOf(selectedHour+2) + ":00");
                } else {
                    String selected = times[timeSpinner.getSelectedItemPosition()];
                    int selectedHour = Integer.valueOf(selected.substring(0,2));
                    int selectedMin = Integer.valueOf(selected.substring(3,4));
                    if (selectedMin == 3){
                        endTime.setText(String.valueOf(selectedHour+2) + ":00");
                    } else {
                        endTime.setText(String.valueOf(selectedHour+1) + ":30");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (choice == 0){
            //art
            gallaryLogo.setImageResource(R.drawable.art);
            gallaryName.setText("ART GALLARY");
        } else if (choice == 1) {
            //visual
            gallaryLogo.setImageResource(R.drawable.visual);
            gallaryName.setText("VISUAL SHOW");
        } else if (choice == 2){
            //war
            gallaryLogo.setImageResource(R.drawable.war);
            gallaryName.setText("WORLD WAR ONE");
        } else if (choice == 3){
            //space
            gallaryLogo.setImageResource(R.drawable.space);
            gallaryName.setText("EXPLORE the SPACE");
        }

    }

    public void showTip(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Select the day, time and amount of visitors then press 'Check Total'.", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void updateTotal(View view){

        double totalValue = 0;

        int quantity = Integer.valueOf(quan.getText().toString());

        if (choice == 0){
            //art
            totalValue = 25 * quantity;

        }
        if (choice == 1){
            //visual
            totalValue = 40 * quantity;
        }
        if (choice == 2){
            //war
            totalValue = 20 * quantity;

        }
        if (choice == 3){
            //space
            totalValue = 30 * quantity;
        }

        if (choice != 1 && (daySpinner.getSelectedItemPosition() == 0 || daySpinner.getSelectedItemPosition() == 6 )){
            totalValue += (5 * quantity);
        }
        if (quantity >= 4){
            totalValue = (totalValue * 0.9);
        }

        totalView.setText("$" + String.format("%.2f", totalValue));
    }
}