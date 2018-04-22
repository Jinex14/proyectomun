package com.licencias.munlima.appmunlima;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class reportePersonal extends AppCompatActivity {
    Button calendario,salir;
    private int mYear;
    private int mMonth;
    private int mDay,sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID=0;
    Spinner spinner;
    String fecha,cod;
    ListView lsSolicitud;

    TextView correc,incorrec;
    ArrayList<Personal> listar = new ArrayList<>();
    ArrayList<escaneoMal> lista=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reporte_personal);
        calendario= (Button) findViewById(R.id.btnFechaReporte2);
        spinner= (Spinner) findViewById(R.id.spinnerIns);
        correc= (TextView) findViewById(R.id.textView20);
        incorrec= (TextView) findViewById(R.id.textView21);
        salir= (Button) findViewById(R.id.btnFechaReporteSalir);
        lsSolicitud= (ListView) findViewById(R.id.listView);
        cargarDatos();

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar as=Calendar.getInstance();
                sDayIni=as.get(Calendar.DAY_OF_MONTH);
                sMonthIni=as.get(Calendar.MONTH);
                sYearIni=as.get(Calendar.YEAR);
                lista.clear();
                ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
                lsSolicitud.setAdapter(adapter);
                showDialog(DATE_ID);

                for(int i=0;i<listar.size();i++) {
                    if(listar.get(i).getUse_per().equals(spinner.getSelectedItem().toString())){
                        cod=listar.get(i).getCod_per();
                    }}

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
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
                    fecha=mYear+"-"+(mMonth+1)+"-"+mDay;
                    cargarDatosReporte(fecha,cod);
                    cargarDatosReportem(fecha,cod);
                    cargarDatosLista(fecha,cod);
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

    public void cargarDatos(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/CargarPersonal.php";
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jr=new JSONArray(response);


                    List<String> lista=new ArrayList<>();
                    for(int i=0;i<jr.length();i++){
                        if(jr.getJSONObject(i).getString("tip_per").equals("0")) {
                            Personal a;

                            a = new Personal(jr.getJSONObject(i).getString("cod_per"), jr.getJSONObject(i).getString("use_per"), jr.getJSONObject(i).getString("nom_per"),
                                    jr.getJSONObject(i).getString("ape_pat_per"), jr.getJSONObject(i).getString("ape_mat_per"), jr.getJSONObject(i).getString("tip_per"));
                            lista.add(a.getUse_per());
                            listar.add(a);
                        }
                    }
                    ArrayAdapter<CharSequence> adapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item_modificado,lista);
                    spinner.setAdapter(adapter);

                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reportePersonal.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }

    public void cargarDatosReporte(String fecha,String cod){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/reporteusub.php?fecha="+fecha+"&cod="+cod;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jr=new JSONArray(response);

                        correc.setText("  "+jr.getJSONObject(0).getString("usu"));




                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reportePersonal.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }

    public void cargarDatosReportem(String fecha,String cod){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/reporteusum.php?fecha="+fecha+"&cod="+cod;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jr=new JSONArray(response);

                    incorrec.setText("  "+jr.getJSONObject(0).getString("usu"));




                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reportePersonal.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }

    public void cargarDatosLista(String fecha,String cod){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/detallem.php?fecha="+fecha+"&cod="+cod;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONArray jr=new JSONArray(response);

                        for(int i=0;i<jr.length();i++){
                          escaneoMal a=new escaneoMal(""+jr.getJSONObject(i).getString("dato"),jr.getJSONObject(i).getString("fecha"));
                            lista.add(a);
                        }
                        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
                        lsSolicitud.setAdapter(adapter);
                    }catch (JSONException e){

                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reportePersonal.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }
}
