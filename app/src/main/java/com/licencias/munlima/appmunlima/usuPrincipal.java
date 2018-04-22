package com.licencias.munlima.appmunlima;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class usuPrincipal extends AppCompatActivity {

    String nom,tra;
    TextView txtUsuarioPrincipal,txtTranqueraPrincipal;
    FloatingActionButton cerrar,tranquera,usuFalta;
    Button bntUsuPrincipalScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usu_principal);

        cerrar= (FloatingActionButton) findViewById(R.id.usuCerrarSesionPrincipal);
        txtUsuarioPrincipal= (TextView) findViewById(R.id.txtUsuarioPrincipal);
        txtTranqueraPrincipal= (TextView) findViewById(R.id.txtTranqueraPrincipal);
        tranquera= (FloatingActionButton) findViewById(R.id.usuCambiarTranquera);
        usuFalta= (FloatingActionButton) findViewById(R.id.usuFaltaEstiva);
        CargarSesion();
        txtUsuarioPrincipal.setText(nom);
        txtTranqueraPrincipal.setText(tra);

          if(tra.equals("")){
            Intent a=new Intent(getApplicationContext(),usu_tranquera.class);
            startActivity(a);
            finish();
        }

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                CerrarSesion();
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(usuPrincipal.this);
                builder.setMessage("¿Seguro que quiere cerrar sesión?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        tranquera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                CambiarTranquera();
                                Intent a=new Intent(getApplicationContext(),usu_tranquera.class);
                                startActivity(a);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(usuPrincipal.this);
                builder.setMessage("¿Seguro que quiere cambiar su tranquera?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        usuFalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent a=new Intent(getApplicationContext(),usuSelectorFalta.class);
                                startActivity(a);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(usuPrincipal.this);
                builder.setMessage("¿Va solicitar sancionar a un estibador?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("nom_per","").toString();
        tra=Sesion.getString("nom_tra","").toString();
    }

    public void CerrarSesion(){
        SharedPreferences Sesion =getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Sesion.edit();
        editor.remove("cod_per");
        editor.remove("use_per");
        editor.remove("nom_per");
        editor.remove("ape_pat_per");
        editor.remove("ape_mat_per");
        editor.remove("tip_per");
        editor.remove("cod_tra");
        editor.remove("nom_tra");
        editor.apply();
    }

    public void CambiarTranquera(){
        SharedPreferences Sesion =getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Sesion.edit();
        editor.remove("cod_tra");
        editor.remove("nom_tra");
        editor.apply();
    }


    public void Escaner(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Se canceló el escaneo", Toast.LENGTH_LONG).show();
            } else {

                final String codigo = result.getContents().toString();
                Intent a = new Intent(getApplicationContext(),usuVerDatos.class);
                a.putExtra("codigo",codigo);
                startActivity(a);
                finish();
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
        }


}

