package ipo2.es.calculadorarcv.dominio;

import java.io.Serializable;

public class FactorRiesgo implements Serializable {

    private String nombre;
    private boolean estado;
    private String valor;
    private String recomendación;

    public FactorRiesgo(String nombre){
        this.nombre = nombre;
    }
    public FactorRiesgo(String nombre, boolean estado, String recomendación) {
        this.nombre = nombre;
        this.estado = estado;
        this.recomendación = recomendación;
        this.valor = null;
    }

    public FactorRiesgo(String nombre, boolean estado, String recomendación, String valor) {
        this.nombre = nombre;
        this.estado = estado;
        this.recomendación = recomendación;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getRecomendación() {
        return recomendación;
    }

    public void setRecomendación(String recomendación) {
        this.recomendación = recomendación;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
