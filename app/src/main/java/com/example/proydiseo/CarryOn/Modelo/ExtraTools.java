package com.example.proydiseo.CarryOn.Modelo;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class ExtraTools {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/*-_@$&%=?¿!";

    public static Drawable LoadImageFromWebOperations(String url,String name) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, name);
            return d;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static String generateCode(){
        StringBuilder builder = new StringBuilder();
        int count = 8;
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static final String[] str = {"Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Setiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"};

    public static String generateDate(String fecha){
        String[] result = fecha.split("-");
        String dia = result[2];
        String mes = result[1];
        String año = result[0];
        int mon = Integer.parseInt(mes);
        String foundM = str[mon-1];
        return dia + " de "+ foundM +", "+año;
    }
}
