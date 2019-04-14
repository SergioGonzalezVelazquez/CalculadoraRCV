package ipo2.es.calculadorarcv.dominio;

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


public class Usuario implements Serializable {
    private String pass;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private char genero; //h=hombre; m=mujer;
    private String email;
    private String ultimoAcceso;
    private String foto;
    private int idUser;

    //Historial Cálculos RCV
    private ArrayList<CalculoRCV> calculosRCV;
    private int maxRisk;

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
        this.maxRisk = 0;

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
        if(calculo.getRisk() > maxRisk) maxRisk = calculo.getRisk();
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


    public int getMaxRisk() {
        return maxRisk;
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

    private int calcularMaxRisk(){

        int max=calculosRCV.get(0).getRisk();

        for(int i = 0; i < calculosRCV.size(); i++)
        {
            if(max<calculosRCV.get(i).getRisk())
            {
                max=calculosRCV.get(i).getRisk();
            }
        }

        return max;
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
        if(calculosRCV.size()>0){
            this.maxRisk = calcularMaxRisk();
        }

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "pass='" + pass + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", genero=" + genero +
                ", email='" + email + '\'' +
                ", ultimoAcceso='" + ultimoAcceso + '\'' +
                ", foto='" + foto + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
