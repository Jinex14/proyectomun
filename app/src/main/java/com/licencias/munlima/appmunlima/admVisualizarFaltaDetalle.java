package com.licencias.munlima.appmunlima;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class admVisualizarFaltaDetalle extends AppCompatActivity {

    private String img="http://dacepsacgde.com/solicitudFalta/";
    TextView cod_sol,cod_fal,cod_tra,cod_per,des_fal,gra_fal,fec_fal,fec_res_fal;
    TextView cod_est;
    ImageView faltaImagen;
    Button btnSancionar,btnNoSancionar,btnEscanearCodigoEste;
    String dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adm_visualizar_falta_detalle);
        cod_tra= (TextView) findViewById(R.id.cod_tra);
        cod_per= (TextView) findViewById(R.id.cod_per);
        des_fal= (TextView) findViewById(R.id.des_fal);
        gra_fal= (TextView) findViewById(R.id.gra_fal);
        fec_fal= (TextView) findViewById(R.id.fec_fal);
        fec_res_fal= (TextView) findViewById(R.id.fec_res_fal);
        cod_est= (TextView) findViewById(R.id.cod_est);
        faltaImagen= (ImageView) findViewById(R.id.faltaImagen);
        btnSancionar= (Button) findViewById(R.id.btnSancionar);
        btnNoSancionar= (Button) findViewById(R.id.btnNoSancionar);
        btnEscanearCodigoEste= (Button) findViewById(R.id.btnEscanearCodigoEste);
        cod_sol= (TextView) findViewById(R.id.cod_sol);
        cod_fal= (TextView) findViewById(R.id.cod_fal);

        final Bundle res=getIntent().getExtras();
        if(res.getStringArray("datos")!=null) {
            String q[] = res.getStringArray("datos");
            cod_sol.setText(q[0]);
            cod_fal.setText(q[1]);
            cod_tra.setText(q[2]);
            cod_per.setText(q[3]);
            des_fal.setText(q[4]);
            gra_fal.setText(q[5]);
            if(q[6].equals("1")){
                fec_fal.setText(q[6]+" Día");
            }else if(q[6].equals("0")){
                fec_fal.setText("Amonestación");
            }else{
                fec_fal.setText(q[6]+" Días");
            }
            fec_res_fal.setText(q[7]);
            cod_est.setText(q[9]);
            img=img+q[10];
            cargarimagen(img);
            dia=q[6];
        }

        btnSancionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String fecha=fmt.format(new Date());
                char ad=cod_est.getText().toString().charAt(0);
                String res=ad+"";
                if(res.equals("E")) {
                    grabarCorrecto(fecha, cod_est.getText().toString(), cod_sol.getText().toString(), dia, "SAN");
                }else{
                    Toast.makeText(admVisualizarFaltaDetalle.this, "El Estivador esta sin codigo o no corresponde al formato", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNoSancionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String fecha=fmt.format(new Date());
                char ad=cod_est.getText().toString().charAt(0);
                String res=ad+"";
                if(res.equals("E")) {
                    grabarCorrecto(fecha, cod_est.getText().toString(), cod_sol.getText().toString(), dia, "DEN");
                }else{
                    Toast.makeText(admVisualizarFaltaDetalle.this, "El Estivador esta sin codigo o no corresponde al formato", Toast.LENGTH_LONG).show();
                }
            }
        });



        btnEscanearCodigoEste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(admVisualizarFaltaDetalle.this);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }



    private void cargarimagen(String url){
        Picasso.with(this).load(url).placeholder(R.drawable.cargando)
                .error(R.drawable.error_imagen)
                .into(faltaImagen,new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }





    private String UPLOAD_URL ="http://dacepsacgde.com/appMunLima/sancionar.php?";
    private void grabarCorrecto(final String fecha, final String cod_est, final String cod_sol, final String fec_fal, final String estado){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(admVisualizarFaltaDetalle.this,"Enviando...","Espere...",false,false);

        final android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(admVisualizarFaltaDetalle.this);
        builder2.setMessage("Ocurrio un Problema trata de hacer el proceso de nuevo").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(getApplicationContext(),usuSelectorFalta.class);
                startActivity(a);
                finish();
            }
        })
                .setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Intent a = new Intent(getApplicationContext(),adminPrincipal.class);
                        startActivity(a);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        builder2.show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("fecha",fecha);
                params.put("cod_est",cod_est);
                params.put("cod_sol",cod_sol);
                params.put("estado",estado);
                params.put("fec_fal",fec_fal);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(admVisualizarFaltaDetalle.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Se canceló el escaneo", Toast.LENGTH_LONG).show();
            } else {

              String codigo = result.getContents().toString();
                try {
                    codigo = decrypt(codigo);
                    cod_est.setText(codigo);
                } catch (Exception e) {
                    Toast.makeText(this, "No se logro desencriptar el QR", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

}
