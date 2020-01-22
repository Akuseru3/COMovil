package com.example.proydiseo.CarryOn.Controlador;

public class Usuario {
    public String nombre;
    public String apellidos;
    public String fecha;
    public String telefono;
    public String correo;

    public Usuario(String nombre, String apellidos, String bday, String telefono,String correo){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha = bday;
        this.telefono = telefono;
        this.correo = correo;
    }
}
