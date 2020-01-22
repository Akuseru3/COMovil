package com.example.proydiseo.CarryOn.Modelo;

public class PedidoBasico {
    public String id;
    public String nombre;
    public String cliente;
    public String fecha;
    public PedidoBasico(String id, String nombre, String cliente, String fecha){
        this.id = id;
        this.nombre = nombre;
        this.cliente = cliente;
        this.fecha = fecha;
    }
}
