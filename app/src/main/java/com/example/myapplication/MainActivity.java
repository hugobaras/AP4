package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button connect;
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.connect);
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        connect.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final String login_entry = login.getText().toString();
                final String pwd_entry = password.getText().toString();

                if (login_entry.equals("test") && pwd_entry.equals("pwd")) {

                    Intent Home = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(Home);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
