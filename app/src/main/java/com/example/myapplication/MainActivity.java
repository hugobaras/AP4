package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button connect;
    private EditText login;
    private EditText password;
    private RequestQueue Queue;

    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = findViewById(R.id.connect);
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);

        Queue = Volley.newRequestQueue(this);

        connect.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                final String login_entry = login.getText().toString();
                final String pwd_entry = password.getText().toString();

                String url = "http://172.16.106.19/SLAM/AP3/AP3/API/connexion.php?login=" + login_entry + "&mdp=" + pwd_entry;

               JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {

                                mail = response.getString("cl_email");
                                Log.v("Test", mail);
                                if(mail != "false") {
                                    Intent Home= new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(Home);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, Throwable::printStackTrace){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("CUSTOM_HEADER", "Yahoo");
                        headers.put("ANOTHER_CUSTOM_HEADER", "Google");
                        return headers;
                    }
                };
                Queue.add(request);


            }

        });


    }


}
