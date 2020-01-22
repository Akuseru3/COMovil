package com.example.proydiseo.CarryOn.Modelo;

public class PedidoSol extends PedidoBasico {
    public String cantSols;
    public PedidoSol(String id, String nombre, String cliente, String fecha,String solis){
        super(id,nombre,cliente,fecha);
        cantSols = solis;
    }
}
