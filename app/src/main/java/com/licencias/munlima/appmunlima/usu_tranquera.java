package com.licencias.munlima.appmunlima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class usu_tranquera extends AppCompatActivity {

    Spinner spinner;
    String nom;
    TextView usu;
    List<String> datos;
    Button btnAceptarTranquera;
    String cod_tra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usu_tranquera);

        CargarSesion();

        usu= (TextView) findViewById(R.id.txtUsuarioTranquera);
        usu.setText(nom);

        spinner = (Spinner) findViewById(R.id.spinnerTranquera);
        btnAceptarTranquera= (Button) findViewById(R.id.btnAceptarTranquera);

        llenarSpinner();

        btnAceptarTranquera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seleccionarTranquera(spinner.getSelectedItem().toString());

            }
        });

    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("nom_per","").toString();
    }


    public void GrabarSesion(String a,String b){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Sesion.edit();
        editor.putString("cod_tra",a);
        editor.putString("nom_tra",b);
        editor.commit();
    }


    public void llenarSpinner(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/tranquera.php";
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                   Toast.makeText(usu_tranquera.this,"Error en la BD",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONArray jr=new JSONArray(response);

                        ArrayList<Tranquera>listar = new ArrayList<>();
                        List<String> lista=new ArrayList<>();
                        for(int i=0;i<jr.length();i++){
                           Tranquera a=new Tranquera(jr.getJSONObject(i).getString("cod_tra"),jr.getJSONObject(i).getString("nom_tra"));
                            lista.add(jr.getJSONObject(i).getString("nom_tra"));
                            listar.add(a);
                        }
                        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item_modificado,lista);
                        spinner.setAdapter(adapter);

                    }catch (JSONException e){

                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Toast.makeText(usu_tranquera.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);
    }

    public void seleccionarTranquera(String tra){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/trancode.php?tra="+tra;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    Toast.makeText(usu_tranquera.this,"Error en la BD",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONArray jr=new JSONArray(response);
                        GrabarSesion(jr.getJSONObject(0).getString("cod_tra"),jr.getJSONObject(0).getString("nom_tra"));
                        Intent a=new Intent(getApplicationContext(),usuPrincipal.class);
                        startActivity(a);
                        finish();
                    }catch (JSONException e){

                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(usu_tranquera.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
