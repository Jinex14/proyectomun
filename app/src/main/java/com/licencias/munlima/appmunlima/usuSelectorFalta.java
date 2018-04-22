package com.licencias.munlima.appmunlima;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class usuSelectorFalta extends AppCompatActivity {
    String nom,tra;
    TextView txtUsuarioFalta,txtUsuarioFaltaTranquera;
    Spinner spinner2;
    Button aceptar;
    ArrayList<Faltas> listar = new ArrayList<>();
    Faltas select=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usu_selector_falta);
        txtUsuarioFalta= (TextView) findViewById(R.id.txtUsuarioFalta);
        txtUsuarioFaltaTranquera= (TextView) findViewById(R.id.txtUsuarioFaltaTranquera);
        spinner2= (Spinner) findViewById(R.id.spinnerFalta);
        aceptar= (Button) findViewById(R.id.btnAceptarFalta);
        CargarSesion();
        txtUsuarioFalta.setText(nom);
        txtUsuarioFaltaTranquera.setText(tra);
        llenarSpinner();
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                for(int i=0;i<listar.size();i++) {
                                    if(listar.get(i).getFalta().equals(spinner2.getSelectedItem().toString())){
                                        select=new Faltas(listar.get(i).getCod(),listar.get(i).getFalta(),listar.get(i).getGravedad(),listar.get(i).getFechas());
                                    }
                                }


                                Intent a=new Intent(getBaseContext(),usuFormularioFalta.class);
                                a.putExtra("codfal",select.getCod());
                                a.putExtra("falta",select.getFalta());
                                a.putExtra("gravedad",select.getGravedad());
                                a.putExtra("dias",select.getFechas());
                                startActivity(a);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(usuSelectorFalta.this);
                builder.setMessage("¿Seguro que quiere realizar la solicitud de sanción?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("nom_per","").toString();
        tra=Sesion.getString("nom_tra","").toString();
    }

    public void llenarSpinner(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/faltaseste.php";
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    Toast.makeText(usuSelectorFalta.this,"Error en el servidor",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONArray jr=new JSONArray(response);


                        List<String> lista=new ArrayList<>();
                        for(int i=0;i<jr.length();i++){
                            Faltas a=new Faltas(jr.getJSONObject(i).getString("cod_fal"),jr.getJSONObject(i).getString("des_fal"),jr.getJSONObject(i).getString("gra_fal"),jr.getJSONObject(i).getInt("fec_fal"));
                            lista.add(jr.getJSONObject(i).getString("des_fal"));
                            listar.add(a);
                        }
                        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item_modificado,lista);
                        spinner2.setAdapter(adapter);


                    }catch (JSONException e){

                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(usuSelectorFalta.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);
    }



}
