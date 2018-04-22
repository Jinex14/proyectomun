package com.licencias.munlima.appmunlima;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String cod,nom,tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                        CargarSesion();
                    if(tipo!=""){
                        int tip=Integer.parseInt(tipo);
                        if(tip==1){
                            Intent intent = new Intent(getApplicationContext(), adminPrincipal.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), usuPrincipal.class);
                            startActivity(intent);
                        }
                    }else {
                            Intent intent = new Intent(getApplicationContext(), login.class);
                            startActivity(intent);
                        }
                        finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        cod=Sesion.getString("cod_per","").toString();
        nom=Sesion.getString("nom_per","").toString();
        tipo=Sesion.getString("tip_per","").toString();
    }

}
