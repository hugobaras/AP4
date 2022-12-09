package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends MainActivity {

    private TextView myResult;
    private RequestQueue Queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Queue = Volley.newRequestQueue(this);
        myResult = findViewById(R.id.textTest);
        Button buttonTest = findViewById(R.id.buttonTest);

        FloatingActionButton scan = findViewById(R.id.scan);
        scan.setOnClickListener(v ->
                scanCode());
        buttonTest.setOnClickListener(view -> getRequest());
    }

    protected void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
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
        String url = "https://raw.githubusercontent.com/hugobaras/AP4/master/test.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("articles");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject articles = jsonArray.getJSONObject(i);

                            String nom = articles.getString("nom");
                            int id = articles.getInt("id");
                            String image = articles.getString("image");
                            String prix = articles.getString("prix");


                            myResult.append(nom + ", " + id + ", " + image + ", " + prix + "\n\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        Queue.add(request);
    }

}
