package com.licencias.munlima.appmunlima;

/**
 * Created by POST on 22/10/2017.
 */
public class escaneoMal {
    private String fecha;
    private String codigo;

    public escaneoMal(String codigo, String fecha) {
        this.codigo = codigo;
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return  "fecha= " +fecha  +" "+
                "codigo= " + codigo ;
    }
}
