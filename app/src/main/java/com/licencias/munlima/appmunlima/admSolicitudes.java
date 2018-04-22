package com.licencias.munlima.appmunlima;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Date;

public class admSolicitudes extends AppCompatActivity {

    ListView lsSolicitud;
    ArrayList<solicitudFaltas> lista=new ArrayList<solicitudFaltas>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adm_solicitudes);

        lsSolicitud= (ListView) findViewById(R.id.lsSolicitudesFaltas);
        cargarDatos();

        lsSolicitud.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                solicitudFaltas a= (solicitudFaltas) lsSolicitud.getItemAtPosition(i);
                String[] as={a.getCod_sol()+"",a.getCod_fal(),a.getCod_tra(),a.getCod_per(),a.getDes_fal(),a.getGra_fal(),a.getFec_fal()+"",a.getFec_reg_fal(),a.getFec_res_fal(),a.getCod_est(),a.getImg_rut()+".jpg",a.getEst_fal()};

                Intent c=new Intent(getApplicationContext(),admVisualizarFaltaDetalle.class);
                c.putExtra("datos",as);
                startActivity(c);
                finish();
            }
        });
    }





    public void cargarDatos(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/listarSolicitud.php";
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(admSolicitudes.this);
        builder.setMessage("No hay solicitudes de sanciones").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(getApplicationContext(),adminPrincipal.class);
                startActivity(a);
                finish();
            }
        }) .setCancelable(false);
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    builder.show();
                }else {
                    try {
                        JSONArray jr=new JSONArray(response);

                        for(int i=0;i<jr.length();i++){
                            solicitudFaltas a=new solicitudFaltas(jr.getJSONObject(i).getString("cod_est"),jr.getJSONObject(i).getString("cod_fal"),jr.getJSONObject(i).getString("cod_per"),jr.getJSONObject(i).getInt("cod_sol")
                                    ,jr.getJSONObject(i).getString("cod_tra"),jr.getJSONObject(i).getString("des_fal"),jr.getJSONObject(i).getString("est_fal"),jr.getJSONObject(i).getInt("fec_fal"),jr.getJSONObject(i).getString("fec_reg_fal")
                                    ,jr.getJSONObject(i).getString("fec_res_fal"),jr.getJSONObject(i).getString("gra_fal"),jr.getJSONObject(i).getString("img_rut"));
                        lista.add(a);
                        }
                        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
                        lsSolicitud.setAdapter(adapter);
                    }catch (JSONException e){

                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admSolicitudes.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);

    }
}
