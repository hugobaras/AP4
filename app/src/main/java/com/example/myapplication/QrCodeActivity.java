package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrCodeActivity extends MainActivity {

    Button scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_activity);
        scan = findViewById(R.id.btn_scan);
        scan.setOnClickListener(v->
        {
            scanCode();


        });
    }
    protected void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume haut pour scanner");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->{
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(QrCodeActivity.this);
            builder.setTitle("Resultat");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    dialog.dismiss();
                }
            }).show();
        }
    });
}
