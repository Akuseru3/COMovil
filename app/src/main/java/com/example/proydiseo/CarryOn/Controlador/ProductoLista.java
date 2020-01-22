package com.example.proydiseo.CarryOn.Controlador;

import java.util.ArrayList;

public class ProductoLista {
    public String nombre;
    public String cantidad;
    public String estado;
    public ProductoLista(String nombre, String cantidad,String estado){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public static ProductoLista findProd(String nombre, ArrayList<ProductoLista> all){
        for(ProductoLista actual : all ){
            if(nombre.equals(actual.nombre)){
                return actual;
            }
        }
        return null;
    }
}
