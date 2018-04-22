package com.licencias.munlima.appmunlima;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class verAforoAdm extends AppCompatActivity {

    TextView aforo;
    Button Activar,Salir,Bloquear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ver_aforo_adm);
        aforo= (TextView) findViewById(R.id.txtAforo);
        Activar= (Button) findViewById(R.id.btnActivarPaso);
        Bloquear= (Button) findViewById(R.id.btnBloquearPaso);
        Salir= (Button) findViewById(R.id.btnAforoSalir);
        leerAforo();
        verStadoAforo();



        Activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAforo();
            }
        });

        Bloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloqueoAforo();
            }
        });

        Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), adminPrincipal.class);
                startActivity(a);
                finish();
            }
        });


    }

    public void leerAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/verAforoADM.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("[]")) {
                    aforo.setText("0");
                }else{
                    try {
                        JSONArray json=new JSONArray(response);
                        aforo.setText(json.getJSONObject(0).getString("aforo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la red",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void bloqueoAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/bloquearAforo.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"Aforo Bloqueado",Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(getApplicationContext(), adminPrincipal.class);
                    startActivity(a);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Problema al Bloquear el aforo",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la red",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void abrirAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/abrirAforo.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"Aforo Abierto",Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(getApplicationContext(), adminPrincipal.class);
                    startActivity(a);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Problema al Bloquear el aforo",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la red",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void verStadoAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/verEstadoDePasoAforo.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json=new JSONArray(response);
                    int vidar=json.getJSONObject(0).getInt("permiso");
                    if(vidar==0){
                        Activar.setEnabled(false);
                        Activar.setBackgroundColor(Color.rgb(100, 100, 100));
                    }else{
                        Bloquear.setEnabled(false);
                        Bloquear.setBackgroundColor(Color.rgb(100, 100, 100));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la red",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
