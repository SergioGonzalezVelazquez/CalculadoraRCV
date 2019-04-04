package ipo2.es.calculadorarcv.presentacion;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.Usuario;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;


public class MainActivity extends AppCompatActivity implements EstadoFragment.OnFragmentInteractionListener,
        CalcularFragment.OnFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle=getIntent().getExtras();
        this.usuario = (Usuario) bundle.getSerializable("usuario");
        Log.d("Debug_NAVIGATION",usuario.getEmail() + " ha iniciado sesión");
        Fragment fragment = new EstadoFragment();
        bundle.putSerializable("calculo",usuario.getUltimoRCV());
        fragment.setArguments(bundle);
        loadFragment(fragment);
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
                    fragment = new EstadoFragment();
                    bundle =  new Bundle();
                    bundle.putSerializable("calculo",usuario.getUltimoRCV());
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_calcular:
                   // mTextMessage.setText(R.string.navigationEstado);
                    Log.d("Debug_NAVIGATION",usuario.getEmail() + " Pulsó la ventana calcular");
                    //fragment = new CalcularFragment();
                    fragment = new PerfilFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_perfil:
                    Log.d("Debug_NAVIGATION",usuario.getEmail() + " Pulsó la ventana perfil");
                    fragment = new PerfilFragment();
                    loadFragment(fragment);
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
}