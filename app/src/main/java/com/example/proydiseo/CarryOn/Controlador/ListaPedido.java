package com.example.proydiseo.CarryOn.Controlador;

import java.util.ArrayList;

public class ListaPedido {
    public String id;
    public String nombre;
    public ArrayList<ProductoLista> productos;
    public ListaPedido(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
        productos = new ArrayList<ProductoLista>();
    }

    public void addProduct(String nombre,String cantidad,String estado){
        ProductoLista nuevo = new ProductoLista(nombre,cantidad,estado);
        productos.add(nuevo);
    }

    public static ListaPedido findId(String id,ArrayList<ListaPedido> all){
        for(ListaPedido actual : all ){
            if(id.equals(actual.id)){
                return actual;
            }
        }
        return null;
    }
}
