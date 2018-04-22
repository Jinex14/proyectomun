package com.licencias.munlima.appmunlima;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;


public class reporteTranqueras extends AppCompatActivity{

    Button calendario,btnSalirReporte;
    private int mYear;
    private int mMonth;
    private int mDay,sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID=0;
    TextView tran01,tran02,tran03,tran04,tran05,tran06,tran07,tran08,tran09,tran10,tran11,tran12,tran13,tran14,tran15,tran16,tran17,tran18,tran19,tran20,tran21,tran22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reporte_tranqueras);
        calendario= (Button) findViewById(R.id.btnFechaReporte);
        btnSalirReporte= (Button) findViewById(R.id.btnSalirReporte);


        tran01= (TextView) findViewById(R.id.tran01);
        tran02= (TextView) findViewById(R.id.tran02);
        tran03= (TextView) findViewById(R.id.tran03);
        tran04= (TextView) findViewById(R.id.tran04);
        tran05= (TextView) findViewById(R.id.tran05);
        tran06= (TextView) findViewById(R.id.tran06);
        tran07= (TextView) findViewById(R.id.tran07);
        tran08= (TextView) findViewById(R.id.tran08);
        tran09= (TextView) findViewById(R.id.tran09);
        tran10= (TextView) findViewById(R.id.tran10);
        tran11= (TextView) findViewById(R.id.tran11);
        tran12= (TextView) findViewById(R.id.tran12);
        tran13= (TextView) findViewById(R.id.tran13);
        tran14= (TextView) findViewById(R.id.tran14);
        tran15= (TextView) findViewById(R.id.tran15);
        tran16= (TextView) findViewById(R.id.tran16);
        tran17= (TextView) findViewById(R.id.tran17);
        tran18= (TextView) findViewById(R.id.tran18);
        tran19= (TextView) findViewById(R.id.tran19);
        tran20= (TextView) findViewById(R.id.tran20);
        tran21= (TextView) findViewById(R.id.tran21);
        tran22= (TextView) findViewById(R.id.tran22);


        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final Calendar as=Calendar.getInstance();
                sDayIni=as.get(Calendar.DAY_OF_MONTH);
                sMonthIni=as.get(Calendar.MONTH);
                sYearIni=as.get(Calendar.YEAR);

                showDialog(DATE_ID);
            }
        });

        btnSalirReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a =new Intent(getApplicationContext(),adminPrincipal.class);
                startActivity(a);
                finish();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    cargarDatos(mYear+"-"+(mMonth+1)+"-"+mDay);
                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DATE_ID:
              return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }


    public void cargarDatos(String fecha){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/cargaReporte.php?fecha="+fecha;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                      try {
                        JSONArray json=new JSONArray(response);

                          tran01.setText("   "+json.getJSONObject(0).getString("tranquera01"));
                          tran02.setText("   "+json.getJSONObject(0).getString("tranquera02"));
                          tran03.setText("   "+json.getJSONObject(0).getString("tranquera03"));
                          tran04.setText("   "+json.getJSONObject(0).getString("tranquera04"));
                          tran05.setText("   "+json.getJSONObject(0).getString("tranquera05"));
                          tran06.setText("   "+json.getJSONObject(0).getString("tranquera06"));
                          tran07.setText("   "+json.getJSONObject(0).getString("tranquera07"));
                          tran08.setText("   "+json.getJSONObject(0).getString("tranquera08"));
                          tran09.setText("   "+json.getJSONObject(0).getString("tranquera09"));
                          tran10.setText("   "+json.getJSONObject(0).getString("tranquera10"));
                          tran11.setText("   "+json.getJSONObject(0).getString("tranquera11"));
                          tran12.setText("   "+json.getJSONObject(0).getString("tranquera12"));
                          tran13.setText("   "+json.getJSONObject(0).getString("tranquera13"));
                          tran14.setText("   "+json.getJSONObject(0).getString("tranquera14"));
                          tran15.setText("   "+json.getJSONObject(0).getString("tranquera15"));
                          tran16.setText("   "+json.getJSONObject(0).getString("tranquera16"));
                          tran17.setText("   "+json.getJSONObject(0).getString("tranquera17"));
                          tran18.setText("   "+json.getJSONObject(0).getString("tranquera18"));
                          tran19.setText("   "+json.getJSONObject(0).getString("tranquera19"));
                          tran20.setText("   "+json.getJSONObject(0).getString("tranquera20"));
                          tran21.setText("   "+json.getJSONObject(0).getString("tranquera21"));
                          tran22.setText("            "+json.getJSONObject(0).getString("Rotativo"));

                          int total=Integer.parseInt(json.getJSONObject(0).getString("tranquera01"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera02"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera03"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera04"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera05"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera06"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera07"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera08"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera09"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera10"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera11"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera12"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera13"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera14"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera15"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera16"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera17"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera18"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera19"))+Integer.parseInt(json.getJSONObject(0).getString("tranquera20"))+
                                  Integer.parseInt(json.getJSONObject(0).getString("tranquera21"))+Integer.parseInt(json.getJSONObject(0).getString("Rotativo"));

                          Toast.makeText(reporteTranqueras.this,"Se escanearon un total de "+total+" de estibadores",Toast.LENGTH_LONG).show();



                    }catch (JSONException e){

                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reporteTranqueras.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }
}
