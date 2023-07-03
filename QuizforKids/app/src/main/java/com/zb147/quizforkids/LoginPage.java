package com.zb147.quizforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    Button submitButton, toggleButton;
    EditText usernameField, passwordField, emailField;
    DatabaseHelper myDB;
    Intent intent;
    TextView textView;
    Boolean registerMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        submitButton = (Button) findViewById(R.id.loginButton);
        toggleButton = (Button) findViewById(R.id.registerButton);

        usernameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.password);
        emailField = (EditText) findViewById(R.id.email);
        textView = (TextView) findViewById(R.id.textView);

        myDB = new DatabaseHelper(this,  null, 1);
        intent = new Intent(this, MainActivity.class);

        toggleButton.setText("Register");
        registerMode = false;
    }

    public void attemptLogin(View view){
        if (registerMode){
            if (myDB.login( usernameField.getText().toString() , passwordField.getText().toString(), emailField.getText().toString() )){
                ((CurrentUser) this.getApplication()).setUsername(usernameField.getText().toString());
                Toast.makeText(getApplicationContext(), "Logging In as " + ((CurrentUser) this.getApplication()).getUsername(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                ((CurrentUser) this.getApplication()).logout();
                Toast.makeText(getApplicationContext(), "Log in failed, please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (myDB.login( usernameField.getText().toString() , passwordField.getText().toString())){
                ((CurrentUser) this.getApplication()).setUsername(usernameField.getText().toString());
                Toast.makeText(getApplicationContext(), "Logging In as " + ((CurrentUser) this.getApplication()).getUsername(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                ((CurrentUser) this.getApplication()).logout();
                Toast.makeText(getApplicationContext(), "Log in failed, please try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void toggleRegister(View view){

        if (toggleButton.getText().toString() == "Register") {
            toggleButton.setText("Cancel");
            submitButton.setText("Register");
            emailField.setEnabled(true);
            emailField.setVisibility(View.VISIBLE);
            textView.setText("Register");
            registerMode = true;
        } else {
            toggleButton.setText("Register");
            submitButton.setText("Login");
            emailField.setText("");
            emailField.setEnabled(false);
            emailField.setVisibility(View.INVISIBLE);
            textView.setText("Login");
            registerMode = false;
        }

    }

}