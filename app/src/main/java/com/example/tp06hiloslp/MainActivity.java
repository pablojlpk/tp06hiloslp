package com.example.tp06hiloslp;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
establecerPermiso();
        intent = new Intent(this, MiServicioContent.class);

    }
    public void iniciar(View view){

        this.startService(intent);
    }
    public void detener(View view){

        this.stopService(intent);
    }

    public void establecerPermiso() {
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }
  }
}
