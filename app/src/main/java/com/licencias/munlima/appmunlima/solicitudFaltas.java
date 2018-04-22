package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 09/10/2017.
 */
public class solicitudFaltas {

    private int cod_sol;
    private String cod_fal,cod_tra,cod_per,des_fal,gra_fal;
    private int fec_fal;
    private String fec_reg_fal,fec_res_fal,cod_est,img_rut,est_fal;

    public solicitudFaltas(String cod_est, String cod_fal, String cod_per, int cod_sol, String cod_tra, String des_fal, String est_fal, int fec_fal, String fec_reg_fal, String fec_res_fal, String gra_fal, String img_rut) {
        this.cod_est = cod_est;
        this.cod_fal = cod_fal;
        this.cod_per = cod_per;
        this.cod_sol = cod_sol;
        this.cod_tra = cod_tra;
        this.des_fal = des_fal;
        this.est_fal = est_fal;
        this.fec_fal = fec_fal;
        this.fec_reg_fal = fec_reg_fal;
        this.fec_res_fal = fec_res_fal;
        this.gra_fal = gra_fal;
        this.img_rut = img_rut;
    }

    public String getCod_est() {
        return cod_est;
    }

    public void setCod_est(String cod_est) {
        this.cod_est = cod_est;
    }

    public String getCod_fal() {
        return cod_fal;
    }

    public void setCod_fal(String cod_fal) {
        this.cod_fal = cod_fal;
    }

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public int getCod_sol() {
        return cod_sol;
    }

    public void setCod_sol(int cod_sol) {
        this.cod_sol = cod_sol;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getDes_fal() {
        return des_fal;
    }

    public void setDes_fal(String des_fal) {
        this.des_fal = des_fal;
    }

    public String getEst_fal() {
        return est_fal;
    }

    public void setEst_fal(String est_fal) {
        this.est_fal = est_fal;
    }

    public int getFec_fal() {
        return fec_fal;
    }

    public void setFec_fal(int fec_fal) {
        this.fec_fal = fec_fal;
    }

    public String getFec_reg_fal() {
        return fec_reg_fal;
    }

    public void setFec_reg_fal(String fec_reg_fal) {
        this.fec_reg_fal = fec_reg_fal;
    }

    public String getFec_res_fal() {
        return fec_res_fal;
    }

    public void setFec_res_fal(String fec_res_fal) {
        this.fec_res_fal = fec_res_fal;
    }

    public String getGra_fal() {
        return gra_fal;
    }

    public void setGra_fal(String gra_fal) {
        this.gra_fal = gra_fal;
    }

    public String getImg_rut() {
        return img_rut;
    }

    public void setImg_rut(String img_rut) {
        this.img_rut = img_rut;
    }

    @Override
    public String toString() {

        if(cod_est.equals("null")){
            cod_est="No se identifico";
        }
        return
                "Codigo Estivador=" + cod_est + " \n" +
                "Codigo Inspector=" + cod_per + " \n" +
                "Codigo tranquera=" + cod_tra + " \n" +
                "Descripción de la falta=" + des_fal +" \n" +
                "Días de Sanción=" + fec_fal + " \n"+
                "Fecha de Registro=" + fec_reg_fal +" \n" +
                "Gravedad=" + gra_fal;
    }
}
