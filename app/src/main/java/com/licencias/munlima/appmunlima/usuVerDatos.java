package com.licencias.munlima.appmunlima;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class usuVerDatos extends AppCompatActivity {

    String nom,tra,cod_tra,cod_per;
    ImageView estFotVerDatos;
    TextView txtEstCodDatos,txtEstNomDatos,txtEstApePatDatos,txtEstApeMatDatos,txtEstDniDatos,txtEstAsoDatos,txtEstAsoEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usu_ver_datos);

        estFotVerDatos= (ImageView) findViewById(R.id.estFotVerDatos);
        txtEstCodDatos= (TextView) findViewById(R.id.txtEstCodDatos);
        txtEstApePatDatos= (TextView) findViewById(R.id.txtEstApePatDatos);
        txtEstApeMatDatos= (TextView) findViewById(R.id.txtEstApeMatDatos);
        txtEstDniDatos= (TextView) findViewById(R.id.txtEstDniDatos);
        txtEstAsoDatos= (TextView) findViewById(R.id.txtEstAsoDatos);
        txtEstNomDatos= (TextView) findViewById(R.id.txtEstNomDatos);
        txtEstAsoEstado= (TextView) findViewById(R.id.txtEstAsoEstado);


        CargarSesion();
        Bundle recupera=getIntent().getExtras();
        String resultado = recupera.getString("codigo");
            try {
                resultado = decrypt(resultado);
                if(resultado.equals("0")){
                    resultado=null;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(resultado!=null) {
            SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd");
            String fe2c=fmt3.format(new Date());
            switch (cod_tra){
                case "T0001": SalidaAforo();
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
                case "T0002":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0003":
                    final String finalResultado = resultado;
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd");
                                            String fe2c=fmt3.format(new Date());
                                            verDisponibilidadDePase(finalResultado,fe2c);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            SalidaAforo();
                                            SimpleDateFormat fm3 = new SimpleDateFormat("yyyy-MM-dd");
                                            String fe2=fm3.format(new Date());
                                            cargarDatos(finalResultado);
                                            Penalidad(fe2, finalResultado);
                                            break;
                                    }
                                }
                            };

                                AlertDialog.Builder builder = new AlertDialog.Builder(usuVerDatos.this);
                                builder.setMessage("¿El estibador esta entrando o saliendo?").setPositiveButton("Entrando", dialogClickListener)
                                        .setNegativeButton("Saliendo", dialogClickListener).show();
                    break;
                case "T0004": SalidaAforo();
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
                case "T0005":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0006":SalidaAforo();
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
                case "T0011":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0013":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0015":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0016":SalidaAforo();
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
                case "T0017":SalidaAforo();
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
                case "T0018":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                case "T0021":
                    verDisponibilidadDePase(resultado,fe2c);
                    break;
                default:
                    cargarDatos(resultado);
                    Penalidad(fe2c,resultado);
                    break;
            }


        }else{
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fec=fmt.format(new Date());
            grabarMal(cod_per,cod_tra,fec,recupera.getString("codigo"));
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(usuVerDatos.this);
            builder.setMessage("Inspector "+nom+" el código escaneado en la "+tra+" no se encuentra registrado").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent a=new Intent(getApplicationContext(),usuPrincipal.class);
                    startActivity(a);
                    finish();
                }
            })
                    .setCancelable(false).show();

        }

    }





    private void cargarimagen(String url){
        Picasso.with(this).load(url).placeholder(R.drawable.cargando)
                .error(R.drawable.error_imagen)
                .into(estFotVerDatos,new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("nom_per","").toString();
        tra=Sesion.getString("nom_tra","").toString();
        cod_tra=Sesion.getString("cod_tra","").toString();
        cod_per=Sesion.getString("cod_per","").toString();
    }

    @Override
    public void onBackPressed() {
        Intent a=new Intent(getApplicationContext(),usuPrincipal.class);
        startActivity(a);
        finish();
    }





    public void cargarDatos(final String est){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/listarestibador.php?est="+est;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String fec=fmt.format(new Date());
                    grabarMal(cod_per,cod_tra,fec,est);
                }else {
                    try {
                        JSONArray json=new JSONArray(response);

                        Estivador a=new Estivador(json.getJSONObject(0).getString("cod_est"),json.getJSONObject(0).getString("nom_est"),
                                json.getJSONObject(0).getString("ape_pat_est"),json.getJSONObject(0).getString("ape_mat_est"),
                                json.getJSONObject(0).getString("dni_est"), json.getJSONObject(0).getString("nom_aso"),json.getJSONObject(0).getString("fot_est"),json.getJSONObject(0).getString("cod_aso"));

                        String url="http://dacepsacgde.com/appMunLimaImg/";


                        txtEstCodDatos.setText(a.getCod_est().toString());
                        txtEstNomDatos.setText(a.getNom_est().toString());
                        txtEstApePatDatos.setText(a.getApe_pat_est().toString());
                        txtEstApeMatDatos.setText(a.getApe_mat_est().toString());
                        txtEstDniDatos.setText(a.getDni_est().toString());
                        txtEstAsoDatos.setText(a.getNom_aso().toString());
                        cargarimagen(url+a.getFot_est().toString());

                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String fec=fmt.format(new Date());
                        grabarCorrecto(cod_per,cod_tra,a.getCod_est().toString(),a.getCod_aso().toString(),fec);


                    }catch (JSONException e){

                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(usuVerDatos.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }


    public void grabarCorrecto(String cod_per,String cod_tra, String cod_est,String cod_aso,String fec_rep){
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://dacepsacgde.com/appMunLima/insertCorrecto2.php?cod_per="+cod_per+"&cod_tra="+cod_tra+"&cod_est="+cod_est+"&cod_aso="+cod_aso+"&fec_rep="+fec_rep;
        URL=URL.replace(" ","%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                }else{
                    Toast.makeText(getApplicationContext(), "Error en la red", Toast.LENGTH_SHORT).show();
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

    public void grabarMal(String cod_per,String cod_tra, String fec_rep,String lin_rem){
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://dacepsacgde.com/appMunLima/insertMal.php?cod_per="+cod_per+"&cod_tra="+cod_tra+"&fec_rep="+fec_rep+"&lin_rem="+lin_rem;
        URL=URL.replace(" ","%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                }else{
                    Toast.makeText(getApplicationContext(), "Error en la red", Toast.LENGTH_SHORT).show();
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

    private String decrypt(String s) throws Exception{
        SecretKeySpec key=generarLlave("AES");
        Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        String textofinal="";
        try {
            byte[] desencriptar= Base64.decode(s,Base64.DEFAULT);
            byte[] decValue=c.doFinal(desencriptar);
            textofinal=new String(decValue);
        }catch (Exception e){
            textofinal="0";
        }

        return textofinal;
    }
    private SecretKeySpec generarLlave(String sl) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes=sl.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secret= new SecretKeySpec(key,"AES");
        return secret;
    }

    public void Penalidad(String fecha, final String est){
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://dacepsacgde.com/appMunLima/consultarpenalidad.php?fech="+fecha+"&est="+est;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("[]")) {
                    if(String.valueOf(est.charAt(0)).toUpperCase().equals("X")){
                        HoraFinalDeCierre();
                    }else {
                        txtEstAsoEstado.setText("HABILITADO");
                        txtEstAsoEstado.setTextColor(Color.rgb(25, 127, 16));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "El Estivador se encuentra Sancionado", Toast.LENGTH_SHORT).show();
                    txtEstAsoEstado.setText("SANCIONADO");
                    txtEstAsoEstado.setTextColor(Color.RED);

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

    public void HoraFinalDeCierre(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd");
        String fe2c=fmt3.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/verHoraCierreTranquera.php?fecha="+fe2c;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("[]")) {
                    final Calendar c= Calendar.getInstance();
                    int rhora=c.get(Calendar.HOUR_OF_DAY);
                    int rminutos=c.get(Calendar.MINUTE);
                    int ractual=(rhora*60)+rminutos;

                    int standar=11*60;

                    if(ractual>standar){
                        txtEstAsoEstado.setText("FUERA DE LA HORA DE PROVEEDORES");
                        txtEstAsoEstado.setTextColor(Color.RED);
                    }else{
                        txtEstAsoEstado.setText("HABILITADO");
                        txtEstAsoEstado.setTextColor(Color.rgb(25, 127, 16));
                    }

                }else{
                    try {
                        JSONArray json = new JSONArray(response);

                        final Calendar c= Calendar.getInstance();
                        int rhora=c.get(Calendar.HOUR_OF_DAY);
                        int rminutos=c.get(Calendar.MINUTE);
                        int ractual=(rhora*60)+rminutos;

                        int hora=json.getJSONObject(0).getInt("hora_maxima");
                        int minuto=json.getJSONObject(0).getInt("min_maxima");
                        int limite=(hora*60)+minuto;

                        if(ractual>limite){
                            txtEstAsoEstado.setText("FUERA DE LA HORA DE PROVEEDORES");
                            txtEstAsoEstado.setTextColor(Color.RED);
                        }else{
                            txtEstAsoEstado.setText("HABILITADO");
                            txtEstAsoEstado.setTextColor(Color.rgb(25, 127, 16));
                        }




                    }catch (Exception e){

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

    public void EntradaAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/aumentarAforo.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                }else{
                    Toast.makeText(getApplicationContext(), "Problema en el tiempo de respuesta", Toast.LENGTH_SHORT).show();
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

    public void SalidaAforo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=fmt.format(new Date());
        String URL = "http://dacepsacgde.com/appMunLima/restarAforo.php?fecha="+fecha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                }else{
                    Toast.makeText(getApplicationContext(), "Problema en el tiempo de respuesta", Toast.LENGTH_SHORT).show();
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
    public void verDisponibilidadDePase(final String resultado,final String fe2c){
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
                        EntradaAforo();
                        cargarDatos(resultado);
                        Penalidad(fe2c, resultado);
                    }else{
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Intent a=new Intent(getApplicationContext(),usuPrincipal.class);
                                        startActivity(a);
                                        finish();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(usuVerDatos.this);
                        builder.setMessage("Los Administradores cerraron las tranqueras de entrada").setPositiveButton("Aceptar", dialogClickListener)
                                .setCancelable(false).show();
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

