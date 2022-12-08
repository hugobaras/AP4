package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends MainActivity {

    private FloatingActionButton scan;
    private TextView myResult;
    private Button buttonTest;
    private RequestQueue Queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Queue = Volley.newRequestQueue(this);
        myResult = findViewById(R.id.textTest);
        buttonTest = findViewById(R.id.buttonTest);

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(v ->
        {
            scanCode();
        });
        buttonTest.setOnClickListener(view -> {
            getRequest();
        });
    }

    protected void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume haut pour scanner");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
        AlertDialog.Builder buil = new AlertDialog.Builder(getApplicationContext());
        buil.setTitle("Quitter");
        buil.setNegativeButton("Ok", (dialog, i) -> dialog.dismiss()).show();
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Resultat");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Ok", (dialog, i) -> dialog.dismiss()).show();
        }
    });

    public void getJSON() {
        getRequest();
    }

    private void getRequest() {
        String url = "https://raw.githubusercontent.com/Jaeger47/UNITY_JSON/main/student_data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("students");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject student = jsonArray.getJSONObject(i);

                                String myName = student.getString("name");
                                int myAge = student.getInt("age");
                                String myEmail = student.getString("email");
                                Boolean myIsEnrolled = student.getBoolean("isEnrolled");


                                myResult.append(myName + ", " + String.valueOf(myAge) + ", " + myEmail + ", " + myIsEnrolled + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Queue.add(request);
    }

}
