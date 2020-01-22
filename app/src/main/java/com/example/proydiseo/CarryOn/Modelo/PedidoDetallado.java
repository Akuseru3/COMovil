package com.example.proydiseo.CarryOn.Modelo;

import com.example.proydiseo.CarryOn.Controlador.TodoPedido;

import java.util.ArrayList;

public class PedidoDetallado {
    public String id;
    public String nombre;
    public String cliente;
    public double lat;
    public double longt;
    public String señalesExtra;
    public ArrayList<TodoPedido> pedido;
    public PedidoDetallado(String id, String nombre, String cliente, String lat,String lon,String sExtra, ArrayList<TodoPedido> todo){
        this.id = id;
        this.nombre = nombre;
        this.cliente = cliente;
        this.lat = Double.parseDouble(lat);
        this.longt = Double.parseDouble(lon);
        señalesExtra = sExtra;
        pedido = todo;
    }
}
