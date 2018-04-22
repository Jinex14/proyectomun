package com.licencias.munlima.appmunlima;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {

    EditText txtUsuarioLogin,txtPassLogin;
    Button btnIniciarLogin;
    TextView txtErrorLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        txtUsuarioLogin= (EditText) findViewById(R.id.txtUsuarioLogin);
        txtPassLogin= (EditText) findViewById(R.id.txtPassLogin);
        txtErrorLogin= (TextView) findViewById(R.id.txtErrorLogin);
        btnIniciarLogin= (Button) findViewById(R.id.btnIniciarLogin);


        btnIniciarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    obtenerDatos(txtUsuarioLogin.getText().toString(), txtPassLogin.getText().toString());

            }
        });


    }


    public void GrabarSesion(Personal a){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Sesion.edit();
        editor.putString("cod_per",a.getCod_per().toString());
        editor.putString("use_per",a.getUse_per());
        editor.putString("nom_per",a.getNom_per());
        editor.putString("ape_pat_per",a.getApe_pat_per());
        editor.putString("ape_mat_per",a.getApe_mat_per());
        editor.putString("tip_per",a.getTip_per());
        editor.commit();
    }

    public void obtenerDatos(String id, String pas){
        final ProgressDialog progressDialog=new ProgressDialog(login.this);
        progressDialog.setTitle("Validando Datos");
        progressDialog.setMessage("Espere");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://dacepsacgde.com/appMunLima/login.php?id="+id+"&pas="+pas;
        StringRequest js=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    progressDialog.dismiss();
                    txtErrorLogin.setText("Error en el usuario o password");
                }else {
                try {
                    JSONArray jr=new JSONArray(response);

                    Personal a;
                    a = new Personal(jr.getJSONObject(0).getString("cod_per"),jr.getJSONObject(0).getString("use_per"),jr.getJSONObject(0).getString("nom_per"),
                            jr.getJSONObject(0).getString("ape_pat_per"),jr.getJSONObject(0).getString("ape_mat_per"),jr.getJSONObject(0).getString("tip_per"));
                    GrabarSesion(a);
                    txtUsuarioLogin.setText("");
                    txtPassLogin.setText("");
                    progressDialog.dismiss();
                    int usu=Integer.parseInt(a.getTip_per());

                    if(usu==1){
                        Intent intent=new Intent(getApplicationContext(),adminPrincipal.class);
                        startActivity(intent);
                        finish();
                    }
                    if(usu==0){

                        Intent intent=new Intent(getApplicationContext(),usu_tranquera.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (JSONException e){

                }
            }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
        Toast.makeText(login.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(js);
    }


}
