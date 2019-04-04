package ipo2.es.calculadorarcv.dominio;

import java.io.Serializable;
import java.util.ArrayList;

public class FactoresRCV implements  ConstantesFactores, Serializable {

    private FactorRiesgo tabaco;
    private FactorRiesgo actividadFisica;
    private FactorRiesgo tensionArterial;
    private FactorRiesgo peso;
    private FactorRiesgo colesterol;


    public FactoresRCV(){
        this.tabaco = new FactorRiesgo("Abstención Tabáquica");
        this.actividadFisica = new FactorRiesgo("Actividad Física");
        this.tensionArterial = new FactorRiesgo("Tensión Arterial");
        this.peso = new FactorRiesgo("Peso Ideal");
        this.colesterol = new FactorRiesgo("Nivel de Colesterol");

        //this.estres = new FactorRiesgo("Estrés");
    }

    public void setTabaco(boolean fumador) {
        this.tabaco.setEstado(fumador);
        //this.tabaco.setValor((fumador) ? "Si" : "No");
    }

    public void setActividadFisica(int nivelActividad) {
        switch (nivelActividad){
            case 0: // No, no practico ningún deporte
                this.actividadFisica.setEstado(false);
                this.actividadFisica.setValor(DEPORTE_NADA);
                break;
            case 1:// Sí, mínimo una vez al mes
                this.actividadFisica.setEstado(false);
                this.actividadFisica.setValor(DEPORTE_POCO);
                break;
            case 2: //Sí, mínimo una vez por semana
                this.actividadFisica.setEstado(true);
                this.actividadFisica.setValor(DEPORTE_SEMANA);
                break;
            case 3://Sí, mínimo dos o tres veces por semana
                this.actividadFisica.setEstado(true);
                this.actividadFisica.setValor(DEPORTE_MUCHO);;
                break;
        }
    }

    public void setTensionArterial(int tensionSistolica, int tensionDiastolica) {
        this.tensionArterial.setEstado((tensionSistolica>MIN_SISTOLICA && tensionSistolica < MAX_DIASTOLICA)
                && (tensionDiastolica>MIN_DIASTOLICA && tensionDiastolica < MAX_DIASTOLICA));
        this.tensionArterial.setValor("Alta: "+tensionSistolica+"mmHg ; " +
                "Baja: "+tensionDiastolica+"mmHg");
    }

    public void setPeso(double peso, double altura) {
        double imc = (double) Math.round(peso/(altura*altura) * (100d/100d));
        this.peso.setEstado(imc<=18.50 || imc<=24.99);
        String cad;
        if(imc<16.00){
            cad="IMC: "+imc+" Infrapeso: Delgadez Severa";
        }else if(imc<=16.00 || imc<=16.99){
            cad="IMC: "+imc+" Infrapeso: Delgadez moderada";
        }else if(imc<=17.00 ||imc<=18.49){
            cad="IMC: "+imc+" Infrapeso: Delgadez aceptable";
        }else if(imc<=18.50 || imc<=24.99){
            cad="IMC: "+imc+" Peso Normal";
        }else if(imc<=25.00 || imc<=29.99){
            cad="IMC: "+imc+" Sobrepeso";
        }else if(imc<=30.00 || imc<=34.99){
            cad="IMC: "+imc+" Obeso: Tipo I";
        }else if(imc<=35.00 || imc<=40.00){
            cad="IMC: "+imc+" Obeso: Tipo III";
        }else{
            cad="no existe clasificacion";
        }
        this.peso.setValor(cad);

    }

    public void setColesterol(int colesterol) {
        if (colesterol<=MAX_COLESTEROL_OPTIMO){
            this.colesterol.setEstado(true);
            this.colesterol.setValor(colesterol + "mg/dL: Deseable");

        }else if(colesterol<=MAX_COLESTEROL){
            this.colesterol.setEstado(true);
            this.colesterol.setValor(colesterol + "mg/dL: Límite superior del rango normal");

        }else{
            this.colesterol.setEstado(false);
            this.colesterol.setValor(colesterol + "mg/dL: Alto");

        }

    }

    public ArrayList<FactorRiesgo> getListadoFactores(){
        ArrayList <FactorRiesgo> listado = new ArrayList<FactorRiesgo>();
        listado.add(tabaco);
        listado.add(actividadFisica);
        listado.add(tensionArterial);
        listado.add(colesterol);
        listado.add(peso);


        return listado;
    }
}
