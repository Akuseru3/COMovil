package com.example.proydiseo.CarryOn.Controlador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.validator.routines.EmailValidator;

public class Validations {

    public static String validatePedido(String nombre, Double lat, Double longt, ArrayList<TodoPedido> pedido){
        if(!checkEmptyString(nombre)){
            return "Es obligatorio que el pedido nuevo tenga un nombre";
        }
        if(lat == 0.0 || longt == 0.0){
            return "Se debe definir la direccion donde se entregara el pedido";
        }
        if(pedido.size()==0){
            return "El pedido no puede ser creado sin productos";
        }
        return "";
    }

    public static String validateRegister(String nombre,String apellidos,String dia, String mes, String año, String telefono, String correo, String contra, String contra2){
        String result = "";
        if(!checkEmptyString(nombre) && !checkEmptyString(apellidos) && !checkEmptyString(dia) && !checkEmptyString(mes) && !checkEmptyString(año) && !checkEmptyString(telefono) && !checkEmptyString(correo) && !checkEmptyString(contra)){
            return "Por favor ingrese toda la información que se le solicita.";
        }
        if(!isDateValid(dia,mes,año)){
            return "Fecha de nacimiento invalida.";
        }
        if(telefono.length() != 8){
            return "El número de telefono ingresado no es valido.";
        }
        if(!emailValid(correo)){
            return "El correo electronico ingresado no es valido.";
        }
        if(!contra.equals(contra2)){
            return "Las contraseñas no coinciden.";
        }
        return result;
    }

    public static String validateUserMod(String nombre,String apellidos,String dia, String mes, String año, String telefono){
        String result = "";
        if(!checkEmptyString(nombre) && !checkEmptyString(apellidos) && !checkEmptyString(dia) && !checkEmptyString(mes) && !checkEmptyString(año) && !checkEmptyString(telefono)){
            return "Por favor ingrese toda la información que se le solicita.";
        }
        if(!isDateValid(dia,mes,año)){
            return "Fecha de nacimiento invalida.";
        }
        if(telefono.length() != 8){
            return "El número de telefono ingresado no es valido.";
        }
        return result;
    }

    public static boolean emailValid(String email){
        boolean valid = EmailValidator.getInstance().isValid(email);
        return valid;
    }

    public static boolean isDateValid(String day, String month, String year){
        if(checkIfInt(day) && checkIfInt(month) && checkIfInt(year)){
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                df.setLenient(false);
                df.parse(year+"-"+month+"-"+day);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static boolean checkIfInt(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static boolean checkEmptyString(String toCheck){
        String processed = toCheck.trim();
        if(processed.equals(""))
            return false;
        else
            return true;
    }
}
