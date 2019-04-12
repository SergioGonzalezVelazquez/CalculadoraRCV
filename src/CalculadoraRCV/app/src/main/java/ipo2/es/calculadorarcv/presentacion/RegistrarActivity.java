package ipo2.es.calculadorarcv.presentacion;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.Usuario;
import ipo2.es.calculadorarcv.persistencia.ConectorBD;

public class RegistrarActivity extends AppCompatActivity {

    EditText txtFechaNacimientoRegistrar;
    EditText txtNombreRegistrar;
    EditText txtApellidoRegistrar;;
    EditText txtConfirmPassRegistrar;
    EditText txtEmailRegistrar;
    EditText txtPassRegistrar;
    Button btnRegistrar;
    RadioButton radio_masculino;
    RadioButton radio_femenino;
    RelativeLayout rellayRegistrar;
    private ConectorBD conectorBD;
    private AppCompatTextView textViewLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        initViews();
        initListeners();
    }

    private void initViews() {
        conectorBD = new ConectorBD(this);
        txtFechaNacimientoRegistrar = (EditText)findViewById(R.id.txtFechaNacimientoRegistrar);
        txtNombreRegistrar = (EditText) findViewById(R.id.txtNombreRegistrar);
        txtApellidoRegistrar = (EditText) findViewById(R.id.txtApellidoRegistrar);
        txtConfirmPassRegistrar = (EditText) findViewById(R.id.txtConfirmPassRegistrar);
        txtPassRegistrar = (EditText) findViewById(R.id.txtPassRegistrar);
        txtEmailRegistrar = (EditText) findViewById(R.id.txtEmailRegistrar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        textViewLinkLogin = (AppCompatTextView) findViewById(R.id.textViewLinkLogin);
        radio_masculino= (RadioButton) findViewById(R.id.radio_masculino);
        radio_femenino = (RadioButton) findViewById(R.id.radio_femenino);
        rellayRegistrar = (RelativeLayout) findViewById(R.id.rellayRegistrar);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario nuevoUser = getValores();
                    if(nuevoUser != null){
                        // Snack Bar to show success message that record saved successfully
                        Snackbar.make(rellayRegistrar, getString(R.string.registro_correcto),
                                Snackbar.LENGTH_LONG).show();
                        finish();
                    }
            }
        });

        textViewLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtFechaNacimientoRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                txtFechaNacimientoRegistrar.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private Usuario getValores(){
        String nombre, apellido, pass, fechaNacimiento, email;
        char genero;


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atención");
        builder.setPositiveButton("OK",null);

        //NOMBRE
        if (txtNombreRegistrar.getText().toString().trim().length() > 0) {
            nombre = txtNombreRegistrar.getText().toString();

        } else {
            builder.setMessage("No ha introducido el nombre");
            builder.create();
            builder.show();
            return null;
        }

        //EMAIL
        if (txtEmailRegistrar.getText().toString().trim().length() > 0) {
            email = txtEmailRegistrar.getText().toString();
            int id = conectorBD.checkUser(email);
            if(id>0)
            {
                builder.setMessage("Ya hay un usuario registado con el correo "+email);
                builder.create();
                builder.show();
                return null;
            }

        } else {
            builder.setMessage("No ha introducido el correo electrónico");
            builder.create();
            builder.show();
            return null;
        }

        //APELLIDO
        if (txtApellidoRegistrar.getText().toString().trim().length() > 0) {
            apellido = txtApellidoRegistrar.getText().toString();

        } else {
            builder.setMessage("No ha introducido el apellido");
            builder.create();
            builder.show();
            return null;
        }

        //FECHA NACIMIENTO
        if (txtFechaNacimientoRegistrar.getText().toString().trim().length() > 0) {
            fechaNacimiento = txtFechaNacimientoRegistrar.getText().toString();

        } else {
            builder.setMessage("No ha introducido la fecha de nacimiento.");
            builder.create();
            builder.show();
            return null;
        }

        //GENERO
        if(radio_femenino.isChecked() && !radio_masculino.isChecked()) genero = 'm'; //mujer
        else if (!radio_femenino.isChecked() && radio_masculino.isChecked()) genero = 'h'; //hombre
        else {
            builder.setMessage("No ha introducido su género.");
            builder.create();
            builder.show();
            return null;
        }

        //PASSWORD
        if((txtPassRegistrar.getText().toString().trim().length() > 0) &&
                (txtConfirmPassRegistrar.getText().toString().trim().length()>0)){
            if (!txtPassRegistrar.getText().toString().equals(txtConfirmPassRegistrar.getText().toString())) {
                builder.setMessage("Las contraseñas no coinciden");
                builder.create();
                builder.show();
                return null;
            }
            else pass= txtPassRegistrar.getText().toString();
        }
        else {
            builder.setMessage("No ha introducido su contraseña");
            builder.create();
            builder.show();
            return null;
        }
        Usuario usuario = new Usuario(email, pass, nombre, apellido, fechaNacimiento, genero, "user1");
        conectorBD.insertarUsuario(usuario);
        return usuario;
    }

}
