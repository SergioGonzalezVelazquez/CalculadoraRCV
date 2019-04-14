package ipo2.es.calculadorarcv.dominio;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalculoRCV implements Serializable, ConstantesFactores {

    private int idUser;
    private int score;
    private int risk;
    private String fecha;
    private String valoracion;
    private int edad;
    private char genero;

    //Factores Riesgo
    private boolean hvi, fumador, diabetes, hipertension;
    private int altura;
    private double peso;
    private int actividadFisica;
    private int tensionDiastolica, tensionSiastolica, colHDL, colTotal;

    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());




    private FactoresRCV factores;

    //CONSTRUCTOR PARA NUEVO CALCULO
    public CalculoRCV(Usuario usuario, boolean fumador, boolean diabetes, boolean hvi,
                      boolean hipertension, double peso, int altura, int actividadFisica,
                      int tensionDiastolica, int tensionSiastolica, int colHDL, int colTotal, String fecha)  {
        this.idUser = usuario.getIdUser();
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
        this.colTotal = colTotal;

        if(fecha == null) this.fecha = this.dateFormat.format(new Date());
        else this.fecha = fecha;

        this.factores = new FactoresRCV();
        this.establecerFactores();

        this.score = this.calcularScore(this.genero, this.edad, this. fumador, this. diabetes, this.colHDL,
                this.colTotal, this.hvi, this.tensionSiastolica);
        this.risk = FraminghamRiskScore.scoreToRisk(this.score);
    }


    private int calcularScore(char genero, int edad, boolean fumador, boolean diabetes,
                               int colHDL, int colTotal, boolean hvi, int pas){
        int score= FraminghamRiskScore.getRiskScore(genero, edad, fumador, diabetes,  colHDL, colTotal, hvi, pas);

        if (score>=22 || diabetes) this.valoracion=RCV_ALTO;
        else if (fumador || hipertension) this.valoracion=RCV_MODERADO ;
        else this.valoracion=RCV_BAJO;
        return score;
    }

    private void establecerFactores(){
        this.factores.setActividadFisica(this.actividadFisica);
        this.factores.setColTotal(this.colTotal);
        this.factores.setTabaco(this.fumador);
        this.factores.setTensionArterial(this.tensionSiastolica, this.tensionDiastolica);
        this.factores.setPeso(this.peso , this.altura);
    }

    public ArrayList<FactorRiesgo> getFactores() {
        return factores.getListadoFactores();
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int id) {
        this.idUser = id;
    }

    public int getScore() {
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

    public void setAltura(int altura) {
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


    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public int getFumadorInt(){
        if (this.fumador) return 1;
        else return 0;
    }

    public int getDiabetesInt(){
        if (this.diabetes) return 1;
        else return 0;
    }

    public int getHviInt(){
        if (this.hvi) return 1;
        else return 0;
    }

    public int getHipertensionInt(){
        if (this.hipertension) return 1;
        else return 0;
    }

    public void setTensionSiastolica(int tensionSiastolica) {
        this.tensionSiastolica = tensionSiastolica;
    }

    public void setFactores(FactoresRCV factores) {
        this.factores = factores;
    }

    public int getColHDL() {
        return colHDL;
    }

    public void setColHDL(int colHDL) {
        this.colHDL = colHDL;
    }

    public int getColTotal() {
        return colTotal;
    }

    public void setColTotal(int colTotal) {
        this.colTotal = colTotal;
    }

    @Override
    public String toString() {
        return "CalculoRCV{" +
                "score=" + score +
                ", fecha='" + fecha + '\'' +
                ", valoracion='" + valoracion + '\'' +
                ", edad=" + edad +
                ", genero=" + genero +
                ", hvi=" + hvi +
                ", fumador=" + fumador +
                ", diabetes=" + diabetes +
                ", hipertension=" + hipertension +
                ", altura=" + altura +
                ", peso=" + peso +
                ", actividadFisica=" + actividadFisica +
                ", tensionDiastolica=" + tensionDiastolica +
                ", tensionSiastolica=" + tensionSiastolica +
                ", colHDL=" + colHDL +
                ", colTotal=" + colTotal +
                '}';
    }
}
