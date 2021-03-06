package ipo2.es.calculadorarcv.presentacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.Usuario;



public class MainActivity extends AppCompatActivity implements Serializable,EstadoFragment.OnFragmentInteractionListener,
        CalcularFragment.OnFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener{


    private Usuario usuario;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle=getIntent().getExtras();
        this.usuario = (Usuario) bundle.getSerializable("usuario");
        Log.d("Debug_NAVIGATION",usuario.getEmail() + " ha iniciado sesión");
        lanzarEstadoFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_superior, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acercaDe:
                Log.d("ActionBar","Pulsó la opción de menú Acerca de...");
                //Se muestra una ventana de diálogo
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case R.id.cerrarSesion:
                Log.d("ActionBar","Pulsó la opción de menú Añadir Contacto");
                finish();
                break;
            case R.id.config:
                Toast notificacion;
                notificacion = Toast.makeText(this, "Funcionalidad no implementada",
                        Toast.LENGTH_SHORT);
                notificacion.show();
                break;
        }
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Bundle bundle;

            switch (item.getItemId()) {
                case R.id.navigation_Estado:
                    Log.d("Debug_NAVIGATION",usuario.getEmail() + " pulsó la ventana estado");
                    lanzarEstadoFragment();
                    return true;
                case R.id.navigation_calcular:
                   // mTextMessage.setText(R.string.navigationEstado);
                    Log.d("Debug_NAVIGATION",usuario.getEmail() + " Pulsó la ventana calcular");
                   lanzarCalcularFragment();
                    return true;
                case R.id.navigation_perfil:
                    Log.d("Debug_NAVIGATION",usuario.getEmail() + " Pulsó la ventana perfil");
                    lanzarPerfilFragment();
                    return true;
            }
            return false;
        }
    };



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void lanzarPerfilFragment(){
        Fragment fragment;
        Bundle bundle;
        fragment = new PerfilFragment();
        bundle =  new Bundle();
        bundle.putSerializable("usuario",usuario);
        fragment.setArguments(bundle);
        loadFragment(fragment);

    }

    private void lanzarCalcularFragment(){
        Fragment fragment;
        Bundle bundle;
        fragment = new CalcularFragment();
        bundle =  new Bundle();
        bundle.putSerializable("usuario",usuario);
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }


    private void lanzarEstadoFragment() {
        Fragment fragment;
        Bundle bundle;
        fragment = new EstadoFragment();
        bundle = new Bundle();
        bundle.putSerializable("calculo", usuario.getUltimoRCV());
        fragment.setArguments(bundle);
        loadFragment(fragment);
        if (usuario.getUltimoRCV() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bienvenido");
            builder.setMessage("Realice su primer cálculo de Riesgo Cardiovascular según el" +
                    " Score de Framingham");

            // Set click listener for alert dialog buttons
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    navigation.getMenu().getItem(1).setChecked(true);
                    lanzarCalcularFragment();
                }
            };
            builder.setPositiveButton("Realizar cálculo", dialogClickListener);

            builder.create();
            builder.show();


        }

    }
}