package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 07/10/2017.
 */
public class Faltas {
    private String cod,falta,gravedad;
    private int fechas;

    public Faltas( String cod, String falta,String gravedad, int fechas) {
        this.gravedad = gravedad;
        this.cod = cod;
        this.falta = falta;
        this.fechas = fechas;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getFalta() {
        return falta;
    }

    public void setFalta(String falta) {
        this.falta = falta;
    }

    public int getFechas() {
        return fechas;
    }

    public void setFechas(int fechas) {
        this.fechas = fechas;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }
}
