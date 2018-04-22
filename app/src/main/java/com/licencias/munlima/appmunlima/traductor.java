package com.licencias.munlima.appmunlima;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class traductor extends AppCompatActivity {

    EditText editText2;
    TextView textView;
    Button button,button2;
    String AES="AES";
    String texto,texto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_traductor);
        editText2= (EditText) findViewById(R.id.editText2);
        textView= (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                 //   texto=encrypt(editText2.getText().toString());
                  //  textView.setText(texto);
                    ArrayList<String> lista = new ArrayList<String>();
                    for(int i=1356;i<1376;i++){
                        if(i<10) {
                            String dato="E000";
                            lista.add(dato+i);
                        }else if(i<100){
                            String dato="E00";
                            lista.add(dato+i);
                        }else if(i<1000){
                            String dato="E0";
                            lista.add(dato+i);
                        }else{
                            String dato="E";
                            lista.add(dato+i);
                        }
                    }
                    for(int i=1;i<lista.size();i++) {
                        String aux=encrypt(lista.get(i));
                        Log.e("Codigo "+lista.get(i)+" : ", aux);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    texto2=decrypt(textView.getText().toString());
                    textView.setText(texto2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String decrypt(String s) throws Exception{
        SecretKeySpec key=generarLlave("AES");
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] desencriptar= Base64.decode(s,Base64.DEFAULT);
        byte[] decValue=c.doFinal(desencriptar);
        String textofinal=new String(decValue);
        return textofinal;
    }

    private String encrypt(String s1) throws Exception{
        SecretKeySpec key=generarLlave("AES");
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(s1.getBytes());
        String encriptado= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encriptado;
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
