package com.licencias.munlima.appmunlima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class usuFormularioFalta extends AppCompatActivity {

    String nom,tra,resultado;
    String falta,gravedad,validar,codfal;
    int CapturaDias;
    Button btnScarnerFalta,btnCagarImgEstFalta,btnSubirSolicitudFalta;
    TextView falt,dia,grave,txtFaltaEstivador;
    ImageView imageFaltaCometida;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usu_formulario_falta);

        Bundle r=getIntent().getExtras();
        falta = r.getString("falta");
        gravedad = r.getString("gravedad");
        CapturaDias=r.getInt("dias");
        codfal=r.getString("codfal");

        falt= (TextView) findViewById(R.id.txtFaltaSeleccionada);
        grave= (TextView) findViewById(R.id.txtFaltaGravedad);
        dia= (TextView) findViewById(R.id.txtFaltaSancionEste);
        btnScarnerFalta= (Button) findViewById(R.id.btnScanearEstFalta);
        txtFaltaEstivador= (TextView) findViewById(R.id.txtFaltaEstivador);
        btnCagarImgEstFalta= (Button) findViewById(R.id.btnCagarImgEstFalta);
        imageFaltaCometida= (ImageView) findViewById(R.id.imageFaltaCometida);
        btnSubirSolicitudFalta= (Button) findViewById(R.id.btnSubirSolicitudFalta);
        validar=null;
        String resultadodeDia="";
        CargarSesion();
        resultado="";
        if(CapturaDias==0){
            resultadodeDia="Amonestación";
        }
        if(CapturaDias==99){
            resultadodeDia="Cancelacion definitiva de la autorización";
        }
        if(CapturaDias==1){
            resultadodeDia=CapturaDias+" Día";
        }
        if(CapturaDias>1 && CapturaDias<99){
            resultadodeDia=CapturaDias+" Días";
        }

        falt.setText(falta);
        grave.setText(gravedad);
        dia.setText(resultadodeDia);


        btnScarnerFalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(usuFormularioFalta.this);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        btnCagarImgEstFalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                cargarImagen();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(usuFormularioFalta.this);
                builder.setMessage("¿Desea Cargar una Imagen?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        btnSubirSolicitudFalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar==null){
                    android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(usuFormularioFalta.this);
                    builder2.setMessage("Tiene que cargar una imagen de manera Obligatoria").setPositiveButton("Aceptar", null)
                            .setCancelable(false).show();
                }else {
                    try {
                        uploadImage();

                    }catch (Exception e){

                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Se canceló el escaneo", Toast.LENGTH_LONG).show();
            } else {

                try {
                    resultado=result.getContents().toString();
                    resultado = decrypt(resultado);
                } catch (Exception e) {
                    e.printStackTrace();
                    resultado="";
                }
                if(resultado.equals("")){
                    txtFaltaEstivador.setText("");
                }else {
                    txtFaltaEstivador.setText(result.getContents().toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10) {
            if (resultCode == RESULT_OK) {
                Uri dat = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 400, 500, true);
                    imageFaltaCometida.setImageBitmap(bitmap);
                    validar="hay imagen";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cargarImagen(){
        Intent a=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        a.setType("image/");
        startActivityForResult(a.createChooser(a,"Seleccione la Aplicación"),10);
    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("cod_per","").toString();
        tra=Sesion.getString("cod_tra","").toString();
    }

    private String convertirImg(Bitmap b){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    //test
    private String UPLOAD_URL ="http://dacepsacgde.com/solicitudFalta/faltaSolicitudCarga.php?";

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(usuFormularioFalta.this,"Enviando...","Espere...",false,false);

        final android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(usuFormularioFalta.this);
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
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Intent a = new Intent(getApplicationContext(),usuPrincipal.class);
                        startActivity(a);
                        finish();
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
                String image = convertirImg(bitmap);


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat fmt2=new SimpleDateFormat("hh:mm:ss");
                String fecha=fmt.format(new Date());
                String fec=fmt.format(new Date());
                params.put("cod_fal",codfal+"");
                params.put("cod_tra",tra+"");
                params.put("cod_per",nom+"");
                params.put("des_fal",falta);
                params.put("gra_fal",gravedad);
                params.put("fec_fal",CapturaDias+"");
                params.put("fec_reg_fal",fecha+"");
                params.put("cod_est",resultado);
                fec=fec.replace("-","a");
                String fec2=fmt2.format(new Date());
                fec2=fec2.replace(":","b");
                String img=nom+fec+fec2;
                params.put("nombre",img);
                params.put("imagen",image);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(usuFormularioFalta.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


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
