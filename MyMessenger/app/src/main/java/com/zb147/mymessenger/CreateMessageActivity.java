package com.zb147.mymessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    EditText messageView;
    String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button viewButton = (Button) findViewById(R.id.viewButton);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        messageView = (EditText) findViewById(R.id.message);

        viewButton.setOnClickListener(clickListener);
        sendButton.setOnClickListener(clickListener);

    }

    private final View.OnClickListener clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.viewButton:
                            messageText = messageView.getText().toString();
                            Intent intent = new Intent(CreateMessageActivity.this, ViewMessageActivity.class);
                            intent.putExtra(ViewMessageActivity.EXTRA_MESSAGE, messageText);

                            startActivity(intent);

                            break;
                        case R.id.sendButton:
                            messageText = messageView.getText().toString();
                            Intent intentA = new Intent(Intent.ACTION_SEND);
                            intentA.setType("text/plain");
                            intentA.putExtra(Intent.EXTRA_TEXT, messageText);
                            String chooserTitle = "SEND BY...";
                            Intent chosenIntent = Intent.createChooser(intentA, chooserTitle);
                            startActivity(chosenIntent);
                            break;
                    }
                }
            };
}