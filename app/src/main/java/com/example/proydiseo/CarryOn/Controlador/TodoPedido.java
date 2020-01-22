package com.example.proydiseo.CarryOn.Controlador;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TodoPedido implements Parcelable {
    public String id;
    public String nombre;
    public ArrayList<Producto> productos;
    public TodoPedido(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
        productos = new ArrayList<Producto>();
    }

    protected TodoPedido(Parcel in){
        id = in.readString();
        nombre = in.readString();
        productos = in.readArrayList(Producto.class.getClassLoader());
    }

    public static final Creator<TodoPedido> CREATOR = new Creator<TodoPedido>() {
        @Override
        public TodoPedido createFromParcel(Parcel in) {
            return new TodoPedido(in);
        }

        @Override
        public TodoPedido[] newArray(int size) {
            return new TodoPedido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nombre);
        parcel.writeList(productos);
    }

    public void addProduct(String nombre,String cantidad){
        Producto nuevo = new Producto(nombre,cantidad);
        productos.add(nuevo);
    }

    public static TodoPedido findId(String id,ArrayList<TodoPedido> all){
        for(TodoPedido actual : all ){
            if(id.equals(actual.id)){
                return actual;
            }
        }
        return null;
    }

    public static String generateOrderString(ArrayList<TodoPedido> all){
        String result = "";
        for(TodoPedido actual : all ){
            result+=actual.nombre+"\n";
            for(Producto prod : actual.productos ){
                result+= "    "+prod.cantidad+", "+prod.nombre+"\n";
            }
        }
        return result;
    }
}
