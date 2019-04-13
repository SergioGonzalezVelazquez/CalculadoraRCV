package ipo2.es.calculadorarcv.dominio;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import ipo2.es.calculadorarcv.persistencia.ConectorBD;
import ipo2.es.calculadorarcv.presentacion.LoginActivity;
import ipo2.es.calculadorarcv.presentacion.Observador;


public class Usuario implements Serializable {
    private String pass;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private char genero;
    private String email;
    private String ultimoAcceso;
    private String foto;
    private int idUser;
    private ArrayList<Observador> observadores = new ArrayList<Observador>();

    //Historial Cálculos RCV
    private ArrayList<CalculoRCV> calculosRCV;

    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


    //Constructor leer de BBDD
    public Usuario(int idUser, String email, String pass, String nombre, String apellidos, char genero, String fechaNacimiento,
                    String ultimoAcceso, String foto) {
        this.idUser = idUser;
        this.pass = pass;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;

        this.email = email;
        this.ultimoAcceso = ultimoAcceso;
        this.foto=foto;

        this.calculosRCV  = new ArrayList<CalculoRCV>();

    }

    //Constructor registrar usuario


    public Usuario( String email, String pass, String nombre, String apellidos, String fechaNacimiento,
                    char genero, String foto) {
        this.pass = pass;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.email = email;
        this.foto = foto;

        this.calculosRCV  = new ArrayList<CalculoRCV>();
        this.ultimoAcceso = this.dateFormat.format(new Date());
    }

    //Constructor comprobar si existe usuario
    public Usuario(String email, String pass) {
        this.pass = pass;
        this.email = email;


    }

    public void registrarObservador (Observador o)
    {
        observadores.add(o);
        o.update();
    }
    public void eliminarObservador (Observador o)
    {
        observadores.remove(o);
    }
    public void actualizarObservadores ()
    {
        Iterator<Observador> i = observadores.iterator();
        while(i.hasNext())
        {
            Observador o = (Observador) i.next();
            o.update();
        }
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public ArrayList<CalculoRCV> getCalculosRCV() {
        return calculosRCV;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(String ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void nuevoCalculo(CalculoRCV calculo) {
        this.calculosRCV.add(calculo);
        Log.d("Debug_OBSERVADOR","Usuario enviada notificación");
        this.actualizarObservadores ();
    }

    public CalculoRCV getUltimoRCV()
    {
        if(this.getNumCalculosRCV() == 0){
            return null;
        }
        else{
            return calculosRCV.get(this.calculosRCV.size() - 1);
        }

    }


    public int getEdad() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date fechaNacimiento = dateFormat.parse(this.fechaNacimiento);
            Calendar cal = Calendar.getInstance();
            Date fechaActual = cal.getTime();

            return calculaEdad(fechaNacimiento, fechaActual);

        } catch (ParseException e) {
            return 0;
        }
    }
    private int calculaEdad(Date fechaNacimiento, Date fechaActual) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int dIni = Integer.parseInt(formatter.format(fechaNacimiento));
        int dEnd = Integer.parseInt(formatter.format(fechaActual));
        int age = (dEnd-dIni)/10000;
        return age;
    }

    public char getGenero() {
        return genero;
    }

    public int getNumCalculosRCV() {
        return calculosRCV.size();
    }


    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setCalculosRCV(ArrayList<CalculoRCV> calculosRCV) {
        this.calculosRCV = calculosRCV;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "pass='" + pass + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", email='" + email + '\'' +
                ", ultimoAcceso='" + ultimoAcceso + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }


}
