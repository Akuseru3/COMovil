package com.example.proydiseo.CarryOn.Modelo;

public class PedidoCurso extends PedidoBasico {
    public String transName;
    public String oferta;
    public PedidoCurso(String id, String nombre, String cliente, String fecha,String transName,String oferta){
        super(id,nombre,cliente,fecha);
        this.transName = transName;
        this.oferta = oferta;
    }
}
