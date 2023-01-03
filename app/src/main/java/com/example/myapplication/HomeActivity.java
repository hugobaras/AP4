package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends MainActivity {

    TextView result;
    EditText article;
    String ArticleTest;
    private ActivityResultLauncher<ScanOptions> barLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button buttonTest = findViewById(R.id.buttonTest);
        FloatingActionButton scan = findViewById(R.id.scan);
        scan.setOnClickListener(v ->
                scanCode());
        barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                ArticleTest = result.getContents();
                Log.v("Test", ArticleTest);
                getRequest(ArticleTest);

            }
        });
    }

    protected void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    private void getRequest(String ArticleTest) {
        article = findViewById(R.id.editTextArticle);
        String idArticle = article.getText().toString();
        result = findViewById(R.id.textTest);
        // Créer une file d'attente de requêtes Volley
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

// Créer la requête JSON
        VolleyLog.DEBUG = true;
       String url = "http://172.16.106.19/SLAM/AP3/AP3/API/getArticle.php?id=" + ArticleTest + "";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Récupérer les données de la réponse
                            String nom = response.getString("pr_nom");
                            String description = response.getString("pr_description");
                            int stock = response.getInt("pr_stockInternet");
                            String lieu = response.getString("ma_lieu");

                            result.append(nom+ " " + description + " " + stock + " " + lieu + "\n");
                            Log.v("nouveau test", nom+ " " + description + " " + stock + " " + lieu + "\n");
                            // Traiter les données
                            // ...
                        } catch (JSONException e) {
                            Log.v("Erreur", "erreur test");
                            // Gérer les erreurs d'analyse de la réponse JSON
                            // ...
                        }
                    }
                },
                error -> {
                    // Gérer les erreurs
                    Log.v("Erreur2", "Error Response");
                    // ...
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("CUSTOM_HEADER", "Yahoo");
                headers.put("ANOTHER_CUSTOM_HEADER", "Google");
                return headers;
            }
        };

// Ajouter la requête à la file d'attente
        queue.add(request);

    }

}
