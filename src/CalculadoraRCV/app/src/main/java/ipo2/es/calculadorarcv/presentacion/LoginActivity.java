package ipo2.es.calculadorarcv.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Este método se ejecutará al pulsar el botón btnEntrar
    public void oyente_btnEntrar (View v)
    {
        Log.d("Debug_LOGIN","Pulsó el botón Entrar");
        /**PRUEBA. BORRAR CUANDO SE HAGA PERSISTENTE
         *
         *
        this.usuario = new Usuario("sergio@gmail.com","1234", "sergio",
                "gonzález velázquez", "20/09/1998", "", "","");
        CalculoRCV calculoPrueba = new CalculoRCV(true, 70.5, 170, 2, 100,
                135, 150);
        this.usuario.nuevoCalculo(calculoPrueba);
        Log.d("Debug_LOGIN","Nuevo cálculo creado OK");
         */

        //Login Correcto
        Intent i = new Intent(this, MainActivity.class);
        Log.d("Debug_LOGIN","Nuevo Intent i");

        //this.usuario = new Usuario ("sergio@gmail.com","1234");
        this.usuario = new Usuario("sergio@gmail.com","1234", "Sergio",
                "González Velázquez", 'h', "20/09/1998",
                "", "");


        CalculoRCV calculoPrueba = new CalculoRCV(this.usuario, true, true,
                true, false,70.5, 1.70, 2,
                100, 135, 0,150);

        this.usuario.nuevoCalculo(calculoPrueba);

        Log.d("Debug_LOGIN","usuario creado:" + usuario.getEmail() + " " + usuario.toString() );
        //Paso el objeto Usuario a la segunda activity

        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        i.putExtras(bundle);
        startActivity(i);

        //Login Erróneo
    }
}
