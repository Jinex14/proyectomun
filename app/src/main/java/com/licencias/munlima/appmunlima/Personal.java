package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 27/08/2017.
 */
public class Personal {

    private String cod_per;
    private String use_per;
    private String nom_per;
    private String ape_pat_per;
    private String ape_mat_per;
    private String tip_per;

    public Personal(String cod_per, String use_per, String nom_per, String ape_pat_per, String ape_mat_per, String tip_per) {
        this.ape_mat_per = ape_mat_per;
        this.ape_pat_per = ape_pat_per;
        this.cod_per = cod_per;
        this.nom_per = nom_per;
        this.tip_per = tip_per;
        this.use_per = use_per;
    }

    public String getApe_mat_per() {
        return ape_mat_per;
    }

    public void setApe_mat_per(String ape_mat_per) {
        this.ape_mat_per = ape_mat_per;
    }

    public String getApe_pat_per() {
        return ape_pat_per;
    }

    public void setApe_pat_per(String ape_pat_per) {
        this.ape_pat_per = ape_pat_per;
    }

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public String getNom_per() {
        return nom_per;
    }

    public void setNom_per(String nom_per) {
        this.nom_per = nom_per;
    }

    public String getTip_per() {
        return tip_per;
    }

    public void setTip_per(String tip_per) {
        this.tip_per = tip_per;
    }

    public String getUse_per() {
        return use_per;
    }

    public void setUse_per(String use_per) {
        this.use_per = use_per;
    }
}
