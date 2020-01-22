package com.example.proydiseo.CarryOn.Controlador;

import android.graphics.drawable.Drawable;

import com.example.proydiseo.CarryOn.Modelo.ExtraTools;

public class Establecimiento {
    public String id;
    public String nombre;
    public Drawable image;
    public String type;
    public Establecimiento(String id,String name, String url, String tipo){
        this.id = id;
        nombre = name;
        if(url.equals("")){
            System.out.println("vacio");
            image = null;
        }
        else
            image = ExtraTools.LoadImageFromWebOperations(url,name);
        type = tipo;
    }
}
