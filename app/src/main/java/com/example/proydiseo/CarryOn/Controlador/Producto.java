package com.example.proydiseo.CarryOn.Controlador;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {
    public String nombre;
    public String cantidad;
    public Producto(String nombre, String cantidad){
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    protected Producto(Parcel in){
        nombre = in.readString();
        cantidad = in.readString();
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(cantidad);
    }
}
