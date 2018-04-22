package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 28/08/2017.
 */
public class Tranquera {

    private String cod_tra;
    private String nom_tra;

    public Tranquera(String cod_tra, String nom_tra) {
        this.cod_tra = cod_tra;
        this.nom_tra = nom_tra;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getNom_tra() {
        return nom_tra;
    }

    public void setNom_tra(String nom_tra) {
        this.nom_tra = nom_tra;
    }
}
