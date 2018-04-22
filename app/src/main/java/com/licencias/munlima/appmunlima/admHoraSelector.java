package com.licencias.munlima.appmunlima;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;


public class admHoraSelector extends AppCompatActivity {

    Button btnHoraCambio;
    private int hora,minutos;
    TextView txtHoraSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adm_hora_selector);

        btnHoraCambio= (Button) findViewById(R.id.btnHoraCambio);
        txtHoraSelect= (TextView) findViewById(R.id.txtHoraSelect);

        HoraFinalDeCierre();

        btnHoraCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                hora=c.get(Calendar.HOUR_OF_DAY);
                minutos=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(admHoraSelector.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        cargarNuevaHora(hourOfDay+"",minute+"");
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
        });

    }



    //test
    private String UPLOAD_URL ="http://dacepsacgde.com/appMunLima/horaTranquera.php?";

    private void cargarNuevaHora(final String Hora,final String min){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(admHoraSelector.this,"Enviando...","Espere...",false,false);
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(admHoraSelector.this);
        builder.setMessage("El proceso fue satisfactorio").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(getApplicationContext(),adminPrincipal.class);
                startActivity(a);
                finish();
            }
        }) .setCancelable(false);

        final android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(admHoraSelector.this);
        builder2.setMessage("Ocurrio un Problema trata de hacer el proceso de nuevo").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(getApplicationContext(),adminPrincipal.class);
                startActivity(a);
                finish();
            }
        })
                .setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(admHoraSelector.this,"Cambio Correcto", Toast.LENGTH_LONG).show();

                        builder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        builder2.show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd");
                String fe2c=fmt3.format(new Date());

                params.put("fecha",fe2c);
                params.put("hora",Hora);
                params.put("minu",min);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(admHoraSelector.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


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
                    txtHoraSelect.setText("11:00");

                }else{
                    try {
                        JSONArray json = new JSONArray(response);

                        int hora=json.getJSONObject(0).getInt("hora_maxima");
                        int minuto=json.getJSONObject(0).getInt("min_maxima");

                        String horaf="00";
                        String minutof="00";

                        if(hora<10){
                            horaf="0"+hora;
                        }else{
                            horaf=""+hora;
                        }

                        if(minuto<10){
                            minutof="0"+minuto;
                        }else{
                            minutof=""+minuto;
                        }

                        String horatotal=horaf+":"+minutof;

                        txtHoraSelect.setText(horatotal);


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

}
