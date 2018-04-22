package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 30/08/2017.
 */
public class Estivador {

    private String cod_est,nom_est,ape_pat_est,ape_mat_est,dni_est,nom_aso,fot_est,cod_aso;

    public Estivador(String cod_est, String nom_est, String ape_pat_est, String ape_mat_est, String dni_est, String nom_aso, String fot_est, String cod_aso) {
        this.ape_mat_est = ape_mat_est;
        this.ape_pat_est = ape_pat_est;
        this.cod_aso = cod_aso;
        this.cod_est = cod_est;
        this.dni_est = dni_est;
        this.fot_est = fot_est;
        this.nom_aso = nom_aso;
        this.nom_est = nom_est;
    }

    public String getApe_mat_est() {
        return ape_mat_est;
    }

    public void setApe_mat_est(String ape_mat_est) {
        this.ape_mat_est = ape_mat_est;
    }

    public String getApe_pat_est() {
        return ape_pat_est;
    }

    public void setApe_pat_est(String ape_pat_est) {
        this.ape_pat_est = ape_pat_est;
    }

    public String getCod_aso() {
        return cod_aso;
    }

    public void setCod_aso(String cod_aso) {
        this.cod_aso = cod_aso;
    }

    public String getCod_est() {
        return cod_est;
    }

    public void setCod_est(String cod_est) {
        this.cod_est = cod_est;
    }

    public String getDni_est() {
        return dni_est;
    }

    public void setDni_est(String dni_est) {
        this.dni_est = dni_est;
    }

    public String getFot_est() {
        return fot_est;
    }

    public void setFot_est(String fot_est) {
        this.fot_est = fot_est;
    }

    public String getNom_aso() {
        return nom_aso;
    }

    public void setNom_aso(String nom_aso) {
        this.nom_aso = nom_aso;
    }

    public String getNom_est() {
        return nom_est;
    }

    public void setNom_est(String nom_est) {
        this.nom_est = nom_est;
    }
}
