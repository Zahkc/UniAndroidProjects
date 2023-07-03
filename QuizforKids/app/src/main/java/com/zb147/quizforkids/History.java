package com.zb147.quizforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class History extends AppCompatActivity {

    boolean sort = true;
    String[] records;
    DatabaseHelper myDB;
    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList= (ListView) findViewById(R.id.historyList);
        myDB = new DatabaseHelper(this,  null, 1);

        records = myDB.getRecords(((CurrentUser) this.getApplication()).getUsername(), sort);
        ((CurrentUser) this.getApplication()).updateUserPoints();
        TextView splashText = (TextView) findViewById(R.id.splashScore);
        splashText.setText("Hi "+((CurrentUser) this.getApplication()).getUsername()+",\nYou have earned "+((CurrentUser) this.getApplication()).getUserPoints()+" points in the following attempts");

        ArrayAdapter<String> previousAttempts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records);

        historyList.setAdapter(previousAttempts);
    }

    public void toggleSort(View view){
        sort = !sort;
        records = myDB.getRecords(((CurrentUser) this.getApplication()).getUsername(), sort);
        ArrayAdapter<String> previousAttempts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records);
        historyList.setAdapter(previousAttempts);

        Button sortButton = (Button) findViewById(R.id.sortButton);
        if (sort){
            sortButton.setText("Sort: Date");
        } else {
            sortButton.setText("Sort: Quiz");
        }

    }
}