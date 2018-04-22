package com.licencias.munlima.appmunlima;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class adminPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    com.getbase.floatingactionbutton.FloatingActionButton abrir;
    String nom;
    TextView txtAdmPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        txtAdmPrincipal= (TextView) findViewById(R.id.txtAdmPrincipal);
        abrir= (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.admActivarMenu);
        CargarSesion();
        txtAdmPrincipal.setText(nom);

        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent a = new Intent(getApplicationContext(), reporteTranqueras.class);
            startActivity(a);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent a = new Intent(getApplicationContext(), reportePersonal.class);
            startActivity(a);
            finish();
        } else if (id == R.id.nav_share) {
            Intent a = new Intent(getApplicationContext(), admSolicitudes.class);
            startActivity(a);
            finish();
        }else if(id == R.id.cambiarHora){
            Intent a = new Intent(getApplicationContext(), admHoraSelector.class);
            startActivity(a);
            finish();
        }else if(id == R.id.cambiarAforo){
            Intent a = new Intent(getApplicationContext(), verAforoAdm.class);
            startActivity(a);
            finish();
        }else if (id == R.id.cerrarSesionAdm){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            CerrarSesion();
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(adminPrincipal.this);
            builder.setMessage("¿Seguro que quiere salir?").setPositiveButton("Si", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void CargarSesion(){
        SharedPreferences Sesion=getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        nom=Sesion.getString("nom_per","").toString();
    }

    public void CerrarSesion(){
        SharedPreferences Sesion =getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=Sesion.edit();
        editor.remove("cod_per");
        editor.remove("use_per");
        editor.remove("nom_per");
        editor.remove("ape_pat_per");
        editor.remove("ape_mat_per");
        editor.remove("tip_per");
        editor.apply();
    }
}
