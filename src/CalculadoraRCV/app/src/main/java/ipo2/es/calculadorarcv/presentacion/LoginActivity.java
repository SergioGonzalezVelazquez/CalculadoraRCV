package ipo2.es.calculadorarcv.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.Usuario;
import ipo2.es.calculadorarcv.persistencia.ConectorBD;
import ipo2.es.calculadorarcv.persistencia.DB_SQLiteHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private String user;
    private EditText txtPassword;
    private String pass;
    private Usuario usuario;
    private ConectorBD conectorBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conectorBD = new ConectorBD(this);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    //Este método se ejecutará al pulsar el botón btnEntrar
    public void oyente_btnEntrar (View v)
    {
        Log.d("Debug_LOGIN","Pulsó el botón Entrar");
        user = txtEmail.getText().toString();
        pass = txtPassword.getText().toString();
        conectorBD.abrir();

        int id = conectorBD.checkUser(new Usuario(user,pass));
        if(id<0)
        {
            Toast.makeText(LoginActivity.this,
                    "Correo electrónico o contraseña incorrectos",Toast.LENGTH_SHORT).show();
            txtEmail.getText().clear();
            txtPassword.getText().clear();

        }
        else
        {
            this.usuario = conectorBD.leerUsuario(id);
            Intent i = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            i.putExtras(bundle);
            startActivity(i);
            txtEmail.getText().clear();
            txtPassword.getText().clear();
            conectorBD.cerrar();
        }

    }
}
