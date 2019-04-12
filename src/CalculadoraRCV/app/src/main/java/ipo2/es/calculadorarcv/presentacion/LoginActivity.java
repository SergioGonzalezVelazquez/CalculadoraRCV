package ipo2.es.calculadorarcv.presentacion;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.Usuario;
import ipo2.es.calculadorarcv.persistencia.ConectorBD;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private Button btnEntrar;
    private String user;
    private EditText txtPassword;
    private String pass;
    private Usuario usuario;
    private ConectorBD conectorBD;
    private AppCompatTextView textViewLinkRegister;
    private RelativeLayout rellayLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();


    }

    /**
     * This method is to initialize views
     */
    private void initViews(){
        conectorBD = new ConectorBD(this);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        rellayLogin = (RelativeLayout) findViewById(R.id.rellayLogin);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oyente_btnEntrar(v);
            }
        });

       textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(intentRegister);
            }
        });
    }


    //Este método se ejecutará al pulsar el botón btnEntrar
    public void oyente_btnEntrar (View v)
    {
        Log.d("Debug_LOGIN","Pulsó el botón Entrar");
        user = txtEmail.getText().toString();
        pass = txtPassword.getText().toString();


        int id = conectorBD.checkUser(user,pass);
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
            Log.d("Debug_Login","Usuario leído:"+usuario.toString());
            Intent i = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            i.putExtras(bundle);
            startActivity(i);
            txtEmail.getText().clear();
            txtPassword.getText().clear();
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(rellayLogin, getString(R.string.login_correcto),
                    Snackbar.LENGTH_LONG).show();
            conectorBD.cerrar();
        }

    }
}
