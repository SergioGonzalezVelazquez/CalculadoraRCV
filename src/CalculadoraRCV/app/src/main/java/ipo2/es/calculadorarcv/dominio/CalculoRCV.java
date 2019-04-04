package ipo2.es.calculadorarcv.dominio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalculoRCV implements Serializable, ConstantesFactores {

    private int id;
    private int score;
    private String fecha;
    private String valoracion;
    private int edad;
    private char genero;

    //Factores Riesgo
    private boolean hvi, fumador, diabetes, hipertension;
    private double altura, peso;
    private int actividadFisica;
    private int tensionDiastolica, tensionSiastolica, colHDL, colLDL;

    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());




    private FactoresRCV factores;

    //CONSTRUCTOR PARA NUEVO CALCULO
    public CalculoRCV(Usuario usuario, boolean fumador, boolean diabetes, boolean hvi,
                      boolean hipertension, double peso, double altura, int actividadFisica,
                      int tensionDiastolica, int tensionSiastolica, int colHDL, int colLDL)  {
        this.id = 0;
        this.fecha = "";
        this.genero = usuario.getGenero();
        this.edad = usuario.getEdad();
        this.diabetes = diabetes;
        this.peso = peso;
        this.altura = altura;
        this.actividadFisica = actividadFisica;
        this.fumador = fumador;
        this.tensionDiastolica = tensionDiastolica;
        this.tensionSiastolica = tensionSiastolica;
        this.hipertension = hipertension;
        this.colHDL = colHDL;
        this.colLDL = colLDL;

        this.fecha = this.dateFormat.format(new Date());;

        this.factores = new FactoresRCV();
        this.establecerFactores();

        this.score = this.calcularScore(this.genero, this.edad, this. fumador, this. diabetes, this.colHDL,
                this.colLDL, this.hvi, this.tensionSiastolica);
    }

    //CONSTRUCTOR PARA LEER BBDD


    private int calcularScore(char genero, int edad, boolean fumador, boolean diabetes,
                              int colLDL, int colHDL, boolean hvi, int pas){
        int score= FraminghamRiskScore.getRiskScore(genero, edad, fumador, diabetes,  colHDL, colLDL, hvi, pas);

        if (score>=22 || diabetes) this.valoracion=RCV_ALTO;
        else if (score<22 || fumador || hipertension) this.valoracion=RCV_MODERADO ;
        else this.valoracion=RCV_BAJO;
        return score;
    }

    private void establecerFactores(){
        this.factores.setActividadFisica(this.actividadFisica);
        this.factores.setColesterol(this.colLDL);
        this.factores.setTabaco(this.fumador);
        this.factores.setTensionArterial(this.tensionSiastolica, this.tensionDiastolica);
        this.factores.setPeso(this.peso , this.altura);
    }

    public ArrayList<FactorRiesgo> getFactores() {
        return factores.getListadoFactores();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getValoracion() { return valoracion;}

    public void setValoracion(String valoracion) { this.valoracion = valoracion; }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public boolean isFumador() {
        return fumador;
    }

    public void setFumador(boolean fumador) {
        this.fumador = fumador;
    }

    public int getActividadFisica() {
        return actividadFisica;
    }

    public void setActividadFisica(int actividadFisica) {
        this.actividadFisica = actividadFisica;
    }


    public double getTensionDiastolica() {
        return tensionDiastolica;
    }

    public void setTensionDiastolica(int tensionDiastolica) {
        this.tensionDiastolica = tensionDiastolica;
    }

    public double getTensionSiastolica() {
        return tensionSiastolica;
    }

    public void setTensionSiastolica(int tensionSiastolica) {
        this.tensionSiastolica = tensionSiastolica;
    }

    public void setFactores(FactoresRCV factores) {
        this.factores = factores;
    }



}
